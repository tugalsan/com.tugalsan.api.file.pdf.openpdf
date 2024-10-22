package com.tugalsan.api.file.pdf.openpdf.server;

import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfStream;
import com.lowagie.text.pdf.PdfWriter;
import com.tugalsan.api.unsafe.client.TGS_UnSafe;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.util.stream.IntStream;

public class TS_FilePdfOpenPdfUtilsPageCompress {

    public static enum CompressionLevel {
        BEST, NORMAL, NONE
    }

    public static void set(PdfWriter pdfWriter, CompressionLevel cLvl) {
        if (cLvl == CompressionLevel.BEST) {
            pdfWriter.setFullCompression();
            pdfWriter.setCompressionLevel(PdfStream.BEST_COMPRESSION);
        }
        if (cLvl == CompressionLevel.NORMAL) {
            pdfWriter.setFullCompression();
            pdfWriter.setCompressionLevel(PdfStream.DEFAULT_COMPRESSION);
        }
        if (cLvl == CompressionLevel.NONE) {
            pdfWriter.setCompressionLevel(PdfStream.NO_COMPRESSION);
        }
    }

    public static void set(PdfCopy pdfCopy, CompressionLevel cLvl) {
        if (cLvl == CompressionLevel.BEST) {
            pdfCopy.setFullCompression();
            pdfCopy.setCompressionLevel(PdfStream.BEST_COMPRESSION);
        }
        if (cLvl == CompressionLevel.NORMAL) {
            pdfCopy.setFullCompression();
            pdfCopy.setCompressionLevel(PdfStream.DEFAULT_COMPRESSION);
        }
        if (cLvl == CompressionLevel.NONE) {
            pdfCopy.setCompressionLevel(PdfStream.NO_COMPRESSION);
        }
    }

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
