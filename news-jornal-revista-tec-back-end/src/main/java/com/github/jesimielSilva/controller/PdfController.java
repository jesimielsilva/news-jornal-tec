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
        String htmlContent = request.get("html");

        byte[] pdfBytes = pdfService.generatePdfFromHtml(htmlContent);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("filename", "jornal-noticias.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdfBytes);
    }

//    private final PdfService pdfService;
//
//    public PdfController(PdfService pdfService) {
//        this.pdfService = pdfService;
//    }
//
//    @GetMapping("/api/generate-pdf")
//    public ResponseEntity<byte[]> generatePdf() {
//        String title = "Jornal de Notícias";
//        String content = "Conteúdo dinâmico do jornal...";
//
//        byte[] pdfBytes = pdfService.generatePdf(title, content);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_PDF);
//        headers.setContentDispositionFormData("filename", "jornal-noticias.pdf");
//
//        return ResponseEntity.ok()
//                .headers(headers)
//                .body(pdfBytes);
//    }
}
