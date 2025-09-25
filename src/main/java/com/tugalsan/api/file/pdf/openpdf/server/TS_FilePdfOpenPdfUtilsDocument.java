package com.tugalsan.api.file.pdf.openpdf.server;

import module com.tugalsan.api.union;
import module com.tugalsan.api.function;
import com.tugalsan.api.file.pdf.openpdf.server.TS_FilePdfOpenPdfUtilsPage.PageInfo;
import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfName;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfString;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.RandomAccessFileOrArray;
import java.io.*;
import java.nio.file.*;

public class TS_FilePdfOpenPdfUtilsDocument {

    private TS_FilePdfOpenPdfUtilsDocument() {

    }

    public static TGS_UnionExcuseVoid run_doc_with_copy(TS_FilePdfOpenPdfUtilsPageCompress.CompressionLevel cLvl, Path dstPdf, TGS_FuncMTU_In2<Document, PdfCopy> doc_copy) {
        return TGS_FuncMTCUtils.call(() -> {
            try (var os = Files.newOutputStream(dstPdf)) {
                try (var _doc = new Document()) {
                    var pdfCopy = new PdfCopy(_doc, new BufferedOutputStream(os));
                    pdfCopy.setPdfVersion(PdfWriter.VERSION_1_7);
                    TS_FilePdfOpenPdfUtilsPageCompress.set(pdfCopy, cLvl);
                    _doc.open();
                    pdfCopy.getInfo().put(PdfName.CREATOR, new PdfString(Document.getVersion()));
                    doc_copy.call(_doc, pdfCopy);
                    return TGS_UnionExcuseVoid.ofVoid();
                }
            }
        }, e -> TGS_UnionExcuseVoid.ofExcuse(e));
    }

    public static TGS_UnionExcuseVoid run_doc_with_writer(TS_FilePdfOpenPdfUtilsPageCompress.CompressionLevel cLvl, PageInfo pageInfo, Path dstPdf, TGS_FuncMTU_In2<Document, PdfWriter> doc_writer) {
        return TGS_FuncMTCUtils.call(() -> {
            try (var os = Files.newOutputStream(dstPdf)) {
                try (var _doc = new Document(pageInfo.toRectangle(), pageInfo.marginLeft(), pageInfo.marginRight(), pageInfo.marginTop(), pageInfo.marginBottom())) {
                    var pdfWriter = PdfWriter.getInstance(_doc, os);
                    TS_FilePdfOpenPdfUtilsPageCompress.set(pdfWriter, cLvl);
                    _doc.open();
                    pdfWriter.getInfo().put(PdfName.CREATOR, new PdfString(Document.getVersion()));
                    doc_writer.run(_doc, pdfWriter);
                    return TGS_UnionExcuseVoid.ofVoid();
                }
            }
        }, e -> TGS_UnionExcuseVoid.ofExcuse(e));
    }

    public static <T> TGS_UnionExcuse<T> call_doc_with_writer(TS_FilePdfOpenPdfUtilsPageCompress.CompressionLevel cLvl, PageInfo pageInfo, Path dstPdf, TGS_FuncMTU_OutTyped_In2<T, Document, PdfWriter> doc_writer) {
        return TGS_FuncMTCUtils.call(() -> {
            try (var os = Files.newOutputStream(dstPdf)) {
                try (var _doc = new Document(pageInfo.toRectangle(), pageInfo.marginLeft(), pageInfo.marginRight(), pageInfo.marginTop(), pageInfo.marginBottom())) {
                    var pdfWriter = PdfWriter.getInstance(_doc, os);
                    TS_FilePdfOpenPdfUtilsPageCompress.set(pdfWriter, cLvl);
                    _doc.open();
                    pdfWriter.getInfo().put(PdfName.CREATOR, new PdfString(Document.getVersion()));
                    return TGS_UnionExcuse.of(doc_writer.call(_doc, pdfWriter));
                }
            }
        }, e -> TGS_UnionExcuse.ofExcuse(e));
    }

    public static TGS_UnionExcuseVoid run_doc_with_reader(Path srcPdf, TGS_FuncMTU_In2<Document, PdfReader> doc_reader) {
        return run_doc_with_reader(srcPdf, null, doc_reader);
    }

    public static <T> TGS_UnionExcuse<T> call_doc_with_reader(Path srcPdf, TGS_FuncMTU_OutTyped_In2<T, Document, PdfReader> doc_reader) {
        return call_doc_with_reader(srcPdf, null, doc_reader);
    }

    public static TGS_UnionExcuseVoid run_doc_with_reader(Path srcPdf, byte[] password, TGS_FuncMTU_In2<Document, PdfReader> doc_reader) {
        return TGS_FuncMTCUtils.call(() -> {
            try (var is = Files.newInputStream(srcPdf)) {
                try (var _doc = new Document()) {
                    try (var raf = new RandomAccessFileOrArray(is)) {
                        var reader = new PdfReader(raf, password);
                        doc_reader.run(_doc, reader);
                        return TGS_UnionExcuseVoid.ofVoid();
                    }
                }
            }
        }, e -> TGS_UnionExcuseVoid.ofExcuse(e));
    }

    public static <T> TGS_UnionExcuse<T> call_doc_with_reader(Path srcPdf, byte[] password, TGS_FuncMTU_OutTyped_In2<T, Document, PdfReader> doc_reader) {
        return TGS_FuncMTCUtils.call(() -> {
            try (var is = Files.newInputStream(srcPdf)) {
                try (var _doc = new Document()) {
                    try (var raf = new RandomAccessFileOrArray(is)) {
                        var reader = new PdfReader(raf, password);
                        return TGS_UnionExcuse.of(doc_reader.call(_doc, reader));
                    }
                }
            }
        }, e -> TGS_UnionExcuse.ofExcuse(e));
    }
}
