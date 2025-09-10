package com.tugalsan.api.file.pdf.openpdf.server;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.tugalsan.api.log.server.TS_Log;
import java.io.FileOutputStream;
import java.io.IOException;

public class TS_FilePdfOpenPdfUtilsTable {

    private TS_FilePdfOpenPdfUtilsTable() {

    }

    final private static TS_Log d = TS_Log.of(TS_FilePdfOpenPdfUtilsTable.class);

    public static void test() {
        try (var document = new Document(PageSize.A4)) {
            // step 2
            var writer = PdfWriter.getInstance(document,
                    new FileOutputStream("tables.pdf"));
            float width = document.getPageSize().getWidth();
            float height = document.getPageSize().getHeight();
            // step 3
            document.open();

            var font8 = FontFactory.getFont(FontFactory.HELVETICA, 8);
            // step 4
            float[] columnDefinitionSize = {33.33F, 33.33F, 33.33F};

            float pos = height / 2;

            var table = new PdfPTable(columnDefinitionSize);
            table.getDefaultCell().setBorder(0);
            table.setHorizontalAlignment(0);
            table.setTotalWidth(width - 72);
            table.setLockedWidth(true);

            var cell = new PdfPCell(new Phrase("Table added with document.add()"));
            cell.setColspan(columnDefinitionSize.length);
            table.addCell(cell);
            table.addCell(new Phrase("Louis Pasteur", font8));
            table.addCell(new Phrase("Albert Einstein", font8));
            table.addCell(new Phrase("Isaac Newton", font8));
            table.addCell(new Phrase("8, Rabic street", font8));
            table.addCell(new Phrase("2 Photons Avenue", font8));
            table.addCell(new Phrase("32 Gravitation Court", font8));
            table.addCell(new Phrase("39100 Dole France", font8));
            table.addCell(new Phrase("12345 Ulm Germany", font8));
            table.addCell(new Phrase("45789 Cambridge  England", font8));

            document.add(table);

            table = new PdfPTable(columnDefinitionSize);
            table.getDefaultCell().setBorder(0);
            table.setHorizontalAlignment(0);
            table.setTotalWidth(width - 72);
            table.setLockedWidth(true);

            cell = new PdfPCell(new Phrase("Table added with writeSelectedRows"));
            cell.setColspan(columnDefinitionSize.length);
            table.addCell(cell);
            table.addCell(new Phrase("Louis Pasteur", font8));
            table.addCell(new Phrase("Albert Einstein", font8));
            table.addCell(new Phrase("Isaac Newton", font8));
            table.addCell(new Phrase("8, Rabic street", font8));
            table.addCell(new Phrase("2 Photons Avenue", font8));
            table.addCell(new Phrase("32 Gravitation Court", font8));
            table.addCell(new Phrase("39100 Dole France", font8));
            table.addCell(new Phrase("12345 Ulm Germany", font8));
            table.addCell(new Phrase("45789 Cambridge  England", font8));

            table.writeSelectedRows(0, -1, 50, pos, writer.getDirectContent());
        } catch (DocumentException | IOException de) {
            d.ce(de.getMessage());
        }
    }
}
