package com.tugalsan.api.file.pdf.openpdf.server;

import com.lowagie.text.Document;
import com.tugalsan.api.function.client.TGS_Func_In1;
import com.tugalsan.api.union.client.TGS_UnionExcuseVoid;
import com.tugalsan.api.unsafe.client.TGS_UnSafe;

public class TS_FilePdfOpenPdfUtilsDocument {

    public static TGS_UnionExcuseVoid run_new_than_save(TGS_Func_In1<Document> doc) {
        return TGS_UnSafe.call(() -> {
            try (var _doc = new Document()) {
                doc.run(_doc);
                return TGS_UnionExcuseVoid.ofVoid();
            }
        }, e -> TGS_UnionExcuseVoid.ofExcuse(e));
    }
}
