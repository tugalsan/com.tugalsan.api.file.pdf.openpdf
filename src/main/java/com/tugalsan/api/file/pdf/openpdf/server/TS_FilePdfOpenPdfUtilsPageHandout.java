package com.tugalsan.api.file.pdf.openpdf.server;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import com.lowagie.text.Rectangle;
import com.lowagie.text.error_messages.MessageLocalization;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;
import com.tugalsan.api.function.client.TGS_FuncUtils;
import com.tugalsan.api.log.server.TS_Log;
import java.io.FileOutputStream;
import java.io.IOException;

public class TS_FilePdfOpenPdfUtilsPageHandout {

    private TS_FilePdfOpenPdfUtilsPageHandout() {

    }

    final private static TS_Log d = TS_Log.of(TS_FilePdfOpenPdfUtilsPageHandout.class);

    public static void test(String[] args) {
        if (args.length != 3) {
            d.ce("arguments: srcfile destfile pages");
        } else {
            try {
                var pages = Integer.parseInt(args[2]);
                if (pages < 2 || pages > 8) {
                    throw new DocumentException(MessageLocalization
                            .getComposedMessage("you.can.t.have.1.pages.on.one.page.minimum.2.maximum.8", pages));
                }

                float x1 = 30f;
                float x2 = 280f;
                float x3 = 320f;
                float x4 = 565f;

                float[] y1 = new float[pages];
                float[] y2 = new float[pages];

                float height = (778f - (20f * (pages - 1))) / pages;
                y1[0] = 812f;
                y2[0] = 812f - height;

                for (var i = 1; i < pages; i++) {
                    y1[i] = y2[i - 1] - 20f;
                    y2[i] = y1[i] - height;
                }

                // we create a reader for a certain document
                var reader = new PdfReader(args[0]);
                // we retrieve the total number of pages
                var n = reader.getNumberOfPages();
                d.cr("There are " + n + " pages in the original file.");

                // step 1: creation of a document-object
                var document = new Document(PageSize.A4);
                // step 2: we create a writer that listens to the document
                var writer = PdfWriter.getInstance(document, new FileOutputStream(args[1]));
                // step 3: we open the document
                document.open();
                var cb = writer.getDirectContent();
                PdfImportedPage page;
                int rotation;
                int i = 0;
                int p = 0;
                // step 4: we add content
                while (i < n) {
                    i++;
                    Rectangle rect = reader.getPageSizeWithRotation(i);
                    float factorx = (x2 - x1) / rect.getWidth();
                    float factory = (y1[p] - y2[p]) / rect.getHeight();
                    float factor = (factorx < factory ? factorx : factory);
                    float dx = (factorx == factor ? 0f : ((x2 - x1) - rect.getWidth() * factor) / 2f);
                    float dy = (factory == factor ? 0f : ((y1[p] - y2[p]) - rect.getHeight() * factor) / 2f);
                    page = writer.getImportedPage(reader, i);
                    rotation = reader.getPageRotation(i);
                    if (rotation == 90 || rotation == 270) {
                        cb.addTemplate(page, 0, -factor, factor, 0, x1 + dx, y2[p] + dy + rect.getHeight() * factor);
                    } else {
                        cb.addTemplate(page, factor, 0, 0, factor, x1 + dx, y2[p] + dy);
                    }
                    cb.setRGBColorStroke(0xC0, 0xC0, 0xC0);
                    cb.rectangle(x3 - 5f, y2[p] - 5f, x4 - x3 + 10f, y1[p] - y2[p] + 10f);
                    for (float l = y1[p] - 19; l > y2[p]; l -= 16) {
                        cb.moveTo(x3, l);
                        cb.lineTo(x4, l);
                    }
                    cb.rectangle(x1 + dx, y2[p] + dy, rect.getWidth() * factor, rect.getHeight() * factor);
                    cb.stroke();
                    d.cr("Processed page " + i);
                    p++;
                    if (p == pages) {
                        p = 0;
                        document.newPage();
                    }
                }
                // step 5: we close the document
                document.close();
            } catch (DocumentException | IOException | NumberFormatException e) {
                TGS_FuncUtils.throwIfInterruptedException(e);
                d.ce(e.getClass().getName() + ": " + e.getMessage());
            }
        }
    }
}
