package com.tugalsan.api.file.pdf.openpdf.server;

import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.LayoutProcessor;
import java.awt.Color;
import java.nio.file.Path;

public class TS_FilePdfOpenPdfUtilsFont {

    private TS_FilePdfOpenPdfUtilsFont() {

    }

    private static volatile boolean WARMED_UP = false;

    public static Font craete(Path ttf, String alias, float size, boolean left_to_right, boolean bold, boolean italic, Color color) {
        if (!WARMED_UP) {
            WARMED_UP = true;
            LayoutProcessor.enableKernLiga();
        }
        var style = Font.NORMAL;
        if (bold && italic) {
            style = Font.BOLDITALIC;
        } else if (bold) {
            style = Font.BOLD;
        } else if (italic) {
            style = Font.ITALIC;
        }
        FontFactory.register(ttf.toAbsolutePath().toString(), alias);
        var font = FontFactory.getFont(alias, BaseFont.IDENTITY_H, true, size, style, color);
        if (left_to_right) {
            LayoutProcessor.setRunDirectionLtr(font);
        } else {
            LayoutProcessor.setRunDirectionRtl(font);
        }
        return font;
    }
}
