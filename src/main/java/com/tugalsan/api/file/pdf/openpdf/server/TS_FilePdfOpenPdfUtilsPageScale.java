package com.tugalsan.api.file.pdf.openpdf.server;

import com.lowagie.text.pdf.PdfStamper;
import com.tugalsan.api.function.client.maythrowexceptions.checked.TGS_FuncMTCUtils;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.util.stream.IntStream;

public class TS_FilePdfOpenPdfUtilsPageScale {

    private TS_FilePdfOpenPdfUtilsPageScale() {

    }

    public static void scale(Path pdfSrcFile, Path pdfDstFile, float scaleFactor) {
        TS_FilePdfOpenPdfUtilsDocument.run_doc_with_reader(pdfSrcFile, (srcDoc, pdfReader) -> {
            TGS_FuncMTCUtils.run(() -> {
                try (var zos = new FileOutputStream(pdfDstFile.toFile()); var pdfStamper = new PdfStamper(pdfReader, zos);) {
                    var count = TS_FilePdfOpenPdfUtilsPage.count(pdfReader);
                    IntStream.rangeClosed(1, count).forEachOrdered(p -> {
                        var offsetX = (pdfReader.getPageSize(p).getWidth() * (1 - scaleFactor)) / 2;
                        var offsetY = (pdfReader.getPageSize(p).getHeight() * (1 - scaleFactor)) / 2;
                        pdfStamper.getUnderContent(p).setLiteral(String.format("\nq %s 0 0 %s %s %s cm\nq\n", scaleFactor, scaleFactor, offsetX, offsetY));
                        pdfStamper.getOverContent(p).setLiteral("\nQ\nQ\n");
                    });
                }
            });
        });
    }
}
