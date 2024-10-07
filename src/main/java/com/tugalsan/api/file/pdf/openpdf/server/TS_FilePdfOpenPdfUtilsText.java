package com.tugalsan.api.file.pdf.openpdf.server;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfName;
import com.lowagie.text.pdf.PdfString;
import com.lowagie.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import com.tugalsan.api.log.server.TS_Log;
import com.tugalsan.api.union.client.TGS_UnionExcuseVoid;
import com.tugalsan.api.unsafe.client.TGS_UnSafe;
import java.nio.file.Path;

public class TS_FilePdfOpenPdfUtilsText {

    final private static TS_Log d = TS_Log.of(TS_FilePdfOpenPdfUtilsText.class);

    public static TGS_UnionExcuseVoid test(Path dstPdf) {
        return TGS_UnSafe.call(() -> {
            TS_FilePdfOpenPdfUtilsDocument.run_new_than_save(doc -> {
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

}
