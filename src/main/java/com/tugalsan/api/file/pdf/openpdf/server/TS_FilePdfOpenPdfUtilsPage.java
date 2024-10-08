package com.tugalsan.api.file.pdf.openpdf.server;

import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfDictionary;
import com.lowagie.text.pdf.PdfReader;
import com.tugalsan.api.union.client.TGS_UnionExcuse;
import java.nio.file.Path;

public class TS_FilePdfOpenPdfUtilsPage {

    public static PdfDictionary getPage(PdfReader reader, int pageIdx) {
        return reader.getPageN(pageIdx);
    }

    public static Rectangle getPageSizeWithRotation(PdfReader reader, PdfDictionary page) {
        return reader.getPageSizeWithRotation(page);
    }

    public static Rectangle getPageSizeWithRotation(PdfReader reader, int pageIdx) {
        return reader.getPageSizeWithRotation(pageIdx);
    }

    public static int getPageRotation(PdfReader reader, int pageIdx) {
        return reader.getPageRotation(pageIdx);
    }

    public static Rectangle getPageSize(PdfReader reader, PdfDictionary page) {
        return reader.getPageSize(page);
    }

    public static Rectangle getPageSize(PdfReader reader, int pageIdx) {
        return reader.getPageSize(pageIdx);
    }

    public static int count(PdfReader reader) {
        return reader.getNumberOfPages();
    }

    public static TGS_UnionExcuse<Integer> count(Path srcfile) {
        return TS_FilePdfOpenPdfUtilsDocument.call_doc_with_reader(srcfile, (doc, reader) -> {
            return count(reader);
        });
    }

}
