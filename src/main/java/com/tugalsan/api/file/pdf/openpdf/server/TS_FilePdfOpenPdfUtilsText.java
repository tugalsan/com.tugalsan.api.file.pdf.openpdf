package com.tugalsan.api.file.pdf.openpdf.server;

import module com.tugalsan.api.log;
import module com.github.librepdf.openpdf;
import java.io.*;

public class TS_FilePdfOpenPdfUtilsText {

    private TS_FilePdfOpenPdfUtilsText() {

    }

    final private static TS_Log d = TS_Log.of(TS_FilePdfOpenPdfUtilsText.class);

    public static void test() {
        d.cr("the Paragraph object");
        // step 1: creation of a document-object
        try (var document = new Document()) {
            // step 2:
            // we create a writer that listens to the document
            PdfWriter.getInstance(document, new FileOutputStream("Paragraphs.pdf"));
            // step 3: we open the document
            document.open();
            // step 4:
            var p1 = new Paragraph(new Chunk(
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
            var p2 = new Paragraph(new Phrase(
                    "This is my second paragraph. ", FontFactory.getFont(
                            FontFactory.HELVETICA, 12)));
            p2.add("As you can see, it started on a new line.");
            document.add(p2);
            var p3 = new Paragraph("This is my third paragraph.",
                    FontFactory.getFont(FontFactory.HELVETICA, 12));
            document.add(p3);
        } catch (DocumentException | IOException de) {
            d.ce(de.getMessage());
        }
    }

}
