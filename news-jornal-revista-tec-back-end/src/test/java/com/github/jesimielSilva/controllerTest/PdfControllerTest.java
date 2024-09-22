package com.github.jesimielSilva.controllerTest;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import com.github.jesimielSilva.controller.PdfController;
import com.github.jesimielSilva.service.PdfService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.MvcResult;

import java.util.HashMap;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class PdfControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PdfService pdfService;

    @InjectMocks
    private PdfController pdfController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(pdfController).build();
    }

    @Test
    public void testGeneratePdf() throws Exception {
        // Conteúdo HTML para ser transformado em PDF
        String htmlContent = "<html><body><h1>Jornal de Notícias</h1></body></html>";
        byte[] pdfBytes = new byte[] {1, 2, 3, 4}; // Simulação dos bytes do PDF gerado

        // Simular o comportamento do PdfService
        when(pdfService.generatePdfFromHtml(htmlContent)).thenReturn(pdfBytes);

        // Mapa de requisição JSON
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("html", htmlContent);

        // Converter o conteúdo HTML para JSON
        String jsonRequest = "{ \"html\": \"" + htmlContent + "\" }";

        // Executar o POST para o endpoint /api/generate-pdf
        MvcResult result = mockMvc.perform(post("/api/generate-pdf")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())  // Verifica se o status da resposta é 200 (OK)
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE))  // Verifica o Content-Type
                .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "form-data; name=\"filename\"; filename=\"jornal-noticias.pdf\"")) // Verifica o Content-Disposition
                .andExpect(content().bytes(pdfBytes))  // Verifica se o conteúdo do PDF corresponde aos bytes gerados
                .andReturn();

        // Verifica se o método do serviço foi chamado corretamente
        verify(pdfService, times(1)).generatePdfFromHtml(htmlContent);
    }
}
