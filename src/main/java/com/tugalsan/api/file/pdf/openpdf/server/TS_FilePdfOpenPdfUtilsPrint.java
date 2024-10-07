package com.tugalsan.api.file.pdf.openpdf.server;

import com.lowagie.text.error_messages.MessageLocalization;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * This class enables you to call an executable that will show a PDF file.
 */
public class TS_FilePdfOpenPdfUtilsPrint {

    /**
     * The path to Acrobat Reader.
     */
    private static String acroread = null;

    /**
     * Performs an action on a PDF document.
     *
     * @param fileName
     * @param parameters
     * @param waitForTermination
     * @return a process
     * @throws IOException
     */
    private static Process action(final String fileName,
            String parameters, boolean waitForTermination) throws IOException {
        Process process = null;
        if (parameters.trim().length() > 0) {
            parameters = " " + parameters.trim();
        } else {
            parameters = "";
        }
        if (acroread != null) {
            process = Runtime.getRuntime().exec(createCommand(
                    acroread, parameters, " \"", fileName, "\""));
        } else if (isWindows()) {
            if (isWindows9X()) {
                process = Runtime.getRuntime().exec(createCommand(
                        "command.com /C start acrord32", parameters, " \"", fileName, "\""));
            } else {
                process = Runtime.getRuntime().exec(createCommand(
                        "cmd /c start acrord32", parameters, " \"", fileName, "\""));
            }
        } else if (isMac()) {
            if (parameters.trim().length() == 0) {
                process = Runtime.getRuntime().exec(
                        new String[]{"/usr/bin/open", fileName});
            } else {
                process = Runtime.getRuntime().exec(
                        new String[]{"/usr/bin/open", parameters.trim(), fileName});
            }
        }
        try {
            if (process != null && waitForTermination) {
                process.waitFor();
            }
        } catch (InterruptedException ignored) {
        }
        return process;
    }

    /**
     * Creates a command string array from the string arguments.
     *
     * @param arguments
     * @return String[] of commands
     */
    private static String[] createCommand(String... arguments) {
        return arguments;
    }

    /**
     * Opens a PDF document.
     *
     * @param fileName the name of the file to open
     * @param waitForTermination true to wait for termination, false otherwise
     * @return a process
     * @throws IOException on error
     */
    public static Process openDocument(String fileName,
            boolean waitForTermination) throws IOException {
        return action(fileName, "", waitForTermination);
    }

    /**
     * Opens a PDF document.
     *
     * @param file the file to open
     * @param waitForTermination true to wait for termination, false otherwise
     * @return a process
     * @throws IOException on error
     */
    public static Process openDocument(File file,
            boolean waitForTermination) throws IOException {
        return openDocument(file.getAbsolutePath(), waitForTermination);
    }

    /**
     * Opens a PDF document.
     *
     * @param fileName the name of the file to open
     * @return a process
     * @throws IOException on error
     */
    public static Process openDocument(String fileName) throws IOException {
        return openDocument(fileName, false);
    }

    /**
     * Opens a PDF document.
     *
     * @param file the file to open
     * @return a process
     * @throws IOException on error
     */
    public static Process openDocument(File file) throws IOException {
        return openDocument(file, false);
    }

    /**
     * Prints a PDF document.
     *
     * @param fileName the name of the file to print
     * @param waitForTermination true to wait for termination, false otherwise
     * @return a process
     * @throws IOException on error
     */
    public static Process printDocument(String fileName,
            boolean waitForTermination) throws IOException {
        return action(fileName, "/p", waitForTermination);
    }

    /**
     * Prints a PDF document.
     *
     * @param file the File to print
     * @param waitForTermination true to wait for termination, false otherwise
     * @return a process
     * @throws IOException on error
     */
    public static Process printDocument(File file,
            boolean waitForTermination) throws IOException {
        return printDocument(file.getAbsolutePath(), waitForTermination);
    }

    /**
     * Prints a PDF document.
     *
     * @param fileName the name of the file to print
     * @return a process
     * @throws IOException on error
     */
    public static Process printDocument(String fileName) throws IOException {
        return printDocument(fileName, false);
    }

    /**
     * Prints a PDF document.
     *
     * @param file the File to print
     * @return a process
     * @throws IOException on error
     */
    public static Process printDocument(File file) throws IOException {
        return printDocument(file, false);
    }

    /**
     * Prints a PDF document without opening a Dialog box.
     *
     * @param fileName the name of the file to print
     * @param waitForTermination true to wait for termination, false otherwise
     * @return a process
     * @throws IOException on error
     */
    public static Process printDocumentSilent(String fileName,
            boolean waitForTermination) throws IOException {
        return action(fileName, "/p /h", waitForTermination);
    }

    /**
     * Prints a PDF document without opening a Dialog box.
     *
     * @param file the File to print
     * @param waitForTermination true to wait for termination, false otherwise
     * @return a process
     * @throws IOException on error
     */
    public static Process printDocumentSilent(File file,
            boolean waitForTermination) throws IOException {
        return printDocumentSilent(file.getAbsolutePath(), waitForTermination);
    }

    /**
     * Prints a PDF document without opening a Dialog box.
     *
     * @param fileName the name of the file to print
     * @return a process
     * @throws IOException on error
     */
    public static Process printDocumentSilent(String fileName) throws IOException {
        return printDocumentSilent(fileName, false);
    }

    /**
     * Prints a PDF document without opening a Dialog box.
     *
     * @param file the File to print
     * @return a process
     * @throws IOException on error
     */
    public static Process printDocumentSilent(File file) throws IOException {
        return printDocumentSilent(file, false);
    }

    /**
     * Launches a browser opening an URL.
     *
     * @param url the URL you want to open in the browser
     * @throws IOException on error
     */
    public static void launchBrowser(String url) throws IOException {
        try {
            if (isMac()) {
                Class<?> macUtils = Class.forName("com.apple.mrj.MRJFileUtils");
                Method openURL = macUtils.getDeclaredMethod("openURL", String.class);
                openURL.invoke(null, url);
            } else if (isWindows()) {
                Runtime.getRuntime().exec(createCommand("rundll32 url.dll,FileProtocolHandler ", url));
            } else { //assume Unix or Linux
                String[] browsers = {
                    "firefox", "opera", "konqueror", "mozilla", "netscape"};
                String browser = null;
                for (int count = 0; count < browsers.length && browser == null; count++) {
                    if (Runtime.getRuntime().exec(new String[]{"which", browsers[count]}).waitFor() == 0) {
                        browser = browsers[count];
                    }
                }
                if (browser == null) {
                    throw new Exception(MessageLocalization.getComposedMessage("could.not.find.web.browser"));
                } else {
                    Runtime.getRuntime().exec(new String[]{browser, url});
                }
            }
        } catch (Exception e) {
            throw new IOException(MessageLocalization.getComposedMessage("error.attempting.to.launch.web.browser"));
        }
    }

    /**
     * Checks the Operating System.
     *
     * @return true if the current os is Windows
     */
    public static boolean isWindows() {
        String os = System.getProperty("os.name").toLowerCase();
        return os.contains("windows") || os.contains("nt");
    }

    /**
     * Checks the Operating System.
     *
     * @return true if the current os is Windows
     */
    public static boolean isWindows9X() {
        String os = System.getProperty("os.name").toLowerCase();
        return os.equals("windows 95") || os.equals("windows 98");
    }

    /**
     * Checks the Operating System.
     *
     * @return true if the current os is Apple
     */
    public static boolean isMac() {
        String os = System.getProperty("os.name").toLowerCase();
        return os.contains("mac");
    }

    /**
     * Checks the Operating System.
     *
     * @return true if the current os is Linux
     */
    public static boolean isLinux() {
        String os = System.getProperty("os.name").toLowerCase();
        return os.contains("linux");
    }
}
