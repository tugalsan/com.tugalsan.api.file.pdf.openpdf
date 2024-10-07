package com.tugalsan.api.file.pdf.openpdf.server;

import com.tugalsan.api.function.client.TGS_FuncEffectivelyFinal;
import com.tugalsan.api.log.server.TS_Log;
import com.tugalsan.api.unsafe.client.TGS_UnSafe;
import java.nio.file.Path;

@Deprecated //TODO Migration from TS_FilePdfItext to here
public class TS_FilePdfBox3 {

    final private static TS_Log d = TS_Log.of(TS_FilePdfBox3.class);

    public Path getFile() {
        return file;
    }
    private final Path file;

    public PDDocument getWriter() {
        return document;
    }

    public PDDocument getDocument() {
        return document;
    }
    private PDDocument document;

    public TS_FilePdfBox3(Path file) {
        this.file = file;
    }

    public void createNewPage(int pageSizeAX, boolean landscape, Integer marginLeft0, Integer marginRight0, Integer marginTop0, Integer marginBottom0) {        
    }
    
}
