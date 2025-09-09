package com.tugalsan.api.file.pdf.openpdf.server;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfName;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfString;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.parser.PdfTextExtractor;
import com.tugalsan.api.log.server.TS_Log;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.function.Supplier;

public class TS_FilePdfOpenPdfUtilsTextExtract {

    private TS_FilePdfOpenPdfUtilsTextExtract() {

    }

    final private static Supplier<TS_Log> d = StableValue.supplier(() -> TS_Log.of(TS_FilePdfOpenPdfUtilsTextExtract.class));

    public static void test() {
        d.get().cr("Text extraction");
        // step 1: create a document object
        var document = new Document();
        // step 2: write some text to the document
        var baos = writeTextToDocument(document);
        try {
            // step 3: extract the text
            var reader = new PdfReader(baos.toByteArray());
            var pdfTextExtractor = new PdfTextExtractor(reader);
            d.get().cr("Page 1 text: " + pdfTextExtractor.getTextFromPage(1));
            d.get().cr("Page 2 text: " + pdfTextExtractor.getTextFromPage(2));
            d.get().cr("Page 3 table cell text: " + pdfTextExtractor.getTextFromPage(3));
        } catch (DocumentException | IOException de) {
            d.get().ce(de.getMessage());
        }
    }

    private static ByteArrayOutputStream writeTextToDocument(Document document) {
        ByteArrayOutputStream baos = null;
        try {
            baos = new ByteArrayOutputStream();
            var writer = PdfWriter.getInstance(document, baos);
            document.open();
            writer.getInfo().put(PdfName.CREATOR, new PdfString(Document.getVersion()));
            document.add(new Paragraph("Text to extract"));
            document.newPage();
            document.add(new Paragraph("Text on page 2"));
            document.newPage();
            var table = new PdfPTable(3);
            table.addCell("Cell 1");
            table.addCell("Cell 2");
            table.addCell("Cell 3");
            document.add(table);
            document.close();
            try (var fos = new FileOutputStream("TextExtraction.pdf");) {
                fos.write(baos.toByteArray());
            }
        } catch (DocumentException | IOException de) {
            d.get().ce(de.getMessage());
        }
        return baos;
    }
}
