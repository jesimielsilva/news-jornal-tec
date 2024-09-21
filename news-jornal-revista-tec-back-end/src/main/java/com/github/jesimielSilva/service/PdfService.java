package com.github.jesimielSilva.service;


import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document.OutputSettings;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;

import com.itextpdf.tool.xml.XMLWorkerHelper;


@Service
public class PdfService {

    public byte[] generatePdfFromHtml(String htmlContent) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {
            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, byteArrayOutputStream); // Corrigido aqui
            document.open();

            // Limpar e preparar o HTML
            org.jsoup.nodes.Document jsoupDoc = Jsoup.parse(htmlContent);
            jsoupDoc.outputSettings(new OutputSettings().syntax(org.jsoup.nodes.Document.OutputSettings.Syntax.xml)); // Correção do pacote OutputSettings

            // Converter o HTML para PDF
            XMLWorkerHelper.getInstance().parseXHtml(writer, document, new StringReader(jsoupDoc.html()));

            document.close();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }

        return byteArrayOutputStream.toByteArray();
    }

}
