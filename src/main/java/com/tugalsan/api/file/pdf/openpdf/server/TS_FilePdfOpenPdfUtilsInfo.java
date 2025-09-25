package com.tugalsan.api.file.pdf.openpdf.server;

import module com.github.librepdf.openpdf;
import module com.tugalsan.api.time;
import module com.tugalsan.api.union;
import java.nio.file.*;
import java.util.*;

public class TS_FilePdfOpenPdfUtilsInfo {

    private TS_FilePdfOpenPdfUtilsInfo() {

    }

    public static record Info(String title, String author, String subject, String producer, TGS_Time dateCreate, TGS_Time dateModified) {

        public Info      {
            if (title == null) {
                title = "";
            }
            if (author == null) {
                author = "";
            }
            if (subject == null) {
                subject = "";
            }
            if (producer == null) {
                producer = "";
            }
            if (dateCreate == null) {
                dateCreate = TGS_Time.of();
            }
            if (dateModified == null) {
                dateModified = TGS_Time.of();
            }
        }

        public Info cloneWithTitle(String title) {
            return new Info(title, author, subject, producer, dateCreate, dateModified);
        }

        public Info cloneWithAuthor(String author) {
            return new Info(title, author, subject, producer, dateCreate, dateModified);
        }

        public Info cloneWithSubject(String subject) {
            return new Info(title, author, subject, producer, dateCreate, dateModified);
        }

        public Info cloneWithProducer(String producer) {
            return new Info(title, author, subject, producer, dateCreate, dateModified);
        }

        public Info cloneWithDateCreate(TGS_Time dateCreate) {
            return new Info(title, author, subject, producer, dateCreate, dateModified);
        }

        public Info cloneWithDateModified(TGS_Time dateModified) {
            return new Info(title, author, subject, producer, dateCreate, dateModified);
        }
    }

    public TGS_UnionExcuse<Info> get(Path srcPdf) {
        return get(srcPdf, null);
    }

    public TGS_UnionExcuse<Info> get(Path srcPdf, byte[] password) {
        var u = TS_FilePdfOpenPdfUtilsDocument.call_doc_with_reader(srcPdf, password, (doc, reader) -> {
            return get(reader);
        });
        if (u.isExcuse()) {
            return u.toExcuse();
        } else {
            return u.value();
        }
    }

    public TGS_UnionExcuse<Info> get(PdfReader reader) {
        Map<String, String> pdfinfo = reader.getInfo();
        return TGS_UnionExcuse.of(new Info(
                pdfinfo.get("Title"),
                pdfinfo.get("Author"),
                pdfinfo.get("Subject"),
                pdfinfo.get("Producer"),
                TGS_Time.of(PdfDate.decode(pdfinfo.get("CreationDate")).getTime()),
                TGS_Time.of(PdfDate.decode(pdfinfo.get("ModDate")).getTime())
        ));
    }

    public void set(PdfWriter writer, Info info) {
        writer.getInfo().put(PdfName.TITLE, new PdfString(info.title));
        writer.getInfo().put(PdfName.AUTHOR, new PdfString(info.author));
        writer.getInfo().put(PdfName.SUBJECT, new PdfString(info.subject));
        writer.getInfo().put(PdfName.PRODUCER, new PdfString(info.producer));
        writer.getInfo().put(PdfName.CREATIONDATE, new PdfDate(TS_TimeUtils.toCalendar(info.dateCreate.toDateObject())));
        writer.getInfo().put(PdfName.MODDATE, new PdfDate(TS_TimeUtils.toCalendar(info.dateModified.toDateObject())));
    }
}
