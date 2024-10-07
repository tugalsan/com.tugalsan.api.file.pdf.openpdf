package com.tugalsan.api.file.pdf.openpdf.server;

import com.lowagie.text.pdf.PdfDate;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.RandomAccessFileOrArray;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class TS_FilePdfOpenPdfUtilsInfo {

    public String createTextFromPDF(File file) {
        int page = 1;
        PdfReader reader = null;

        try (RandomAccessFileOrArray raf = new RandomAccessFileOrArray(file.getAbsolutePath())) {
            reader = new PdfReader(raf, null);
            Map<String, String> pdfinfo = reader.getInfo();

            StringBuilder sb = new StringBuilder();
            sb.append("<html>=== Document Information ===<p>");
            sb.append(reader.getCropBox(page).getHeight()).append("*").append(reader.getCropBox(page).getWidth())
                    .append("<p>");
            sb.append("PDF Version: ").append(reader.getPdfVersion()).append("<p>");
            sb.append("Number of pages: ").append(reader.getNumberOfPages()).append("<p>");
            sb.append("Number of PDF objects: ").append(reader.getXrefSize()).append("<p>");
            sb.append("File length: ").append(reader.getFileLength()).append("<p>");
            sb.append("Encrypted= ").append(reader.isEncrypted()).append("<p>");
            if (pdfinfo.get("Title") != null) {
                sb.append("Title= ").append(pdfinfo.get("Title")).append("<p>");
            }
            if (pdfinfo.get("Author") != null) {
                sb.append("Author= ").append(pdfinfo.get("Author")).append("<p>");
            }
            if (pdfinfo.get("Subject") != null) {
                sb.append("Subject= ").append(pdfinfo.get("Subject")).append("<p>");
            }
            if (pdfinfo.get("Producer") != null) {
                sb.append("Producer= ").append(pdfinfo.get("Producer")).append("<p>");
            }
            if (pdfinfo.get("ModDate") != null) {
                sb.append("ModDate= ").append(PdfDate.decode(pdfinfo.get("ModDate"))
                        .getTime()).append("<p>");
            }
            if (pdfinfo.get("CreationDate") != null) {
                sb.append("CreationDate= ").append(PdfDate.decode(
                        pdfinfo.get("CreationDate"))
                        .getTime()).append("<p>");
            }
            sb.append("</html>");
            return sb.toString();
        } catch (IOException ex) {
            return ex.getMessage();
        }
    }
}
