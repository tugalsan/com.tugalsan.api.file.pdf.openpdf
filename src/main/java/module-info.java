module com.tugalsan.api.file.pdf.openpdf {
    requires com.github.librepdf.openpdf;
    requires com.tugalsan.api.file.common;
    requires com.tugalsan.api.log;
    requires com.tugalsan.api.function;
    requires com.tugalsan.api.union;
    requires com.tugalsan.api.unsafe;
    exports com.tugalsan.api.file.pdf.openpdf.server;
}
