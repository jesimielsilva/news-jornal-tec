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

    // Criação de um stream de saída em memória para armazenar o PDF gerado
    public byte[] generatePdfFromHtml(String htmlContent) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        try {
            // Cria uma instância de um documento PDF vazio
            Document document = new Document();
            // Associa o documento a um escritor de PDF, que será responsável por gerar o PDF no byteArrayOutputStream
            PdfWriter writer = PdfWriter.getInstance(document, byteArrayOutputStream);
            // Abre o documento para permitir a escrita de conteúdo
            document.open();

            // Usa jsoup para analisar o conteúdo HTML
            org.jsoup.nodes.Document jsoupDoc = Jsoup.parse(htmlContent);
            // Configura o jsoup para formatar a saída como XML, o que facilita a conversão para PDF
            jsoupDoc.outputSettings(new OutputSettings().syntax(org.jsoup.nodes.Document.OutputSettings.Syntax.xml));

            // Usa o XMLWorkerHelper para converter o HTML analisado pelo jsoup em PDF, enviando o conteúdo HTML para o documento aberto
            XMLWorkerHelper.getInstance().parseXHtml(writer, document, new StringReader(jsoupDoc.html()));

            document.close();
        } catch (DocumentException | IOException e) {
            // Caso ocorra uma exceção, ela será capturada e o erro será impresso
            e.printStackTrace();
        }
        // Retorna o conteúdo do PDF gerado como um array de bytes, que pode ser enviado ou salvo
        return byteArrayOutputStream.toByteArray();
    }

}
