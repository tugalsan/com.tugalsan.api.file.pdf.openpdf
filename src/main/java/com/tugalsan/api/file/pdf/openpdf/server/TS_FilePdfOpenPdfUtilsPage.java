package com.tugalsan.api.file.pdf.openpdf.server;

import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfDictionary;
import com.lowagie.text.pdf.PdfReader;
import com.tugalsan.api.union.client.TGS_UnionExcuse;
import java.nio.file.Path;

public class TS_FilePdfOpenPdfUtilsPage {

    private TS_FilePdfOpenPdfUtilsPage() {

    }

    public static record PageInfo(int aX, boolean landscape, float marginLeft, float marginRight, float marginTop, float marginBottom) {

        public Rectangle toRectangle() {
            if (aX <= 0) {
                return landscape ? PageSize.A0.rotate() : PageSize.A0;
            }
            if (aX == 1) {
                return landscape ? PageSize.A1.rotate() : PageSize.A1;
            }
            if (aX == 2) {
                return landscape ? PageSize.A2.rotate() : PageSize.A2;
            }
            if (aX == 3) {
                return landscape ? PageSize.A3.rotate() : PageSize.A3;
            }
            if (aX == 4) {
                return landscape ? PageSize.A4.rotate() : PageSize.A4;
            }
            if (aX == 5) {
                return landscape ? PageSize.A5.rotate() : PageSize.A5;
            }
            return landscape ? PageSize.A6.rotate() : PageSize.A6;
        }
    }
    final public static TS_FilePdfOpenPdfUtilsPage.PageInfo PAGE_INFO_A4_PORT_0_0_0_0 = new TS_FilePdfOpenPdfUtilsPage.PageInfo(4, false, 0, 0, 0, 0);
    final public static TS_FilePdfOpenPdfUtilsPage.PageInfo PAGE_INFO_A4_LAND_0_0_0_0 = new TS_FilePdfOpenPdfUtilsPage.PageInfo(4, false, 0, 0, 0, 0);

    public static boolean addPage(Document doc, PageInfo pageInfo) {
        doc.setPageSize(pageInfo.toRectangle());
        doc.setMargins(pageInfo.marginLeft, pageInfo.marginRight, pageInfo.marginTop, pageInfo.marginBottom);
        return doc.newPage();
    }

    public static float getPageWidth(PdfReader reader, int pageIdx) {
        return reader.getCropBox(pageIdx).getWidth();
    }

    public static float getPageHeight(PdfReader reader, int pageIdx) {
        return reader.getCropBox(pageIdx).getHeight();
    }

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
