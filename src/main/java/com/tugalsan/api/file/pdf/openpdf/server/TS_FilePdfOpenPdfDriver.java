package com.tugalsan.api.file.pdf.openpdf.server;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.tugalsan.api.charset.client.TGS_CharSet;
import com.tugalsan.api.file.server.TS_FileUtils;
import com.tugalsan.api.function.client.maythrowexceptions.unchecked.TGS_FuncMTUEffectivelyFinal;
import com.tugalsan.api.log.server.TS_Log;
import com.tugalsan.api.function.client.maythrowexceptions.checked.TGS_FuncMTCUtils;
import com.tugalsan.api.function.client.maythrowexceptions.unchecked.TGS_FuncMTUUtils;
import java.awt.Color;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.IntStream;

//    private static String GOOGLEPDF () "https://docs.google.com/gview?url=";
public class TS_FilePdfOpenPdfDriver {

    final private static TS_Log d = TS_Log.of(TS_FilePdfOpenPdfDriver.class);

    public static Color getFONT_COLOR_BLACK() {
        return Color.BLACK;
    }

    public static Color getFONT_COLOR_BLUE() {
        return Color.BLUE;
    }

    public static Color getFONT_COLOR_CYAN() {
        return Color.CYAN;
    }

    public static Color getFONT_COLOR_DARK_GRAY() {
        return Color.DARK_GRAY;
    }

    public static Color getFONT_COLOR_GRAY() {
        return Color.GRAY;
    }

    public static Color getFONT_COLOR_GREEN() {
        return Color.GREEN;
    }

    public static Color getFONT_COLOR_LIGHT_GRAY() {
        return Color.LIGHT_GRAY;
    }

    public static Color getFONT_COLOR_MAGENTA() {
        return Color.MAGENTA;
    }

    public static Color getFONT_COLOR_ORANGE() {
        return Color.ORANGE;
    }

    public static Color getFONT_COLOR_PINK() {
        return Color.PINK;
    }

    public static Color getFONT_COLOR_RED() {
        return Color.RED;
    }

    public static Color getFONT_COLOR_YELLOW() {
        return Color.YELLOW;
    }

    public static Rectangle getPAGE_SIZE_A0_LAND() {
        return PageSize.A0.rotate();
    }

    public static Rectangle getPAGE_SIZE_A0_PORT() {
        return PageSize.A0;
    }

    public static Rectangle getPAGE_SIZE_A1_LAND() {
        return PageSize.A1.rotate();
    }

    public static Rectangle getPAGE_SIZE_A1_PORT() {
        return PageSize.A1;
    }

    public static Rectangle getPAGE_SIZE_A2_LAND() {
        return PageSize.A2.rotate();
    }

    public static Rectangle getPAGE_SIZE_A2_PORT() {
        return PageSize.A2;
    }

    public static Rectangle getPAGE_SIZE_A3_LAND() {
        return PageSize.A3.rotate();
    }

    public static Rectangle getPAGE_SIZE_A3_PORT() {
        return PageSize.A3;
    }

    public static Rectangle getPAGE_SIZE_A4_LAND() {
        return PageSize.A4.rotate();
    }

    public static Rectangle getPAGE_SIZE_A4_PORT() {
        return PageSize.A4;
    }

    public static Rectangle getPAGE_SIZE_A5_LAND() {
        return PageSize.A5.rotate();
    }

    public static Rectangle getPAGE_SIZE_A5_PORT() {
        return PageSize.A5;
    }

    public static Rectangle getPAGE_SIZE_A6_LAND() {
        return PageSize.A6.rotate();
    }

    public static Rectangle getPAGE_SIZE_A6_PORT() {
        return PageSize.A6;
    }

    public PdfWriter getWriter() {
        return writer;
    }
    private PdfWriter writer;

    public Document getDocument() {
        return document;
    }
    private Document document;

    public Path getFile() {
        return file;
    }
    private final Path file;

    public TS_FilePdfOpenPdfDriver(Path file) {
        this.file = file;
    }

    public void createNewPage(int pageSizeAX0, boolean landscape, Integer marginLeft0, Integer marginRight0, Integer marginTop0, Integer marginBottom0) {
        TGS_FuncMTCUtils.run(() -> {
            d.ci("createNewPage");
            var marginLeft = marginLeft0 == null ? 50 : marginLeft0;
            var marginRight = marginRight0 == null ? 10 : marginRight0;
            var marginTop = marginTop0 == null ? 10 : marginTop0;
            var marginBottom = marginBottom0 == null ? 10 : marginBottom0;
            var pageSizeAX = TGS_FuncMTUEffectivelyFinal.of(pageSizeAX0)
                    .anointIf(val -> val < 0, val -> 0)
                    .anointIf(val -> val > 6, val -> 6)
                    .coronate();
            var pageSize = TGS_FuncMTUEffectivelyFinal.of(getPAGE_SIZE_A4_PORT())
                    .anointIf(val -> pageSizeAX == 0, val -> landscape ? getPAGE_SIZE_A0_LAND() : getPAGE_SIZE_A0_PORT())
                    .anointIf(val -> pageSizeAX == 1, val -> landscape ? getPAGE_SIZE_A1_LAND() : getPAGE_SIZE_A1_PORT())
                    .anointIf(val -> pageSizeAX == 2, val -> landscape ? getPAGE_SIZE_A2_LAND() : getPAGE_SIZE_A2_PORT())
                    .anointIf(val -> pageSizeAX == 3, val -> landscape ? getPAGE_SIZE_A3_LAND() : getPAGE_SIZE_A3_PORT())
                    .anointIf(val -> pageSizeAX == 4, val -> landscape ? getPAGE_SIZE_A4_LAND() : getPAGE_SIZE_A4_PORT())
                    .anointIf(val -> pageSizeAX == 5, val -> landscape ? getPAGE_SIZE_A5_LAND() : getPAGE_SIZE_A5_PORT())
                    .anointIf(val -> pageSizeAX == 6, val -> landscape ? getPAGE_SIZE_A6_LAND() : getPAGE_SIZE_A6_PORT())
                    .coronate();
            if (document == null) {
                document = new Document(pageSize, marginLeft, marginRight, marginTop, marginBottom);
                writer = PdfWriter.getInstance(document, Files.newOutputStream(file));//I KNOW
                document.open();
            } else {
                document.setPageSize(pageSize);
                document.newPage();
            }
        });
    }

    public void setAlignLeft(Paragraph p) {
        p.setAlignment(Element.ALIGN_LEFT);
    }

    public void setAlignRight(Paragraph p) {
        p.setAlignment(Element.ALIGN_RIGHT);
    }

    public void setAlignCenter(Paragraph p) {
        p.setAlignment(Element.ALIGN_CENTER);
    }

    public void setAlignJustified(Paragraph p) {
        p.setAlignment(Element.ALIGN_JUSTIFIED);
    }

    public Paragraph createParagraph() {
        return new Paragraph();
    }

    public Paragraph createParagraph(Font font) {
        var p = new Paragraph();
        p.setFont(font);
        return p;
    }

    private Chunk createChunkText(CharSequence text) {
        return new Chunk(text.toString());
    }

//    private Chunk createChunkLineSeperator(LineSeparator ls) {
//        return new Chunk(ls);
//    }
    private Chunk createChunkText(CharSequence text, Font font) {
        var c = new Chunk(text.toString());
        c.setFont(font);
        return c;
    }

    private Chunk createChunkNewLine() {
        return Chunk.NEWLINE;
    }

    public Image createImage(java.awt.Image imageFile, Color color) {
        return TGS_FuncMTCUtils.call(() -> Image.getInstance(imageFile, color));
    }

    public Image createImage(CharSequence filePath) {
        return TGS_FuncMTCUtils.call(() -> Image.getInstance(filePath.toString()));
    }

    private static void addImage2Document(Document document, Image image) throws DocumentException {
        var c = new Chunk(image, 0, -image.getHeight() / 2, true);
        document.add(c);
    }

    private static void addImage2Cell(PdfPCell cell, Image image) {
        var h = image.getHeight();
        var offsetX = -1;
        var offsetY = 0;
        var c = new Chunk(image, offsetX + 0, offsetY + -h / 2, true);
        cell.setMinimumHeight(h);
        d.ci("addImage2Cell", "image.getHeight()", h);
        cell.addElement(c);
    }

    public void addImageToPageLeft(java.awt.Image image, boolean textWrap, boolean transperancyAsWhite) {
        addImageToPageLeft(createImage(image, transperancyAsWhite ? Color.WHITE : Color.BLACK), textWrap, transperancyAsWhite);
    }

    public void addImageToPageLeft(Image image, boolean textWrap, boolean transperancyAsWhite) {
        TGS_FuncMTCUtils.run(() -> {
            if (image == null) {
                TGS_FuncMTUUtils.thrw(d.className(), "addImageToPageLeft", "image == null");
                return;
            }
            if (textWrap) {
                image.setAlignment(/*Image.ALIGN_LEFT | */Image.TEXTWRAP);//allign left is 0 already
            } else {
                image.setAlignment(Image.ALIGN_LEFT);
            }
            addImage2Document(document, image);
        });
    }

    public void addImageToPageRight(java.awt.Image image, boolean textWrap, boolean transperancyAsWhite) {
        addImageToPageRight(createImage(image, transperancyAsWhite ? Color.WHITE : Color.BLACK), textWrap, transperancyAsWhite);
    }

    public void addImageToPageRight(Image image, boolean textWrap, boolean transperancyAsWhite) {
        TGS_FuncMTCUtils.run(() -> {
            if (image == null) {
                TGS_FuncMTUUtils.thrw(d.className(), "addImageToPageRight", "image == null");
                return;
            }
            if (textWrap) {
                image.setAlignment(Image.ALIGN_RIGHT | Image.TEXTWRAP);
            } else {
                image.setAlignment(Image.ALIGN_RIGHT);
            }
            addImage2Document(document, image);
        });
    }

    public void addImageToPageCenter(java.awt.Image image, boolean textWrap, boolean transperancyAsWhite) {
        addImageToPageCenter(createImage(image, transperancyAsWhite ? Color.WHITE : Color.BLACK), textWrap, transperancyAsWhite);
    }

    public void addImageToPageCenter(Image image, boolean textWrap, boolean transperancyAsWhite) {
        TGS_FuncMTCUtils.run(() -> {
            if (image == null) {
                TGS_FuncMTUUtils.thrw(d.className(), "addImageToPageCenter", "image == null");
                return;
            }
            if (textWrap) {
                image.setAlignment(Image.ALIGN_CENTER | Image.TEXTWRAP);
            } else {
                image.setAlignment(Image.ALIGN_CENTER);
            }
            addImage2Document(document, image);
        });
    }

    public void addImageToCellLeft(PdfPCell cell, java.awt.Image image, boolean textWrap, boolean transperancyAsWhite) {
        if (image == null) {
            d.ce("addImageToCellLeft.ERROR: TKPDFDocument.addImageToCellLeft.imageAWT == null");
            return;
        }
        var i = createImage(image, transperancyAsWhite ? Color.WHITE : Color.BLACK);
        if (textWrap) {
            i.setAlignment(/*Image.ALIGN_LEFT | */Image.TEXTWRAP);//allign left is 0 already
        } else {
            i.setAlignment(Image.ALIGN_LEFT);
        }
        addImage2Cell(cell, i);
    }

    public void addImageToCellRight(PdfPCell cell, java.awt.Image image, boolean textWrap, boolean transperancyAsWhite) {
        if (image == null) {
            d.ce("addImageToCellRight.ERROR: TKPDFDocument.addImageToCellRight.imageAWT == null");
            return;
        }
        var i = createImage(image, transperancyAsWhite ? Color.WHITE : Color.BLACK);
        if (textWrap) {
            i.setAlignment(Image.ALIGN_RIGHT | Image.TEXTWRAP);
        } else {
            i.setAlignment(Image.ALIGN_RIGHT);
        }
        addImage2Cell(cell, i);
    }

    public void addImageToCellCenter(PdfPCell cell, java.awt.Image image, boolean textWrap, boolean transperancyAsWhite) {
        if (image == null) {
            d.ce("addImageToCellCenter.ERROR: TKPDFDocument.addImageToCellCenter.imageAWT == null");
            return;
        }
        var i = createImage(image, transperancyAsWhite ? Color.WHITE : Color.BLACK);
        if (textWrap) {
            i.setAlignment(Image.ALIGN_CENTER | Image.TEXTWRAP);
        } else {
            i.setAlignment(Image.ALIGN_CENTER);
        }
        addImage2Cell(cell, i);
    }

    private void addChunkToParagraph(Chunk c, Paragraph p) {
        p.add(c);
    }

    public void addTextToParagraph(CharSequence text, Paragraph p) {
        addChunkToParagraph(createChunkText(text), p);
    }

    public void addTextToParagraph(CharSequence text, Paragraph p, Font font) {
        addChunkToParagraph(createChunkText(text, font), p);
    }

    public void addLineSeperatorParagraph(Paragraph p) {
        addChunkToParagraph(Chunk.NEWLINE, p);
    }

    public void addNewLineToParagraph(Paragraph p) {
        p.add(createChunkNewLine());
    }

    public void addParagraphToPage(Paragraph p) {
        TGS_FuncMTCUtils.run(() -> document.add(p));
    }

    public void addTableToPage(PdfPTable table) {
        TGS_FuncMTCUtils.run(() -> document.add(table));
    }

    public void addCellToTable(PdfPTable table, PdfPCell cell, int rotation_0_90_180_270) {
        cell.setRotation(rotation_0_90_180_270);
        table.addCell(cell);
    }

//    final private record FontBufferItem(Path path, int height, boolean bold, boolean italic, BaseColor fontColor, float fontSizeCorrectionForFontFile, Font pdfFont) {
//
//    }
//    final private static TS_ThreadSyncLst<FontBufferItem> fontBuffer = TS_ThreadSyncLst.of();
    public static Font getFontFrom(int height, boolean bold, boolean italic, Color fontColor,
            Path path, float fontSizeCorrectionForFontFile) {
        var style = TGS_FuncMTUEffectivelyFinal.ofInt().coronateAs(__ -> {
            if (bold && italic) {
                return Font.BOLDITALIC;
            }
            if (bold) {
                return Font.BOLD;
            }
            if (italic) {
                return Font.ITALIC;
            }
            return Font.NORMAL;
        });
//        var fontAlreadyExists = fontBuffer.stream()
//                .filter(t -> t.path.equals(path))
//                .filter(t -> t.height == height)
//                .filter(t -> t.bold == bold)
//                .filter(t -> t.italic == italic)
//                .filter(t -> t.fontColor.equals(fontColor))
//                .filter(t -> t.fontSizeCorrectionForFontFile == fontSizeCorrectionForFontFile)
//                .map(t -> t.pdfFont)
//                .findAny().orElse(null);
//        if (fontAlreadyExists != null) {
//            return fontAlreadyExists;
//        }
        if (!TS_FileUtils.isExistFile(path)) {
            d.ce("getFontFrom", "UTF8 font bold not find!", path);
            return getFontInternal(height, bold, italic, fontColor);
        }
        var newPdfFont = new Font(
                TGS_FuncMTCUtils.call(() -> {
                    return BaseFont.createFont(
                            path.toAbsolutePath().normalize().toString(),
                            BaseFont.IDENTITY_H, BaseFont.EMBEDDED
                    );
                }),
                height * fontSizeCorrectionForFontFile, style, fontColor
        );
//        fontBuffer.add(new FontBufferItem(path, height, bold, italic, fontColor, fontSizeCorrectionForFontFile, newPdfFont));
        return newPdfFont;
    }

    public static Font getFontInternal(int fontSize, boolean bold, boolean italic, Color fontColor) {
        var fontStyle = TGS_FuncMTUEffectivelyFinal.ofInt().coronateAs(__ -> {
            if (bold && italic) {
                return Font.BOLDITALIC;
            }
            if (bold) {
                return Font.BOLD;
            }
            if (italic) {
                return Font.ITALIC;
            }
            return Font.NORMAL;
        });
        return FontFactory.getFont(BaseFont.TIMES_ROMAN, TGS_CharSet.cmn().getCharset_IBM_TURKISH(), true, fontSize, fontStyle, fontColor);
        //return FontFactory.getFont("arialuni", "Cp857", true, fontSize, (bold & italic ? Font.BOLDITALIC : (bold && !italic ? Font.BOLD : ((!bold && italic ? Font.ITALIC : Font.NORMAL)))), fontColor);
        //return FontFactory.getFont("arialuni", "Identity-H", fontSize, (bold & italic ? Font.BOLDITALIC : (bold && !italic ? Font.BOLD : ((!bold && italic ? Font.ITALIC : Font.NORMAL)))), fontColor);
        //return FontFactory.getFont(FontFactory.HELVETICA, TK_GWTCharacterSets.DEFAULT, fontSize, (bold & italic ? Font.BOLDITALIC : (bold && !italic ? Font.BOLD : ((!bold && italic ? Font.ITALIC : Font.NORMAL)))), fontColor);
    }

    public void setFont(Font font, Paragraph p) {
        p.setFont(font);
    }

    public void setFont(Font font, Chunk chunk) {
        chunk.setFont(font);
    }

    public PdfPCell createCell(Paragraph paragraph) {
        return new PdfPCell(paragraph);
    }

    public void setCellColSpan(PdfPCell cell, int colSpan) {
        cell.setColspan(colSpan);
    }

    public PdfPTable createTable(int colCount) {
        return new PdfPTable(colCount);
    }

    public PdfPTable createTable(int[] relColWidths) {
        var frelColWidths = new float[relColWidths.length];
        IntStream.range(0, relColWidths.length).parallel().forEach(i -> frelColWidths[i] = relColWidths[i]);
        return new PdfPTable(frelColWidths);
    }

    public void setCellPadding(PdfPCell cell, float padding) {
        cell.setPadding(padding);//10.0f
    }

    public void setCellBackground(PdfPCell cell, Color color) {
        cell.setBackgroundColor(color);
    }

    public void setCellAlignLeft(PdfPCell cell) {
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    }

    public void setCellAlignRight(PdfPCell cell) {
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
    }

    public void setCellAlignCenter(PdfPCell cell) {
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    }

    public void close() {
        TGS_FuncMTCUtils.run(() -> {
            if (skipCloseFix) {
                return;
            }
            var p = createParagraph();
            addChunkToParagraph(createChunkText("."), p);
            addParagraphToPage(p);
        }, e -> d.ct("close.closeFix", e));
        TGS_FuncMTCUtils.run(() -> {
            if (document != null) {
                document.close();
            }
        }, e -> d.ct("close.document", e));
        TGS_FuncMTCUtils.run(() -> {
            if (writer != null) {
                writer.close();
            }
        }, e -> d.ct("close.writer", e));
    }
    public boolean skipCloseFix = true;
}
