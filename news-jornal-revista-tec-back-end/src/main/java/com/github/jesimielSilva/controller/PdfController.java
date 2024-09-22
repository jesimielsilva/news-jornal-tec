package com.github.jesimielSilva.controller;

import com.github.jesimielSilva.service.PdfService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class PdfController {

    private final PdfService pdfService;

    public PdfController(PdfService pdfService) {
        this.pdfService = pdfService;
    }

    @PostMapping("/api/generate-pdf")
    public ResponseEntity<byte[]> generatePdf(@RequestBody Map<String, String> request) {
        // Recupera o conteúdo HTML enviado no corpo da requisição como um mapa de pares chave-valor
        String htmlContent = request.get("html");

        // Gera o PDF a partir do HTML usando o serviço PdfService
        byte[] pdfBytes = pdfService.generatePdfFromHtml(htmlContent);

        // Criação de cabeçalhos HTTP para a resposta
        HttpHeaders headers = new HttpHeaders();
        // Define o tipo de mídia da resposta como PDF
        headers.setContentType(MediaType.APPLICATION_PDF);
        // Define o cabeçalho de disposição de conteúdo, sugerindo um nome para o arquivo de download (jornal-noticias.pdf)
        headers.setContentDispositionFormData("filename", "jornal-noticias.pdf");

        // Retorna uma resposta com o código HTTP 200 (OK), os cabeçalhos e o corpo da resposta contendo o PDF em bytes
        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfBytes);
    }
}
