package com.tugalsan.api.file.pdf.openpdf.server;

import module com.tugalsan.api.function;
import module com.github.librepdf.openpdf;
import java.io.*;
import java.nio.file.*;
import java.util.stream.*;

public class TS_FilePdfOpenPdfUtilsPageRotate {

    private TS_FilePdfOpenPdfUtilsPageRotate() {

    }

    public static void rotate(Path pdfSrcFile, Path pdfDstFile, int degree) {
        TS_FilePdfOpenPdfUtilsDocument.run_doc_with_reader(pdfSrcFile, (srcDoc, pdfReader) -> {
            TGS_FuncMTCUtils.run(() -> {
                try (var zos = new FileOutputStream(pdfDstFile.toFile())) {
                    var count = TS_FilePdfOpenPdfUtilsPage.count(pdfReader);
                    IntStream.rangeClosed(1, count).parallel().forEach(i -> {
                        var dictI = pdfReader.getPageN(i);
                        var num = dictI.getAsNumber(PdfName.ROTATE);
                        dictI.put(PdfName.ROTATE, num == null ? new PdfNumber(degree) : new PdfNumber((num.intValue() + degree) % 360));
                    });
                    try (var pdfStamper = new PdfStamper(pdfReader, zos);) {
                    }
                }
            });
        });
    }
}
