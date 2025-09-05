package com.tugalsan.api.file.pdf.openpdf.server;

import com.lowagie.text.pdf.PdfEncryptor;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;
import com.tugalsan.api.function.client.TGS_FuncUtils;
import com.tugalsan.api.log.server.TS_Log;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

public class TS_FilePdfOpenPdfUtilsPermissions {

    private TS_FilePdfOpenPdfUtilsPermissions() {

    }

    private static TS_Log d() {
        return d.orElse(TS_Log.of(TS_FilePdfOpenPdfUtilsPermissions.class));
    }
    final private static StableValue<TS_Log> d = StableValue.of();

    private final static int INPUT_FILE = 0;
    private final static int OUTPUT_FILE = 1;
    private final static int USER_PASSWORD = 2;
    private final static int OWNER_PASSWORD = 3;
    private final static int PERMISSIONS = 4;
    private final static int STRENGTH = 5;
    private final static int MOREINFO = 6;
    private final static int[] permit = {
        PdfWriter.ALLOW_PRINTING,
        PdfWriter.ALLOW_MODIFY_CONTENTS,
        PdfWriter.ALLOW_COPY,
        PdfWriter.ALLOW_MODIFY_ANNOTATIONS,
        PdfWriter.ALLOW_FILL_IN,
        PdfWriter.ALLOW_SCREENREADERS,
        PdfWriter.ALLOW_ASSEMBLY,
        PdfWriter.ALLOW_DEGRADED_PRINTING};

    private static void usage() {
        d().cr(
                "usage", "usage: input_file output_file user_password owner_password permissions 128|40 [new info string pairs]");
        d().cr("usage", "permissions is 8 digit long 0 or 1. Each digit has a particular security function:");
        d().cr("usage", "AllowPrinting");
        d().cr("usage", "AllowModifyContents");
        d().cr("usage", "AllowCopy");
        d().cr("usage", "AllowModifyAnnotations");
        d().cr("usage", "AllowFillIn (128 bit only)");
        d().cr("usage", "AllowScreenReaders (128 bit only)");
        d().cr("usage", "AllowAssembly (128 bit only)");
        d().cr("usage", "AllowDegradedPrinting (128 bit only)");
        d().cr("usage", "Example permissions to copy and print would be: 10100000");
    }

    /**
     * Encrypts a PDF document.
     *
     * @param args input_file output_file user_password owner_password
     * permissions 128|40 [new info string pairs]
     */
    public static void test(String[] args) {
        d().cr("test", "PDF document encryptor");
        if (args.length <= STRENGTH || args[PERMISSIONS].length() != 8) {
            usage();
            return;
        }
        try {
            int permissions = 0;
            String p = args[PERMISSIONS];
            for (int k = 0; k < p.length(); ++k) {
                permissions |= (p.charAt(k) == '0' ? 0 : permit[k]);
            }
            d().cr("Reading " + args[INPUT_FILE]);
            PdfReader reader = new PdfReader(args[INPUT_FILE]);
            d().cr("Writing " + args[OUTPUT_FILE]);
            Map<String, String> moreInfo = new HashMap<>();
            for (int k = MOREINFO; k < args.length - 1; k += 2) {
                moreInfo.put(args[k], args[k + 1]);
            }
            try (var os = new FileOutputStream(args[OUTPUT_FILE])) {
                PdfEncryptor.encrypt(
                        reader,
                        os,
                        args[USER_PASSWORD].getBytes(),
                        args[OWNER_PASSWORD].getBytes(),
                        permissions,
                        args[STRENGTH].equals("128"),
                        moreInfo
                );
            }
            d().cr("test", "Done.");
        } catch (Exception e) {
            TGS_FuncUtils.throwIfInterruptedException(e);
            e.printStackTrace();
        }
    }
}
