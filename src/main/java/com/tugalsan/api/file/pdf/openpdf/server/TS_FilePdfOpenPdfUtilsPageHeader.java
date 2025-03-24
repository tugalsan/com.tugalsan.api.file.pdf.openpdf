package com.tugalsan.api.file.pdf.openpdf.server;

import com.lowagie.text.Paragraph;
import com.tugalsan.api.function.client.maythrowexceptions.checked.TGS_FuncMTCUtils;
import java.nio.file.Path;
import java.util.stream.IntStream;

public class TS_FilePdfOpenPdfUtilsPageHeader {

    public static void addHeader(TS_FilePdfOpenPdfUtilsPageCompress.CompressionLevel cLvl, Path pdfSrcFile, Path pdfDstFile) {
        TS_FilePdfOpenPdfUtilsDocument.run_doc_with_reader(pdfSrcFile, (srcDoc, pdfReader) -> {
            TS_FilePdfOpenPdfUtilsDocument.run_doc_with_copy(cLvl, pdfDstFile, (dstDoc, pdfCopy) -> {
                var count = TS_FilePdfOpenPdfUtilsPage.count(pdfReader);
                var writerContent = pdfCopy.getDirectContent();
                IntStream.rangeClosed(1, count).forEachOrdered(pageNumber -> {
                    TGS_FuncMTCUtils.run(() -> {
                        var page = pdfCopy.getImportedPage(pdfReader, 1);
                        dstDoc.newPage();
                        writerContent.addTemplate(page, 0, 0);
                        dstDoc.add(new Paragraph("my timestamp"));
                    });
                });
//            document.addHeader("Header Name", "Header Content");
            });
        });
    }
}
