package com.tugalsan.api.file.pdf.openpdf.server;

import com.tugalsan.api.log.server.TS_Log;
import java.nio.file.Path;
import com.lowagie.text.Document;

//https://github.com/LibrePDF/OpenPDF/wiki/Tutorial
//https://web.archive.org/web/20090703162811/http://itextdocs.lowagie.com/tutorial/
@Deprecated //TODO Migration from TS_FilePdfItext to here
public class TS_FilePdfOpenPdf {

    final private static TS_Log d = TS_Log.of(TS_FilePdfOpenPdf.class);

    public Path getFile() {
        return file;
    }
    private final Path file;

    public Document getWriter() {
        return document;
    }

    public Document getDocument() {
        return document;
    }
    private Document document;

    public TS_FilePdfOpenPdf(Path file) {
        this.file = file;
    }

    public void createNewPage(int pageSizeAX, boolean landscape, Integer marginLeft0, Integer marginRight0, Integer marginTop0, Integer marginBottom0) {
    }

}
