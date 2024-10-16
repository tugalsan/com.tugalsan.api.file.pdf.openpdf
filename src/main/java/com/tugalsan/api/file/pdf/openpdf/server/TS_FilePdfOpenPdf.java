package com.tugalsan.api.file.pdf.openpdf.server;

import com.tugalsan.api.log.server.TS_Log;
import java.nio.file.Path;
import com.tugalsan.api.file.common.server.TS_FileCommonAbstract;
import com.tugalsan.api.file.common.server.TS_FileCommonConfig;
import com.tugalsan.api.file.server.TS_FileUtils;
import com.tugalsan.api.function.client.TGS_Func_In1;
import com.tugalsan.api.url.client.TGS_Url;
import java.awt.image.BufferedImage;

//https://github.com/LibrePDF/OpenPDF/wiki/Tutorial
//https://web.archive.org/web/20090703162811/http://itextdocs.lowagie.com/tutorial/
@Deprecated //TODO Migration from TS_FilePdfItext to here. Cooking...
public class TS_FilePdfOpenPdf extends TS_FileCommonAbstract {

    final private static TS_Log d = TS_Log.of(TS_FilePdfOpenPdf.class);

//    public TS_FilePdfItextUtils pdf;
//    public PdfPTable pdfTable = null;
//    public PdfPCell pdfCell = null;
//    public Paragraph pdfParag = null;
//    public Font pdfFont;
//    public Font pdfFont_half;
//    public BaseColor pdfFontColor = BaseColor.BLACK;
    private TS_FileCommonConfig fileCommonConfig;

    private TS_FilePdfOpenPdf(boolean enabled, Path localFile, TGS_Url remoteFile) {
        super(enabled, localFile, remoteFile);
    }

    public static void use(boolean enabled, TS_FileCommonConfig fileCommonConfig, Path localFile, TGS_Url remoteFile, TGS_Func_In1<TS_FilePdfOpenPdf> pdf) {
//        var instance = new TS_FilePdfItext(enabled, localFile, remoteFile);
        try {
//            instance.use_init(fileCommonConfig);
//            pdf.run(instance);
        } catch (Exception e) {
//            instance.saveFile(e.getMessage());
            throw e;
        } finally {
//            instance.saveFile(null);
        }
    }

    private void use_init(TS_FileCommonConfig fileCommonConfig) {
        this.fileCommonConfig = fileCommonConfig;
        if (isClosed()) {
            return;
        }
//        pdf = new TS_FilePdfItextUtils(localFile);
        setFontStyle();
    }

    @Override
    public boolean saveFile(String errorSource) {
        d.ce("saveFile", "todo");
        TS_FileUtils.createFileIfNotExists(localFile);
        return true;
    }

    @Override
    public boolean createNewPage(int pageSizeAX, boolean landscape, Integer marginLeft, Integer marginRight, Integer marginTop, Integer marginBottom) {
        d.ce("createNewPage", "todo");
        return true;
    }

    @Override
    public boolean addImage(BufferedImage pstImage, Path pstImageLoc, boolean textWrap, int left0_center1_right2, long imageCounter) {
        d.ce("addImage", "todo");
        return true;
    }

    @Override
    public boolean beginTableCell(int rowSpan, int colSpan, Integer cellHeight) {
        d.ce("beginTableCell", "todo");
        return true;
    }

    @Override
    public boolean endTableCell(int rotationInDegrees_0_90_180_270) {
        d.ce("endTableCell", "todo");
        return true;
    }

    @Override
    public boolean beginTable(int[] relColSizes) {
        d.ce("beginTable", "todo");
        return true;
    }

    @Override
    public boolean endTable() {
        d.ce("endTable", "todo");
        return true;
    }

    @Override
    public boolean beginText(int allign_Left0_center1_right2_just3) {
        d.ce("beginText", "todo");
        return true;
    }

    @Override
    public boolean endText() {
        d.ce("endText", "todo");
        return true;
    }

    @Override
    public boolean addText(String text) {
        d.ce("addText", "todo");
        return true;
    }

    @Override
    public boolean addLineBreak() {
        d.ce("addLineBreak", "todo");
        return true;
    }

    @Override
    public boolean setFontStyle() {
        d.ce("setFontStyle", "todo");
        return true;
    }

    @Override
    public boolean setFontHeight() {
        d.ce("setFontHeight", "todo");
        return true;
    }

    @Override
    public boolean setFontColor() {
        d.ce("setFontColor", "todo");
        return true;
    }
}
