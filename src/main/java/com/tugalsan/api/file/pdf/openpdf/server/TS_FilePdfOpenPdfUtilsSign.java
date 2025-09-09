package com.tugalsan.api.file.pdf.openpdf.server;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.PdfDate;
import com.lowagie.text.pdf.PdfDictionary;
import com.lowagie.text.pdf.PdfName;
import com.lowagie.text.pdf.PdfPKCS7;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfString;
import com.lowagie.text.pdf.PdfWriter;
import com.tugalsan.api.log.server.TS_Log;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class TS_FilePdfOpenPdfUtilsSign {

    private TS_FilePdfOpenPdfUtilsSign() {

    }

    final private static Supplier<TS_Log> d = StableValue.supplier(() -> TS_Log.of(TS_FilePdfOpenPdfUtilsSign.class));

    public static void test() {
        try {
            addUnverifiedSignature(true);
            addUnverifiedSignature(false);
            extractVerifiedCryptoSignature();

        } catch (DocumentException e) {
            d.get().ce("test", e.getMessage());
        }
    }

    private static void addUnverifiedSignature(boolean visible) {
        try {
            String visibility = visible ? "visible" : "invisible";
            String description = "Document with " + visibility + " signature";
            d.get().cr("addUnverifiedSignature", description);

            var document = new Document();
            var baos = new ByteArrayOutputStream();
            var writer = PdfWriter.getInstance(document, baos);
            document.open();
            writer.getInfo().put(PdfName.CREATOR, new PdfString(Document.getVersion()));

            document.add(new Paragraph(description));
            document.close();

            var reader = new PdfReader(baos.toByteArray());
            // A verified signature would require a private key plus a valid certificate. see the JavaDoc of this
            // method for details
            var stp = PdfStamper.createSignature(reader, baos, '\0', null, true);

            var signDate = Calendar.getInstance();
            stp.setEnforcedModificationDate(signDate);

            var sap = stp.getSignatureAppearance();
            var dic = new PdfDictionary();
            // self signed
            dic.put(PdfName.FILTER, PdfName.ADOBE_PPKLITE);
            dic.put(PdfName.M, new PdfDate(signDate));
            sap.setCryptoDictionary(dic);
            sap.setSignDate(signDate);

            if (visible) {
                sap.setVisibleSignature(new Rectangle(100, 100), 1);
                sap.setLayer2Text("Test signer");
            }

            // exclude the signature from the hash of the PDF and fill the resulting gap
            Map<PdfName, Integer> exc = new HashMap<>();
            exc.put(PdfName.CONTENTS, 10);
            sap.preClose(exc);
            var update = new PdfDictionary();
            update.put(PdfName.CONTENTS, new PdfString("aaaa").setHexWriting(true));
            sap.close(update);

            var fileNamePrefix = visibility.substring(0, 1).toUpperCase() + visibility.substring(1);
            var fos = new FileOutputStream(fileNamePrefix + "Signature.pdf");
            fos.write(baos.toByteArray());
            fos.close();

            var resultIS = new ByteArrayInputStream(baos.toByteArray());
            var resultReader = new PdfReader(resultIS);

            var fields = resultReader.getAcroFields();

            List<String> signatures = fields.getSignedFieldNames();
            for (var signature : signatures) {
                printSignatureDetails(fields, signature);
            }
        } catch (DocumentException | IOException e) {
            d.get().ce("addUnverifiedSignature", e.getMessage());
        }
    }

    private static void extractVerifiedCryptoSignature() {

        d.get().cr("extractVerifiedCryptoSignature", "Signature extraction");

        PdfPKCS7.loadCacertsKeyStore();

        try {
            var is = TS_FilePdfOpenPdfUtilsSign.class.getResourceAsStream("/CryptoSignedSha256.pdf");
            var reader = new PdfReader(is);
            var fields = reader.getAcroFields();

            List<String> signatures = fields.getSignedFieldNames();
            for (var signature : signatures) {
                printSignatureDetails(fields, signature);

                var pk = fields.verifySignature(signature);

                var certificate = pk.getSigningCertificate();
                var subjectFields = PdfPKCS7.getSubjectFields(certificate);
                d.get().cr("extractVerifiedCryptoSignature", "Certificate subject fields: " + subjectFields);
                d.get().cr("extractVerifiedCryptoSignature", "Certificate verified: " + pk.verify());

                var sdf = new SimpleDateFormat("yyyy-MM-dd");
                d.get().cr("extractVerifiedCryptoSignature", "Date signed: " + sdf.format(pk.getSignDate().getTime()));
                d.get().cr("extractVerifiedCryptoSignature", "Timestamp verified: " + pk.verifyTimestampImprint());
            }
        } catch (SignatureException | IOException | NoSuchAlgorithmException e) {
            d.get().ce(e.getMessage());
        }
    }

    private static void printSignatureDetails(AcroFields fields, String signature) {
        d.get().cr("printSignatureDetails", "Signature: " + signature);
        d.get().cr("printSignatureDetails", "Signature covers whole document: " + fields.signatureCoversWholeDocument(signature));
        d.get().cr("printSignatureDetails", "Revision: " + fields.getRevision(signature));
    }
}
