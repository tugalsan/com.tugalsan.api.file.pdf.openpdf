package com.tugalsan.api.file.pdf.openpdf.server;

import com.tugalsan.api.union.client.TGS_UnionExcuseVoid;
import com.tugalsan.api.function.client.maythrowexceptions.checked.TGS_FuncMTCUtils;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TS_FilePdfOpenPdfUtilsPageExtract {

    private TS_FilePdfOpenPdfUtilsPageExtract() {

    }

    @Deprecated//OLD WAY
    public static void extract_old(TS_FilePdfOpenPdfUtilsPageCompress.CompressionLevel cLvl, Path pdfSrcFile, int pageNr, Path pdfDstFile) {
        TS_FilePdfOpenPdfUtilsDocument.run_doc_with_copy(cLvl, pdfDstFile, (docDst, pdfCopy) -> {
            TS_FilePdfOpenPdfUtilsDocument.run_doc_with_reader(pdfSrcFile, (srcDoc, reader) -> {
                TGS_FuncMTCUtils.run(() -> {
                    pdfCopy.addPage(pdfCopy.getImportedPage(reader, pageNr + 1));
                });
            });
        });
    }

    public static List<TGS_UnionExcuseVoid> extract_old(TS_FilePdfOpenPdfUtilsPageCompress.CompressionLevel cLvl, Path pdfSrcFile, int[] pageNrs, Path pdfDstFile) {
        List<TGS_UnionExcuseVoid> results = new ArrayList();
        TS_FilePdfOpenPdfUtilsDocument.run_doc_with_copy(cLvl, pdfDstFile, (docDst, pdfCopy) -> {
            TS_FilePdfOpenPdfUtilsDocument.run_doc_with_reader(pdfSrcFile, (srcDoc, reader) -> {
                TGS_FuncMTCUtils.run(() -> {
                    Arrays.stream(pageNrs).forEach(pageNr -> {
                        TGS_FuncMTCUtils.run(() -> {
                            pdfCopy.addPage(pdfCopy.getImportedPage(reader, pageNr + 1));
                        }, e -> results.add(TGS_UnionExcuseVoid.ofExcuse(e)));
                    });
                });
            });
        });
        return results;
    }

    public static void extract(TS_FilePdfOpenPdfUtilsPageCompress.CompressionLevel cLvl, Path srcFile, Path destFile1, Path destFile2, int newFile_PageIndex_StartOffset) {
        TS_FilePdfOpenPdfUtilsDocument.run_doc_with_reader(srcFile, (srcDoc, srcReader) -> {
            var srcPageCount = TS_FilePdfOpenPdfUtilsPage.count(srcReader);
            TS_FilePdfOpenPdfUtilsDocument.run_doc_with_writer(cLvl, TS_FilePdfOpenPdfUtilsPage.PAGE_INFO_A4_PORT_0_0_0_0, destFile1, (dstDoc1, dstWriter1) -> {
                TS_FilePdfOpenPdfUtilsDocument.run_doc_with_writer(cLvl, TS_FilePdfOpenPdfUtilsPage.PAGE_INFO_A4_PORT_0_0_0_0, destFile2, (dstDoc2, dstWriter2) -> {
                    var dstContentByte1 = dstWriter1.getDirectContent();
                    var dstContentByte2 = dstWriter2.getDirectContent();
                    int i = 0;
                    while (i < newFile_PageIndex_StartOffset - 1) {
                        i++;
                        dstDoc1.setPageSize(srcReader.getPageSizeWithRotation(i));
                        dstDoc1.newPage();
                        var srcPage = dstWriter1.getImportedPage(srcReader, i);
                        var rotation = srcReader.getPageRotation(i);
                        if (rotation == 90 || rotation == 270) {
                            dstContentByte1.addTemplate(srcPage, 0, -1f, 1f, 0, 0, srcReader.getPageSizeWithRotation(i).getHeight());
                        } else {
                            dstContentByte1.addTemplate(srcPage, 1f, 0, 0, 1f, 0, 0);
                        }
                    }
                    while (i < srcPageCount) {
                        i++;
                        dstDoc2.setPageSize(srcReader.getPageSizeWithRotation(i));
                        dstDoc2.newPage();
                        var srcPage = dstWriter2.getImportedPage(srcReader, i);
                        var rotation = srcReader.getPageRotation(i);
                        if (rotation == 90 || rotation == 270) {
                            dstContentByte2.addTemplate(srcPage, 0, -1f, 1f, 0, 0, srcReader.getPageSizeWithRotation(i).getHeight());
                        } else {
                            dstContentByte2.addTemplate(srcPage, 1f, 0, 0, 1f, 0, 0);
                        }
                    }
                });
            });
        });

    }
}
