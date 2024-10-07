package com.tugalsan.api.file.pdf.openpdf.server;

import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.LayoutProcessor;
import java.nio.file.Path;

public class TS_FilePdfOpenPdfUtilsFont {

    public static void enable() {
        LayoutProcessor.enableKernLiga();
    }

    public static Font craete(Path ttf, String alias, float size, boolean left_to_right) {
        FontFactory.register(ttf.toAbsolutePath().toString(), alias);
        var font = FontFactory.getFont(alias, BaseFont.IDENTITY_H, true, size);
        if (left_to_right) {
            LayoutProcessor.setRunDirectionLtr(font);
        } else {
            LayoutProcessor.setRunDirectionRtl(font);
        }
        return font;
    }
}
