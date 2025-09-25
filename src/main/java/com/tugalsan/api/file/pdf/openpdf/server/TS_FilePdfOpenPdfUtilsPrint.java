package com.tugalsan.api.file.pdf.openpdf.server;

import module com.tugalsan.api.os;
import java.io.*;

public class TS_FilePdfOpenPdfUtilsPrint {

    private TS_FilePdfOpenPdfUtilsPrint() {

    }

    private static String acroread = null;

    private static Process action(final String fileName, String parameters, boolean waitForTermination) throws IOException {
        Process process = null;
        if (parameters.trim().length() > 0) {
            parameters = " " + parameters.trim();
        } else {
            parameters = "";
        }
        if (acroread != null) {
            process = Runtime.getRuntime().exec(new String[]{acroread, parameters, " \"", fileName, "\""});
        } else if (TS_OsPlatformUtils.isWindows()) {
            if (TS_OsPlatformUtils.isWindows9X()) {
                process = Runtime.getRuntime().exec(new String[]{"command.com /C start acrord32", parameters, " \"", fileName, "\""});
            } else {
                process = Runtime.getRuntime().exec(new String[]{"cmd /c start acrord32", parameters, " \"", fileName, "\""});
            }
        } else if (TS_OsPlatformUtils.isMac()) {
            if (parameters.trim().length() == 0) {
                process = Runtime.getRuntime().exec(new String[]{"/usr/bin/open", fileName});
            } else {
                process = Runtime.getRuntime().exec(new String[]{"/usr/bin/open", parameters.trim(), fileName});
            }
        }
        try {
            if (process != null && waitForTermination) {
                process.waitFor();
            }
        } catch (InterruptedException _) {
        }
        return process;
    }

    public static Process openDocument(String fileName, boolean waitForTermination) throws IOException {
        return action(fileName, "", waitForTermination);
    }

    public static Process printDocument(String fileName, boolean waitForTermination) throws IOException {
        return action(fileName, "/p", waitForTermination);
    }

    public static Process printDocumentSilent(String fileName, boolean waitForTermination) throws IOException {
        return action(fileName, "/p /h", waitForTermination);
    }

}
