package com.tugalsan.api.file.pdf.openpdf.server;

import com.lowagie.text.pdf.PdfStamper;
import com.tugalsan.api.unsafe.client.TGS_UnSafe;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.util.stream.IntStream;

public class TS_FilePdfOpenPdfUtilsPageCompress {

    public static void compress(Path pdfSrcFile, Path pdfDstFile) {
        TS_FilePdfOpenPdfUtilsDocument.run_doc_with_reader(pdfSrcFile, (srcDoc, pdfReader) -> {
            TGS_UnSafe.run(() -> {
                try (var zos = new FileOutputStream(pdfDstFile.toFile()); var pdfStamper = new PdfStamper(pdfReader, zos, '5');) {
                    pdfStamper.getWriter().setCompressionLevel(9);
                    var count = pdfReader.getNumberOfPages();
                    IntStream.rangeClosed(1, count).forEachOrdered(p -> {
                        TGS_UnSafe.run(() -> {
                            pdfReader.setPageContent(p, pdfReader.getPageContent(p));
                        });
                    });
                    pdfStamper.setFullCompression();
                }
            });
        });

    }
}
