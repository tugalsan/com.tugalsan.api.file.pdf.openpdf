package com.tugalsan.api.file.pdf.openpdf.server;

import com.lowagie.text.pdf.PdfReader;

public class TS_FilePdfOpenPdfUtilsEncrypt {

    private TS_FilePdfOpenPdfUtilsEncrypt() {

    }

    public static boolean isEncrypted(PdfReader reader) {
        return reader.isEncrypted();
    }
}
