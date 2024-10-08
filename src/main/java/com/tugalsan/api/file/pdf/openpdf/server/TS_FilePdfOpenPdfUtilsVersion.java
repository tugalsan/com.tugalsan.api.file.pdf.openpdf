package com.tugalsan.api.file.pdf.openpdf.server;

import com.lowagie.text.pdf.PdfReader;

public class TS_FilePdfOpenPdfUtilsVersion {

    public static char isVersion(PdfReader reader) {
        return reader.getPdfVersion();
    }
}
