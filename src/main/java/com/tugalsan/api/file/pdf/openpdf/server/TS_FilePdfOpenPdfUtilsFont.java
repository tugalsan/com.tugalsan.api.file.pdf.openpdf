package com.tugalsan.api.file.pdf.openpdf.server;

import module java.desktop;
import module com.github.librepdf.openpdf;
import com.lowagie.text.Font;
import java.nio.file.*;

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
