package com.tugalsan.api.file.pdf.openpdf.server;

import module com.tugalsan.api.union;
import module com.tugalsan.api.function;
import module org.jsoup;
import module flying.saucer.pdf;
import java.nio.file.*;

@Deprecated //HOW about using com.tugalsan.api.file.pdf.pdfbox3.server.TS_FilePdfBox3UtilsHtml.toPdf(Path srcHTM, Path dstPDF)
public class TS_FilePdfOpenPdfUtilsHtml {

    private TS_FilePdfOpenPdfUtilsHtml() {

    }

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
