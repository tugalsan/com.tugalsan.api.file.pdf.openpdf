package com.tugalsan.api.file.pdf.openpdf.server;

import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.SimpleBookmark;
import com.tugalsan.api.thread.server.sync.TS_ThreadSyncLst;
import com.tugalsan.api.union.client.TGS_UnionExcuseVoid;
import com.tugalsan.api.function.client.maythrow.checkedexceptions.TGS_FuncMTCEUtils;
import com.tugalsan.api.function.client.maythrow.uncheckedexceptions.TGS_FuncMTUCEUtils;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class TS_FilePdfOpenPdfUtilsPageMerge {

    public static TGS_UnionExcuseVoid merge(TS_FilePdfOpenPdfUtilsPageCompress.CompressionLevel cLvl, List<Path> srcFiles, Path dstFile) {
        return TS_FilePdfOpenPdfUtilsDocument.run_doc_with_copy(cLvl, dstFile, (dstDoc, copy) -> {
            TGS_FuncMTCEUtils.run(() -> {
                var pageIdxOffset = new AtomicInteger(0);
                TS_ThreadSyncLst<Map<String, Object>> masterBookmarkList = TS_ThreadSyncLst.ofSlowRead();
                for (Path srcFile : srcFiles) {
                    var u_reader = TS_FilePdfOpenPdfUtilsDocument.run_doc_with_reader(srcFile, (srcDoc, srcReader) -> {
                        merge(copy, pageIdxOffset, masterBookmarkList, srcReader);
                    });
                    if (u_reader.isExcuse()) {
                        TGS_FuncMTUCEUtils.thrw(u_reader.excuse());
                    }
                }
                if (!masterBookmarkList.isEmpty()) {
                    copy.setOutlines(masterBookmarkList.toList_fast());
                }
            });
        });
    }

    private static void merge(PdfCopy copy, AtomicInteger pageIdxOffset, TS_ThreadSyncLst<Map<String, Object>> masterBookmarkList, PdfReader srcReader) {
        TGS_FuncMTCEUtils.run(() -> {
            srcReader.consolidateNamedDestinations();
            var count = TS_FilePdfOpenPdfUtilsPage.count(srcReader);
            List<Map<String, Object>> bookmarks = SimpleBookmark.getBookmarkList(srcReader);
            if (bookmarks != null) {
                if (pageIdxOffset.get() != 0) {
                    SimpleBookmark.shiftPageNumbersInRange(bookmarks, pageIdxOffset.get(), null);
                }
                masterBookmarkList.add(bookmarks);
            }
            pageIdxOffset.set(pageIdxOffset.get() + count);
            for (var i = 1; i <= count; i++) {
                var page = copy.getImportedPage(srcReader, i);
                copy.addPage(page);
            }
            copy.freeReader(srcReader);
        });
    }

    @Deprecated//OLD WAY
    public static void merge_old(TS_FilePdfOpenPdfUtilsPageCompress.CompressionLevel cLvl, List<Path> pdfSrcFiles, Path pdfDstFile) {
        TS_FilePdfOpenPdfUtilsDocument.run_doc_with_copy(cLvl, pdfDstFile, (docDst, pdfCopy) -> {
            pdfSrcFiles.stream().forEachOrdered(srcFile -> {
                TS_FilePdfOpenPdfUtilsDocument.run_doc_with_reader(srcFile, (srcDoc, srcReader) -> {
                    IntStream.range(0, TS_FilePdfOpenPdfUtilsPage.count(srcReader)).forEachOrdered(pageIdx -> {
                        TGS_FuncMTCEUtils.run(() -> {
                            pdfCopy.addPage(pdfCopy.getImportedPage(srcReader, (pageIdx + 1)));
                        });
                    });
                });
            });
        });
    }
}
