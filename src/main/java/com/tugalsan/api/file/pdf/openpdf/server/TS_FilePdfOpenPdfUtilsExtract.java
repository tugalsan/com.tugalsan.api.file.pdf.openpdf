package com.tugalsan.api.file.pdf.openpdf.server;

import java.nio.file.Path;

public class TS_FilePdfOpenPdfUtilsExtract {

    public static void extract(Path srcFile, Path destFile1, Path destFile2, int newFile_PageIndex_StartOffset) {
        TS_FilePdfOpenPdfUtilsDocument.run_doc_with_reader(srcFile, (srcDoc, srcReader) -> {
            var srcPageCount = TS_FilePdfOpenPdfUtilsPage.count(srcReader);
            TS_FilePdfOpenPdfUtilsDocument.run_doc_with_writer(destFile1, (dstDoc1, dstWriter1) -> {
                TS_FilePdfOpenPdfUtilsDocument.run_doc_with_writer(destFile2, (dstDoc2, dstWriter2) -> {
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
