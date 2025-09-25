package com.tugalsan.api.file.pdf.openpdf.server;

import module com.github.librepdf.openpdf;

public class TS_FilePdfOpenPdfUtilsVersion {

    private TS_FilePdfOpenPdfUtilsVersion() {

    }

    public static char isVersion(PdfReader reader) {
        return reader.getPdfVersion();
    }
}
