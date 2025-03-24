package com.tugalsan.api.file.pdf.openpdf.server;

import com.tugalsan.api.union.client.TGS_UnionExcuseVoid;
import com.tugalsan.api.function.client.maythrowexceptions.checked.TGS_FuncMTCUtils;
import java.nio.file.Files;
import java.nio.file.Path;
import org.jsoup.Jsoup;
import org.xhtmlrenderer.pdf.ITextRenderer;

@Deprecated //HOW about using com.tugalsan.api.file.pdf.pdfbox3.server.TS_FilePdfBox3UtilsHtml.toPdf(Path srcHTM, Path dstPDF)
public class TS_FilePdfOpenPdfUtilsHtml {

//    final private static TS_Log d = TS_Log.of(TS_FilePdfOpenPdfUtilsHtml.class);

    public static TGS_UnionExcuseVoid toPdf(Path pathHtmlInput, Path pathPdfOutput) {
        return TGS_FuncMTCUtils.call(() -> {
            var html = new String(Files.readAllBytes(pathHtmlInput));
            var doc = Jsoup.parse(html);
            doc.outputSettings().syntax(org.jsoup.nodes.Document.OutputSettings.Syntax.html);
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
