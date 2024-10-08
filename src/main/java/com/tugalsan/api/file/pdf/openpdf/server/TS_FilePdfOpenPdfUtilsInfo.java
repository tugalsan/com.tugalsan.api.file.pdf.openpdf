package com.tugalsan.api.file.pdf.openpdf.server;

import com.lowagie.text.pdf.PdfDate;
import com.tugalsan.api.union.client.TGS_UnionExcuse;
import java.nio.file.Path;
import java.util.Date;
import java.util.Map;

public class TS_FilePdfOpenPdfUtilsInfo {

    public static record Info(String title, String Author, String subject, String producer, Date dateCreate, Date datemodified) {

    }

    public TGS_UnionExcuse<Info> get(Path srcPdf) {
        return get(srcPdf, null);
    }

    public TGS_UnionExcuse<Info> get(Path srcPdf, byte[] password) {
        var u = TS_FilePdfOpenPdfUtilsDocument.call_doc_with_reader(srcPdf, password, (doc, reader) -> {
            Map<String, String> pdfinfo = reader.getInfo();
            return TGS_UnionExcuse.of(new Info(
                    pdfinfo.get("Title"),
                    pdfinfo.get("Author"),
                    pdfinfo.get("Subject"),
                    pdfinfo.get("Producer"),
                    PdfDate.decode(pdfinfo.get("CreationDate")).getTime(),
                    PdfDate.decode(pdfinfo.get("ModDate")).getTime()
            ));
        });
        if (u.isExcuse()) {
            return u.toExcuse();
        } else {
            return u.value();
        }
    }
}
