package com.tugalsan.api.file.pdf.openpdf.server;

import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfName;
import com.lowagie.text.pdf.PdfString;
import com.lowagie.text.pdf.PdfWriter;
import com.tugalsan.api.charset.client.TGS_CharSetCast;
import com.tugalsan.api.union.client.TGS_UnionExcuseVoid;
import com.tugalsan.api.unsafe.client.TGS_UnSafe;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.util.List;

public class TS_FilePdfOpenPdfUtilsImage {

    public static TGS_UnionExcuseVoid test(Path dstPdf, List<Path> srcImages) {
        return test(dstPdf, srcImages.toArray(Path[]::new));
    }

    public static TGS_UnionExcuseVoid test(Path dstPdf, Path... srcImages) {
        return TGS_UnSafe.call(() -> {
            TS_FilePdfOpenPdfUtilsDocument.run_new_than_save(doc -> {
                TGS_UnSafe.run(() -> {
                    var pdfWriter = PdfWriter.getInstance(doc, new FileOutputStream(dstPdf.toFile()));
                    doc.open();
                    pdfWriter.getInfo().put(PdfName.CREATOR, new PdfString(Document.getVersion()));
                    for (var srcImage : srcImages) {
                        var pathImageStr = srcImage.toAbsolutePath().toString();
                        doc.add(new Paragraph(pathImageStr));
                        doc.add(Image.getInstance(pathImageStr));
                    }
                });
            });
            return TGS_UnionExcuseVoid.ofVoid();
        }, e -> TGS_UnionExcuseVoid.ofExcuse(e));
    }

    public static boolean isSupported(Path imgFile) {
        var fn = TGS_CharSetCast.current().toLowerCase(imgFile.getFileName().toString());
        return fn.endsWith(".jpg") || fn.endsWith(".jpeg") || fn.endsWith(".tif") || fn.endsWith(".tiff") || fn.endsWith(".gif") || fn.endsWith(".bmp") || fn.endsWith(".png") || fn.endsWith(".wmf");
    }
}
