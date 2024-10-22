package com.tugalsan.api.file.pdf.openpdf.server;

import com.lowagie.text.Image;
import com.tugalsan.api.charset.client.TGS_CharSetCast;
import com.tugalsan.api.file.img.server.TS_FileImageUtils;
import com.tugalsan.api.file.server.TS_DirectoryUtils;
import com.tugalsan.api.file.server.TS_FileUtils;
import com.tugalsan.api.log.server.TS_Log;
import com.tugalsan.api.shape.client.TGS_ShapeDimension;
import com.tugalsan.api.union.client.TGS_UnionExcuse;
import com.tugalsan.api.union.client.TGS_UnionExcuseVoid;
import com.tugalsan.api.unsafe.client.TGS_UnSafe;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class TS_FilePdfOpenPdfUtilsImage {

    private static TS_Log d = TS_Log.of(TS_FilePdfOpenPdfUtilsImage.class);

    public static TGS_UnionExcuseVoid toPdf(TS_FilePdfOpenPdfUtilsPage.PageInfo pageInfo, Path dstPdf, float quality, List<Path> srcImages) {
        return toPdf(pageInfo, dstPdf, quality, srcImages.toArray(Path[]::new));
    }

    //TS_FilePdfOpenPdfUtilsPage.PAGE_INFO_A4_PORT_0_0_0_0
    public static TGS_UnionExcuseVoid toPdf(TS_FilePdfOpenPdfUtilsPage.PageInfo pageInfo, Path dstPdf, float quality, Path... srcImages) {
        return TGS_UnSafe.call(() -> {
            TS_FilePdfOpenPdfUtilsDocument.run_doc_with_writer(pageInfo, dstPdf, (doc, pdfWriter) -> {
                TGS_UnSafe.run(() -> {
                    var firstPage = true;
                    for (var srcImage : srcImages) {
                        //var pathImageStr = srcImage.toAbsolutePath().toString();
                        //doc.add(new Paragraph(pathImageStr));
                        var bi = TS_FileImageUtils.readImageFromFile(srcImage, true);
                        var biScaled = TS_FileImageUtils.resize_and_rotate(
                                bi,
                                TGS_ShapeDimension.of(
                                        (int) pageInfo.toRectangle().getWidth(),
                                        (int) pageInfo.toRectangle().getHeight()
                                ),
                                0, true
                        );
                        var pdfImage = Image.getInstance(pdfWriter, biScaled, quality);
                        pdfImage.setAbsolutePosition(0, 0);
                        pdfImage.scaleToFit(pageInfo.toRectangle().getWidth(), pageInfo.toRectangle().getHeight());
                        if (!firstPage) {
                            doc.newPage();
                        }
                        doc.add(pdfImage);
                        if (firstPage) {
                            firstPage = false;
                        }
                    }
                });
            });
            return TGS_UnionExcuseVoid.ofVoid();
        }, e -> TGS_UnionExcuseVoid.ofExcuse(e));
    }

    public static boolean isSupported(Path imgFile) {
        var fn = TGS_CharSetCast.current().toLowerCase(imgFile.getFileName().toString());
        return fn.endsWith(".jpg") || fn.endsWith(".jpeg") || fn.endsWith(".tif") || fn.endsWith(".tiff") || fn.endsWith(".gif") || fn.endsWith(".bmp") || fn.endsWith(".png") || fn.endsWith(".wmf");
    }

    public static List<TGS_UnionExcuse<Path>> toPdf_fromDir(TS_FilePdfOpenPdfUtilsPage.PageInfo pageInfo, Path srcDir, float quality, boolean skipIfExists, boolean deleteIMGAfterConversion) {
        d.ci("ofPdf_fromImageFolder_A4PORT", "srcDir", srcDir, "#10");
        var subFiles = TS_DirectoryUtils.subFiles(srcDir, null, false, false);
        d.ci("ofPdf_fromImageFolder_A4PORT", "srcDir", srcDir, "#20");
        List<TGS_UnionExcuse<Path>> convertedFiles = new ArrayList();
        d.ci("ofPdf_fromImageFolder_A4PORT", "srcDir", srcDir, "#30");
        subFiles.stream().filter(subFile -> isSupported(subFile)).forEach(imgFile -> {
            d.ci("ofPdf_fromImageFolder_A4PORT", "srcDir", srcDir, "#100", ":1", imgFile);
            var pdfFile = imgFile.resolveSibling(TS_FileUtils.getNameLabel(imgFile) + ".pdf");
            d.ci("ofPdf_fromImageFolder_A4PORT", "srcDir", srcDir, "#100", ":2", imgFile);
            if (TS_FileUtils.isExistFile(pdfFile)) {
                if (skipIfExists) {
                    d.ci("ofPdf_fromImageFolder_A4PORT", "srcDir", srcDir, "#100", "skipIfExists", imgFile);
                    return;
                } else {
                    TS_FileUtils.deleteFileIfExists(pdfFile);
                }
            }
            d.ci("ofPdf_fromImageFolder_A4PORT", "srcDir", srcDir, "#100", ":3", imgFile);
            var u_file = toPdf(pageInfo, pdfFile, quality, imgFile);
            d.ci("ofPdf_fromImageFolder_A4PORT", "srcDir", srcDir, "#100", ":4", imgFile);
            if (u_file.isExcuse()) {
                d.ce("ofPdf_fromImageFolder_A4PORT", "srcDir", srcDir, "#100", "isExcuse", imgFile, u_file.excuse().getMessage());
                convertedFiles.add(u_file.toExcuse());
            } else {
                d.ci("ofPdf_fromImageFolder_A4PORT", "srcDir", srcDir, "#100", ":5", imgFile);
                convertedFiles.add(TGS_UnionExcuse.of(pdfFile));
            }
            d.ci("ofPdf_fromImageFolder_A4PORT", "srcDir", srcDir, "#100", ":6", imgFile);
            if (deleteIMGAfterConversion) {
                TS_FileUtils.deleteFileIfExists(imgFile);
            }
            d.ci("ofPdf_fromImageFolder_A4PORT", "srcDir", srcDir, "#100", ":7", imgFile);
        });
        d.ci("ofPdf_fromImageFolder_A4PORT", "srcDir", srcDir, "#200");
        return convertedFiles;
    }
}
