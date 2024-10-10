module com.tugalsan.api.file.pdf.openpdf {
    requires com.github.librepdf.openpdf;
    requires com.tugalsan.api.file.common;
    requires com.tugalsan.api.log;
    requires com.tugalsan.api.thread;
    requires com.tugalsan.api.time;
    requires com.tugalsan.api.function;
    requires com.tugalsan.api.union;
    requires com.tugalsan.api.charset;
    requires com.tugalsan.api.unsafe;
    requires com.tugalsan.api.os;
    requires com.tugalsan.api.string;
    exports com.tugalsan.api.file.pdf.openpdf.server;
}
