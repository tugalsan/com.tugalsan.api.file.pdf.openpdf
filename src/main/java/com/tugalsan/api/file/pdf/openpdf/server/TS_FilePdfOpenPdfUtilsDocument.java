package com.tugalsan.api.file.pdf.openpdf.server;

import com.lowagie.text.Document;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfName;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfString;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.RandomAccessFileOrArray;
import com.tugalsan.api.function.client.TGS_Func_In1;
import com.tugalsan.api.function.client.TGS_Func_In2;
import com.tugalsan.api.function.client.TGS_Func_OutTyped_In1;
import com.tugalsan.api.function.client.TGS_Func_OutTyped_In2;
import com.tugalsan.api.union.client.TGS_UnionExcuse;
import com.tugalsan.api.union.client.TGS_UnionExcuseVoid;
import com.tugalsan.api.unsafe.client.TGS_UnSafe;
import java.nio.file.Files;
import java.nio.file.Path;

public class TS_FilePdfOpenPdfUtilsDocument {

    public static TGS_UnionExcuseVoid run_doc(TGS_Func_In1<Document> doc) {
        return TGS_UnSafe.call(() -> {
            try (var _doc = new Document()) {
                doc.run(_doc);
                return TGS_UnionExcuseVoid.ofVoid();
            }
        }, e -> TGS_UnionExcuseVoid.ofExcuse(e));
    }

    public static <T> TGS_UnionExcuse<T> call_doc(TGS_Func_OutTyped_In1<T, Document> doc) {
        return TGS_UnSafe.call(() -> {
            try (var _doc = new Document()) {
                return TGS_UnionExcuse.of(doc.call(_doc));
            }
        }, e -> TGS_UnionExcuse.ofExcuse(e));
    }

    public static TGS_UnionExcuseVoid run_doc(Rectangle defaultPageSizeWithRotation, TGS_Func_In1<Document> doc) {
        return TGS_UnSafe.call(() -> {
            try (var _doc = new Document(defaultPageSizeWithRotation)) {
                doc.run(_doc);
                return TGS_UnionExcuseVoid.ofVoid();
            }
        }, e -> TGS_UnionExcuseVoid.ofExcuse(e));
    }

    public static <T> TGS_UnionExcuse<T> call_doc(Rectangle defaultPageSizeWithRotation, TGS_Func_OutTyped_In1<T, Document> doc) {
        return TGS_UnSafe.call(() -> {
            try (var _doc = new Document(defaultPageSizeWithRotation)) {
                return TGS_UnionExcuse.of(doc.call(_doc));
            }
        }, e -> TGS_UnionExcuse.ofExcuse(e));
    }

    public static TGS_UnionExcuseVoid run_doc_with_writer(Path dstPdf, TGS_Func_In2<Document, PdfWriter> doc_writer) {
        return TGS_UnSafe.call(() -> {
            try (var _doc = new Document()) {
                var writer = PdfWriter.getInstance(_doc, Files.newOutputStream(dstPdf));
                _doc.open();
                writer.getInfo().put(PdfName.CREATOR, new PdfString(Document.getVersion()));
                doc_writer.run(_doc, writer);
                return TGS_UnionExcuseVoid.ofVoid();
            }
        }, e -> TGS_UnionExcuseVoid.ofExcuse(e));
    }

    public static <T> TGS_UnionExcuse<T> call_doc_with_writer(Path dstPdf, TGS_Func_OutTyped_In2<T, Document, PdfWriter> doc_writer) {
        return TGS_UnSafe.call(() -> {
            try (var _doc = new Document()) {
                var writer = PdfWriter.getInstance(_doc, Files.newOutputStream(dstPdf));
                _doc.open();
                writer.getInfo().put(PdfName.CREATOR, new PdfString(Document.getVersion()));
                return TGS_UnionExcuse.of(doc_writer.call(_doc, writer));
            }
        }, e -> TGS_UnionExcuse.ofExcuse(e));
    }

    public static TGS_UnionExcuseVoid run_doc_with_writer(Rectangle defaultPageSizeWithRotation, Path dstPdf, TGS_Func_In2<Document, PdfWriter> doc_writer) {
        return TGS_UnSafe.call(() -> {
            try (var _doc = new Document(defaultPageSizeWithRotation)) {
                var writer = PdfWriter.getInstance(_doc, Files.newOutputStream(dstPdf));
                _doc.open();
                writer.getInfo().put(PdfName.CREATOR, new PdfString(Document.getVersion()));
                doc_writer.run(_doc, writer);
                return TGS_UnionExcuseVoid.ofVoid();
            }
        }, e -> TGS_UnionExcuseVoid.ofExcuse(e));
    }

    public static <T> TGS_UnionExcuse<T> call_doc_with_writer(Rectangle defaultPageSizeWithRotation, Path dstPdf, TGS_Func_OutTyped_In2<T, Document, PdfWriter> doc_writer) {
        return TGS_UnSafe.call(() -> {
            try (var _doc = new Document(defaultPageSizeWithRotation)) {
                var writer = PdfWriter.getInstance(_doc, Files.newOutputStream(dstPdf));
                _doc.open();
                writer.getInfo().put(PdfName.CREATOR, new PdfString(Document.getVersion()));
                return TGS_UnionExcuse.of(doc_writer.call(_doc, writer));
            }
        }, e -> TGS_UnionExcuse.ofExcuse(e));
    }

    public static TGS_UnionExcuseVoid run_doc_with_reader(Path srcPdf, TGS_Func_In2<Document, PdfReader> doc_reader) {
        return run_doc_with_reader(srcPdf, null, doc_reader);
    }

    public static <T> TGS_UnionExcuse<T> call_doc_with_reader(Path srcPdf, TGS_Func_OutTyped_In2<T, Document, PdfReader> doc_reader) {
        return call_doc_with_reader(srcPdf, null, doc_reader);
    }

    public static TGS_UnionExcuseVoid run_doc_with_reader(Path srcPdf, byte[] password, TGS_Func_In2<Document, PdfReader> doc_reader) {
        return TGS_UnSafe.call(() -> {
            try (var _doc = new Document()) {
                try (var raf = new RandomAccessFileOrArray(Files.newInputStream(srcPdf))) {
                    var reader = new PdfReader(raf, password);
                    doc_reader.run(_doc, reader);
                    return TGS_UnionExcuseVoid.ofVoid();
                }
            }
        }, e -> TGS_UnionExcuseVoid.ofExcuse(e));
    }

    public static <T> TGS_UnionExcuse<T> call_doc_with_reader(Path srcPdf, byte[] password, TGS_Func_OutTyped_In2<T, Document, PdfReader> doc_reader) {
        return TGS_UnSafe.call(() -> {
            try (var _doc = new Document()) {
                try (var raf = new RandomAccessFileOrArray(Files.newInputStream(srcPdf))) {
                    var reader = new PdfReader(raf, password);
                    return TGS_UnionExcuse.of(doc_reader.call(_doc, reader));
                }
            }
        }, e -> TGS_UnionExcuse.ofExcuse(e));
    }
}
