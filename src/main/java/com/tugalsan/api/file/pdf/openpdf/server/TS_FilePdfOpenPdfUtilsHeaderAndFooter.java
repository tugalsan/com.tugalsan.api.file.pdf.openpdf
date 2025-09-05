package com.tugalsan.api.file.pdf.openpdf.server;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class TS_FilePdfOpenPdfUtilsHeaderAndFooter extends PdfPageEventHelper {

    public static void test(String[] args) throws DocumentException, FileNotFoundException {
        var document = new Document(PageSize.A4, 36, 36, 65, 36);
        var writer = PdfWriter.getInstance(document, new FileOutputStream("HeaderAndFooter.pdf"));
        writer.setPageEvent(new TS_FilePdfOpenPdfUtilsHeaderAndFooter());
        document.open();
        var page1Body = new Paragraph("Page one content.");
        page1Body.setAlignment(Element.ALIGN_CENTER);
        document.add(page1Body);
        document.close();
        writer.close();
    }

    @Override
    public void onStartPage(PdfWriter writer, Document document) {
        var table = new PdfPTable(3);
        table.setTotalWidth(510);
        table.setWidths(new int[]{38, 36, 36});
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.getDefaultCell().setPaddingBottom(5);
        table.getDefaultCell().setBorder(Rectangle.BOTTOM);

        var cEmpty = new PdfPCell(new Paragraph(""));
        cEmpty.setBorder(Rectangle.NO_BORDER);

        table.addCell(cEmpty);
        var pTitle = new Paragraph("Header", new Font(Font.COURIER, 20, Font.BOLD));
        var cTitle = new PdfPCell(pTitle);
        cTitle.setPaddingBottom(10);
        cTitle.setHorizontalAlignment(Element.ALIGN_CENTER);
        cTitle.setBorder(Rectangle.NO_BORDER);
        table.addCell(cTitle);
        table.addCell(cEmpty);

        var cellFont = new Font(Font.HELVETICA, 8);
        table.addCell(new Paragraph("Phone Number: 888-999-0000", cellFont));
        table.addCell(new Paragraph("Address : 333, Manhattan, New York", cellFont));
        table.addCell(new Paragraph("Website : http://grogu-yoda.com", cellFont));

        table.writeSelectedRows(0, -1, 34, 828, writer.getDirectContent());
    }

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        var table = new PdfPTable(2);
        table.setTotalWidth(510);
        table.setWidths(new int[]{50, 50});
        table.getDefaultCell().setPaddingBottom(5);
        table.getDefaultCell().setBorder(Rectangle.TOP);

        var pTitle = new Paragraph("Footer", new Font(Font.HELVETICA, 10));
        var cTitle = new PdfPCell(pTitle);
        cTitle.setPaddingTop(4);
        cTitle.setHorizontalAlignment(Element.ALIGN_LEFT);
        cTitle.setBorder(Rectangle.TOP);
        table.addCell(cTitle);

        var pPageNumber = new Paragraph("Page " + document.getPageNumber(), new Font(Font.HELVETICA, 10));
        var cPageNumber = new PdfPCell(pPageNumber);
        cPageNumber.setPaddingTop(4);
        cPageNumber.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cPageNumber.setBorder(Rectangle.TOP);
        table.addCell(cPageNumber);

        table.writeSelectedRows(0, -1, 34, 36, writer.getDirectContent());
    }
}
