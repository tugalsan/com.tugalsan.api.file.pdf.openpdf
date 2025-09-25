package com.tugalsan.api.file.pdf.openpdf.server;

import module com.github.librepdf.openpdf;

public class TS_FilePdfOpenPdfUtilsEncrypt {

    private TS_FilePdfOpenPdfUtilsEncrypt() {

    }

    public static boolean isEncrypted(PdfReader reader) {
        return reader.isEncrypted();
    }
}
