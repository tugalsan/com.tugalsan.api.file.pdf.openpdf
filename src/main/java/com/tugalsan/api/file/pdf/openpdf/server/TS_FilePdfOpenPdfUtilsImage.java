package com.tugalsan.api.file.pdf.openpdf.server;

import module com.tugalsan.api.charset;
import module com.tugalsan.api.file.img;
import module com.tugalsan.api.file;
import module com.tugalsan.api.log;
import module com.tugalsan.api.shape;
import module com.tugalsan.api.union;
import module com.tugalsan.api.function;
import module java.desktop;
import module com.github.librepdf.openpdf;
import com.lowagie.text.Document;
import com.lowagie.text.Image;
import java.nio.file.*;
import java.util.*;
import java.util.List;
import java.util.stream.*;

public class TS_FilePdfOpenPdfUtilsImage {

    private TS_FilePdfOpenPdfUtilsImage() {

    }

    final private static TS_Log d = TS_Log.of(TS_FilePdfOpenPdfUtilsImage.class);

    public static TGS_UnionExcuseVoid toPdfFromImage(TS_FilePdfOpenPdfUtilsPageCompress.CompressionLevel cLvl,
            TS_FilePdfOpenPdfUtilsPage.PageInfo pageInfo_orNullForImageSize,
            Path dstPdf, float quality, List<BufferedImage> srcImages) {
        return toPdfFromImage(cLvl, pageInfo_orNullForImageSize, dstPdf, quality, srcImages.toArray(BufferedImage[]::new));
    }

    public static TGS_UnionExcuseVoid toPdfFromPath(TS_FilePdfOpenPdfUtilsPageCompress.CompressionLevel cLvl,
            TS_FilePdfOpenPdfUtilsPage.PageInfo pageInfo_orNullForImageSize,
            Path dstPdf, float quality, List<Path> srcImages) {
        return toPdfFromPath(cLvl, pageInfo_orNullForImageSize, dstPdf, quality, srcImages.toArray(Path[]::new));
    }

    //TS_FilePdfOpenPdfUtilsPage.PAGE_INFO_A4_PORT_0_0_0_0
    public static TGS_UnionExcuseVoid toPdfFromImage(TS_FilePdfOpenPdfUtilsPageCompress.CompressionLevel cLvl,
            TS_FilePdfOpenPdfUtilsPage.PageInfo pageInfo_orNullForImageSize,
            Path dstPdf, float quality, BufferedImage... srcImages) {
        var srcImagePaths = new Path[srcImages.length];
        IntStream.range(0, srcImages.length).parallel().forEach(i -> {
            srcImagePaths[i] = TS_FileImageUtils.toFileTemp(srcImages[i], quality, ".jpg");
        });
        return toPdfFromPath(cLvl, pageInfo_orNullForImageSize, dstPdf, quality, srcImagePaths);
    }

    //TS_FilePdfOpenPdfUtilsPage.PAGE_INFO_A4_PORT_0_0_0_0
    public static TGS_UnionExcuseVoid toPdfFromPath(TS_FilePdfOpenPdfUtilsPageCompress.CompressionLevel cLvl,
            TS_FilePdfOpenPdfUtilsPage.PageInfo pageInfo_orNullForImageSize,
            Path dstPdf, float quality, Path... srcImages) {
        if (pageInfo_orNullForImageSize == null) {
            return toPdf_useImageSize(cLvl, dstPdf, quality, srcImages);
        }
        return TGS_FuncMTCUtils.call(() -> {
            TS_FilePdfOpenPdfUtilsDocument.run_doc_with_writer(cLvl, pageInfo_orNullForImageSize, dstPdf, (doc, pdfWriter) -> {
                TGS_FuncMTCUtils.run(() -> {
                    var firstPage = true;
                    for (var srcImage : srcImages) {
                        //var pathImageStr = srcImage.toAbsolutePath().toString();
                        //doc.add(new Paragraph(pathImageStr));
                        var bi = TS_FileImageUtils.readImageFromFile(srcImage, true);
                        var biScaled = TS_FileImageUtils.resize_and_rotate(
                                bi,
                                TGS_ShapeDimension.of(
                                        (int) pageInfo_orNullForImageSize.toRectangle().getWidth(),
                                        (int) pageInfo_orNullForImageSize.toRectangle().getHeight()
                                ),
                                0, true
                        );
                        var pdfImage = Image.getInstance(pdfWriter, biScaled, quality);
                        pdfImage.setAbsolutePosition(0, 0);
                        pdfImage.scaleToFit(pageInfo_orNullForImageSize.toRectangle().getWidth(), pageInfo_orNullForImageSize.toRectangle().getHeight());
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

    private static TGS_UnionExcuseVoid toPdf_useImageSize(TS_FilePdfOpenPdfUtilsPageCompress.CompressionLevel cLvl, Path dstPdf, float quality, Path... srcImages) {
        if (srcImages == null || srcImages.length == 0) {
            return TGS_UnionExcuseVoid.ofExcuse(d.className(), "toPdf_protectImageSize", "srcImages == null || srcImages.length == 0");
        }
        return TGS_FuncMTCUtils.call(() -> {
            var pdfImageFirst = Image.getInstance(srcImages[0].toAbsolutePath().toString());
            try (var os = Files.newOutputStream(dstPdf)) {
                try (var _doc = new Document(pdfImageFirst, 0, 0, 0, 0)) {
                    var pdfWriter = PdfWriter.getInstance(_doc, os);
                    TS_FilePdfOpenPdfUtilsPageCompress.set(pdfWriter, cLvl);
                    _doc.open();
                    pdfWriter.getInfo().put(PdfName.CREATOR, new PdfString(Document.getVersion()));
                    TGS_FuncMTCUtils.run(() -> {
                        var firstPage = true;
                        for (var srcImage : srcImages) {
                            var bi = TS_FileImageUtils.readImageFromFile(srcImage, true);
                            var pdfImage = Image.getInstance(pdfWriter, bi, quality);
                            pdfImage.setAbsolutePosition(0, 0);
                            if (!firstPage) {
                                _doc.setPageSize(pdfImage);
                                _doc.newPage();
                            }
                            _doc.add(pdfImage);
                            if (firstPage) {
                                firstPage = false;
                            }
                        }
                    });
                    return TGS_UnionExcuseVoid.ofVoid();
                }
            }
        }, e -> TGS_UnionExcuseVoid.ofExcuse(e));
    }

    public static boolean isSupported(Path imgFile) {
        var fn = TGS_CharSetCast.current().toLowerCase(imgFile.getFileName().toString());
        return fn.endsWith(".jpg") || fn.endsWith(".jpeg") || fn.endsWith(".tif") || fn.endsWith(".tiff") || fn.endsWith(".gif") || fn.endsWith(".bmp") || fn.endsWith(".png") || fn.endsWith(".wmf");
    }

    public static List<TGS_UnionExcuse<Path>> toPdf_fromDir(TS_FilePdfOpenPdfUtilsPageCompress.CompressionLevel cLvl, TS_FilePdfOpenPdfUtilsPage.PageInfo pageInfo_orNullForImageSize, Path srcDir, float quality, boolean skipIfExists, boolean deleteIMGAfterConversion) {
        if (pageInfo_orNullForImageSize == null) {
            return toPdf_fromDir_useImageSize(cLvl, srcDir, quality, skipIfExists, deleteIMGAfterConversion);
        }
        d.ci("toPdf_fromDir", "srcDir", srcDir, "#10");
        var subFiles = TS_DirectoryUtils.subFiles(srcDir, null, false, false);
        d.ci("toPdf_fromDir", "srcDir", srcDir, "#20");
        List<TGS_UnionExcuse<Path>> convertedFiles = new ArrayList();
        d.ci("toPdf_fromDir", "srcDir", srcDir, "#30");
        subFiles.stream().filter(subFile -> isSupported(subFile)).forEach(imgFile -> {
            d.ci("toPdf_fromDir", "srcDir", srcDir, "#100", ":1", imgFile);
            var pdfFile = imgFile.resolveSibling(TS_FileUtils.getNameLabel(imgFile) + ".pdf");
            d.ci("toPdf_fromDir", "srcDir", srcDir, "#100", ":2", imgFile);
            if (TS_FileUtils.isExistFile(pdfFile)) {
                if (skipIfExists) {
                    d.ci("toPdf_fromDir", "srcDir", srcDir, "#100", "skipIfExists", imgFile);
                    return;
                } else {
                    TS_FileUtils.deleteFileIfExists(pdfFile);
                }
            }
            d.ci("toPdf_fromDir", "srcDir", srcDir, "#100", ":3", imgFile);
            var u_file = toPdfFromPath(cLvl, pageInfo_orNullForImageSize, pdfFile, quality, imgFile);
            d.ci("toPdf_fromDir", "srcDir", srcDir, "#100", ":4", imgFile);
            if (u_file.isExcuse()) {
                d.ce("toPdf_fromDir", "srcDir", srcDir, "#100", "isExcuse", imgFile, u_file.excuse().getMessage());
                convertedFiles.add(u_file.toExcuse());
            } else {
                d.ci("toPdf_fromDir", "srcDir", srcDir, "#100", ":5", imgFile);
                convertedFiles.add(TGS_UnionExcuse.of(pdfFile));
            }
            d.ci("toPdf_fromDir", "srcDir", srcDir, "#100", ":6", imgFile);
            if (deleteIMGAfterConversion) {
                TS_FileUtils.deleteFileIfExists(imgFile);
            }
            d.ci("toPdf_fromDir", "srcDir", srcDir, "#100", ":7", imgFile);
        });
        d.ci("toPdf_fromDir", "srcDir", srcDir, "#200");
        return convertedFiles;
    }

    private static List<TGS_UnionExcuse<Path>> toPdf_fromDir_useImageSize(TS_FilePdfOpenPdfUtilsPageCompress.CompressionLevel cLvl, Path srcDir, float quality, boolean skipIfExists, boolean deleteIMGAfterConversion) {
        d.ci("toPdf_fromDir_useImageSize", "srcDir", srcDir, "#10");
        var subFiles = TS_DirectoryUtils.subFiles(srcDir, null, false, false);
        d.ci("toPdf_fromDir_useImageSize", "srcDir", srcDir, "#20");
        List<TGS_UnionExcuse<Path>> convertedFiles = new ArrayList();
        d.ci("toPdf_fromDir_useImageSize", "srcDir", srcDir, "#30");
        subFiles.stream().filter(subFile -> isSupported(subFile)).forEach(imgFile -> {
            d.ci("toPdf_fromDir_useImageSize", "srcDir", srcDir, "#100", ":1", imgFile);
            var pdfFile = imgFile.resolveSibling(TS_FileUtils.getNameLabel(imgFile) + ".pdf");
            d.ci("toPdf_fromDir_useImageSize", "srcDir", srcDir, "#100", ":2", imgFile);
            if (TS_FileUtils.isExistFile(pdfFile)) {
                if (skipIfExists) {
                    d.ci("toPdf_fromDir_useImageSize", "srcDir", srcDir, "#100", "skipIfExists", imgFile);
                    return;
                } else {
                    TS_FileUtils.deleteFileIfExists(pdfFile);
                }
            }
            d.ci("toPdf_fromDir_useImageSize", "srcDir", srcDir, "#100", ":3", imgFile);
            var u_file = toPdf_useImageSize(cLvl, pdfFile, quality, imgFile);
            d.ci("toPdf_fromDir_useImageSize", "srcDir", srcDir, "#100", ":4", imgFile);
            if (u_file.isExcuse()) {
                d.ce("toPdf_fromDir_useImageSize", "srcDir", srcDir, "#100", "isExcuse", imgFile, u_file.excuse().getMessage());
                convertedFiles.add(u_file.toExcuse());
            } else {
                d.ci("toPdf_fromDir_useImageSize", "srcDir", srcDir, "#100", ":5", imgFile);
                convertedFiles.add(TGS_UnionExcuse.of(pdfFile));
            }
            d.ci("toPdf_fromDir_useImageSize", "srcDir", srcDir, "#100", ":6", imgFile);
            if (deleteIMGAfterConversion) {
                TS_FileUtils.deleteFileIfExists(imgFile);
            }
            d.ci("toPdf_fromDir_useImageSize", "srcDir", srcDir, "#100", ":7", imgFile);
        });
        d.ci("toPdf_fromDir_useImageSize", "srcDir", srcDir, "#200");
        return convertedFiles;
    }
}
