package com.tugalsan.api.file.pdf.openpdf.server;

import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.tugalsan.api.charset.client.TGS_CharSetCast;
import com.tugalsan.api.union.client.TGS_UnionExcuseVoid;
import com.tugalsan.api.unsafe.client.TGS_UnSafe;
import java.nio.file.Path;
import java.util.List;

public class TS_FilePdfOpenPdfUtilsImage {

    public static TGS_UnionExcuseVoid toPdf(Path dstPdf, List<Path> srcImages) {
        return toPdf(dstPdf, srcImages.toArray(Path[]::new));
    }

    public static TGS_UnionExcuseVoid toPdf(Path dstPdf, Path... srcImages) {
        return TGS_UnSafe.call(() -> {
            TS_FilePdfOpenPdfUtilsDocument.run_doc_with_writer(TS_FilePdfOpenPdfUtilsPage.PAGE_INFO_A4_PORT_0_0_0_0, dstPdf, (doc, writer) -> {
                TGS_UnSafe.run(() -> {
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
