package com.tugalsan.api.file.pdf.openpdf.server;

import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfStream;
import com.lowagie.text.pdf.PdfWriter;
import com.tugalsan.api.function.client.maythrowexceptions.checked.TGS_FuncMTCUtils;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.util.stream.IntStream;

public class TS_FilePdfOpenPdfUtilsPageCompress {

    public static enum CompressionLevel {
        BEST, NORMAL, NONE
    }

    public static void set(PdfWriter pdfWriter, CompressionLevel cLvl) {
        if (cLvl == null) {
            pdfWriter.setCompressionLevel(PdfStream.NO_COMPRESSION);
        } else {
            switch (cLvl) {
                case BEST -> {
                    pdfWriter.setFullCompression();
                    pdfWriter.setCompressionLevel(PdfStream.BEST_COMPRESSION);
                }
                case NORMAL -> {
                    pdfWriter.setFullCompression();
                    pdfWriter.setCompressionLevel(PdfStream.DEFAULT_COMPRESSION);
                }
                default -> {//NONE
                    pdfWriter.setCompressionLevel(PdfStream.NO_COMPRESSION);
                }
            }
        }
    }

    public static void set(PdfCopy pdfCopy, CompressionLevel cLvl) {
        if (cLvl == null) {
            pdfCopy.setCompressionLevel(PdfStream.NO_COMPRESSION);
        } else {
            switch (cLvl) {
                case BEST -> {
                    pdfCopy.setFullCompression();
                    pdfCopy.setCompressionLevel(PdfStream.BEST_COMPRESSION);
                }
                case NORMAL -> {
                    pdfCopy.setFullCompression();
                    pdfCopy.setCompressionLevel(PdfStream.DEFAULT_COMPRESSION);
                }
                default -> {//NONE
                    pdfCopy.setCompressionLevel(PdfStream.NO_COMPRESSION);
                }
            }
        }
    }

    public static void compress(Path pdfSrcFile, Path pdfDstFile) {
        TS_FilePdfOpenPdfUtilsDocument.run_doc_with_reader(pdfSrcFile, (srcDoc, pdfReader) -> {
            TGS_FuncMTCUtils.run(() -> {
                try (var zos = new FileOutputStream(pdfDstFile.toFile()); var pdfStamper = new PdfStamper(pdfReader, zos, '5');) {
                    pdfStamper.getWriter().setCompressionLevel(9);
                    var count = pdfReader.getNumberOfPages();
                    IntStream.rangeClosed(1, count).forEachOrdered(p -> {
                        TGS_FuncMTCUtils.run(() -> {
                            pdfReader.setPageContent(p, pdfReader.getPageContent(p));
                        });
                    });
                    pdfStamper.setFullCompression();
                }
            });
        });

    }
}
