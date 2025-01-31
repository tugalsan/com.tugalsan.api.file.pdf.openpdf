package com.tugalsan.api.file.pdf.openpdf.server;

import com.tugalsan.api.union.client.TGS_UnionExcuseVoid;
import com.tugalsan.api.unsafe.client.TGS_UnSafe;
import java.nio.file.Files;
import java.nio.file.Path;
import org.jsoup.Jsoup;
import org.xhtmlrenderer.pdf.ITextRenderer;

public class TS_FilePdfOpenPdfUtilsHtml {

//    final private static TS_Log d = TS_Log.of(TS_FilePdfOpenPdfUtilsHtml.class);

    public TGS_UnionExcuseVoid toPdf(Path pathHtmlInput, Path pathPdfOutput) {
        return TGS_UnSafe.call(() -> {
            var html = new String(Files.readAllBytes(pathHtmlInput));
            var doc = Jsoup.parse(html);
            doc.outputSettings().syntax(org.jsoup.nodes.Document.OutputSettings.Syntax.xml);
            var renderer = new ITextRenderer();
            renderer.setDocumentFromString(doc.html());
            renderer.layout();
            try (var os = Files.newOutputStream(pathPdfOutput)) {
                renderer.createPDF(os);
            }
            return TGS_UnionExcuseVoid.ofVoid();
        }, e -> TGS_UnionExcuseVoid.ofExcuse(e));
    }
}
