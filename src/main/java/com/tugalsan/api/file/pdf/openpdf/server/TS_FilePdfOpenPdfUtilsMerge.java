package com.tugalsan.api.file.pdf.openpdf.server;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStream;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.SimpleBookmark;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TS_FilePdfOpenPdfUtilsMerge {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("arguments: file1 [file2 ...] destfile");
        } else {
            try {
                File outFile = new File(args[args.length - 1]);
                List<File> sources = new ArrayList<>();
                for (int i = 0; i < args.length - 1; i++) {
                    sources.add(new File(args[i]));
                }
                concat(sources, outFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void concat(List<File> sources, File target) throws IOException {

        for (File source : sources) {
            if (!source.isFile() || !source.canRead()) {
                throw new IOException("cannot read:" + source.getAbsolutePath());
            }
        }

        int pageOffset = 0;
        List<Map<String, Object>> master = new ArrayList<>();

        Document document = new Document();
        PdfCopy writer = new PdfCopy(document, new BufferedOutputStream(Files.newOutputStream(target.toPath())));
        writer.setPdfVersion(PdfWriter.VERSION_1_7);
        writer.setFullCompression();
        writer.setCompressionLevel(PdfStream.BEST_COMPRESSION);
        document.open();
        for (File source : sources) {
            // we create a reader for a certain document
            PdfReader reader = new PdfReader(new BufferedInputStream(Files.newInputStream(source.toPath())));
            reader.consolidateNamedDestinations();
            // we retrieve the total number of pages
            int numberOfPages = reader.getNumberOfPages();
            List<Map<String, Object>> bookmarks = SimpleBookmark.getBookmarkList(reader);
            if (bookmarks != null) {
                if (pageOffset != 0) {
                    SimpleBookmark.shiftPageNumbersInRange(bookmarks, pageOffset, null);
                }
                master.addAll(bookmarks);
            }
            pageOffset += numberOfPages;

            // we add content
            PdfImportedPage page;
            for (int i = 1; i <= numberOfPages; i++) {
                page = writer.getImportedPage(reader, i);
                writer.addPage(page);
            }
            writer.freeReader(reader);
        }
        if (!master.isEmpty()) {
            writer.setOutlines(master);
        }
        // we close the document
        document.close();
    }
}
