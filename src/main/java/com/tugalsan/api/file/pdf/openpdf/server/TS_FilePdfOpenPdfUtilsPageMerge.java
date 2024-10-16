package com.tugalsan.api.file.pdf.openpdf.server;

import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.SimpleBookmark;
import com.tugalsan.api.thread.server.sync.TS_ThreadSyncLst;
import com.tugalsan.api.union.client.TGS_UnionExcuseVoid;
import com.tugalsan.api.unsafe.client.TGS_UnSafe;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class TS_FilePdfOpenPdfUtilsPageMerge {

    public static TGS_UnionExcuseVoid merge(List<Path> srcFiles, Path dstFile) {
        return TS_FilePdfOpenPdfUtilsDocument.run_doc_with_copy(dstFile, (dstDoc, copy) -> {
            TGS_UnSafe.run(() -> {
                var pageIdxOffset = new AtomicInteger(0);
                TS_ThreadSyncLst<Map<String, Object>> masterBookmarkList = TS_ThreadSyncLst.of();
                for (Path srcFile : srcFiles) {
                    var u_reader = TS_FilePdfOpenPdfUtilsDocument.run_doc_with_reader(srcFile, (srcDoc, srcReader) -> {
                        merge(copy, pageIdxOffset, masterBookmarkList, srcReader);
                    });
                    if (u_reader.isExcuse()) {
                        TGS_UnSafe.thrw(u_reader.excuse());
                    }
                }
                if (!masterBookmarkList.isEmpty()) {
                    copy.setOutlines(masterBookmarkList.toList());
                }
            });
        });
    }

    private static void merge(PdfCopy copy, AtomicInteger pageIdxOffset, TS_ThreadSyncLst<Map<String, Object>> masterBookmarkList, PdfReader srcReader) {
        TGS_UnSafe.run(() -> {
            srcReader.consolidateNamedDestinations();
            var numberOfPages = srcReader.getNumberOfPages();
            List<Map<String, Object>> bookmarks = SimpleBookmark.getBookmarkList(srcReader);
            if (bookmarks != null) {
                if (pageIdxOffset.get() != 0) {
                    SimpleBookmark.shiftPageNumbersInRange(bookmarks, pageIdxOffset.get(), null);
                }
                masterBookmarkList.add(bookmarks);
            }
            pageIdxOffset.set(pageIdxOffset.get() + numberOfPages);
            for (var i = 1; i <= numberOfPages; i++) {
                var page = copy.getImportedPage(srcReader, i);
                copy.addPage(page);
            }
            copy.freeReader(srcReader);
        });
    }
}
