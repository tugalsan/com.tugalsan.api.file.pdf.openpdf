package com.tugalsan.api.file.pdf.openpdf.server;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfName;
import com.lowagie.text.pdf.PdfString;
import com.lowagie.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.io.IOException;

import com.tugalsan.api.log.server.TS_Log;
import java.nio.file.Path;

//https://github.com/LibrePDF/OpenPDF/wiki/Tutorial
public class TS_FilePdfOpenPdfUtils {

    final private static TS_Log d = TS_Log.of(TS_FilePdfOpenPdfUtils.class);

    public static void test_openpdf_hello() {
        var pdfBase = Path.of("C:\\Users\\me\\Desktop\\PDF");
        var pdfFile = pdfBase.resolve("HelloWorld.pdf");
        var doc = new Document();
        try {
            var instance = PdfWriter.getInstance(doc, new FileOutputStream(pdfFile.toFile()));
            doc.open();
            instance.getInfo().put(PdfName.CREATOR, new PdfString(Document.getVersion()));
            doc.add(new Paragraph("Hello World"));
        } catch (DocumentException | IOException de) {
            System.err.println(de.getMessage());
        } finally {
            doc.close();
        }
    }

}
