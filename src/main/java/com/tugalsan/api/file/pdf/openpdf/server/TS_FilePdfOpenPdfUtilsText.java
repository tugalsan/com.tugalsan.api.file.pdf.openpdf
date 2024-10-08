package com.tugalsan.api.file.pdf.openpdf.server;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfName;
import com.lowagie.text.pdf.PdfString;
import com.lowagie.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import com.tugalsan.api.log.server.TS_Log;
import com.tugalsan.api.union.client.TGS_UnionExcuseVoid;
import com.tugalsan.api.unsafe.client.TGS_UnSafe;
import java.io.IOException;
import java.nio.file.Path;

public class TS_FilePdfOpenPdfUtilsText {

    final private static TS_Log d = TS_Log.of(TS_FilePdfOpenPdfUtilsText.class);

    public static TGS_UnionExcuseVoid test(Path dstPdf) {
        return TGS_UnSafe.call(() -> {
            TS_FilePdfOpenPdfUtilsDocument.run_doc(doc -> {
                TGS_UnSafe.run(() -> {
                    var pdfWriter = PdfWriter.getInstance(doc, new FileOutputStream(dstPdf.toFile()));
                    doc.open();
                    pdfWriter.getInfo().put(PdfName.CREATOR, new PdfString(Document.getVersion()));
                    doc.add(new Paragraph("Hello World"));
                });
            });
            return TGS_UnionExcuseVoid.ofVoid();
        }, e -> TGS_UnionExcuseVoid.ofExcuse(e));
    }
    
    public static void main(String[] args) {

        System.out.println("the Paragraph object");

        // step 1: creation of a document-object
        Document document = new Document();
        try {
            // step 2:
            // we create a writer that listens to the document
            PdfWriter.getInstance(document, new FileOutputStream("Paragraphs.pdf"));

            // step 3: we open the document
            document.open();
            // step 4:
            Paragraph p1 = new Paragraph(new Chunk(
                    "This is my first paragraph. ",
                    FontFactory.getFont(FontFactory.HELVETICA, 10)));
            p1.add("The leading of this paragraph is calculated automagically. ");
            p1.add("The default leading is 1.5 times the fontsize. ");
            p1.add(new Chunk("You can add chunks "));
            p1.add(new Phrase("or you can add phrases. "));
            p1.add(new Phrase(
                    "Unless you change the leading with the method setLeading, the leading doesn't change if you add text with another leading. This can lead to some problems.",
                    FontFactory.getFont(FontFactory.HELVETICA, 18)));
            document.add(p1);
            Paragraph p2 = new Paragraph(new Phrase(
                    "This is my second paragraph. ", FontFactory.getFont(
                    FontFactory.HELVETICA, 12)));
            p2.add("As you can see, it started on a new line.");
            document.add(p2);
            Paragraph p3 = new Paragraph("This is my third paragraph.",
                    FontFactory.getFont(FontFactory.HELVETICA, 12));
            document.add(p3);
        } catch (DocumentException | IOException de) {
            System.err.println(de.getMessage());
        }

        // step 5: we close the document
        document.close();
    }

}
