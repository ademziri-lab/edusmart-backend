package com.edusmart.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ai")
@CrossOrigin(origins = "http://localhost:4200")
public class AiController {

    private final String OLLAMA_URL = "http://localhost:11434/api/chat";
    private final String MODEL = "qwen2.5:1.5b";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/chat")
    public ResponseEntity<Map<String, Object>> chat(@RequestBody Map<String, Object> body) {
        try {
            System.out.println("=== AI REQUEST RECEIVED ===");

            RestTemplate restTemplate = new RestTemplate();

            String systemPrompt = (String) body.get("system");
            List<Map<String, String>> messages = (List<Map<String, String>>) body.get("messages");

            // Check if a PDF file was uploaded
            String pdfBase64 = (String) body.get("pdfBase64");
            if (pdfBase64 != null && !pdfBase64.isEmpty()) {
                System.out.println("=== PDF DETECTED, EXTRACTING TEXT ===");
                String pdfText = extractTextFromPdf(pdfBase64);
                systemPrompt += "\n\nThe student has uploaded a PDF document. Here is its content:\n\n" + pdfText + "\n\nPlease use this document content to answer the student's questions.";
                System.out.println("=== PDF TEXT EXTRACTED, LENGTH: " + pdfText.length() + " ===");
            }

            List<Map<String, String>> ollamaMessages = new ArrayList<>();
            ollamaMessages.add(Map.of("role", "system", "content", systemPrompt));
            ollamaMessages.addAll(messages);

            Map<String, Object> ollamaBody = new HashMap<>();
            ollamaBody.put("model", MODEL);
            ollamaBody.put("messages", ollamaMessages);
            ollamaBody.put("stream", false);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(ollamaBody, headers);

            System.out.println("=== CALLING OLLAMA API ===");
            ResponseEntity<Map> response = restTemplate.postForEntity(OLLAMA_URL, request, Map.class);
            System.out.println("=== RESPONSE STATUS: " + response.getStatusCode() + " ===");

            Map responseBody = response.getBody();
            Map message = (Map) responseBody.get("message");
            String text = (String) message.get("content");

            System.out.println("=== AI RESPONSE: " + text + " ===");

            Map<String, Object> contentItem = new HashMap<>();
            contentItem.put("type", "text");
            contentItem.put("text", text);

            Map<String, Object> result = new HashMap<>();
            result.put("content", List.of(contentItem));

            return ResponseEntity.ok(result);

        } catch (Exception e) {
            System.out.println("=== ERROR: " + e.getMessage() + " ===");
            e.printStackTrace();
            Map<String, Object> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(500).body(error);
        }
    }

    private String extractTextFromPdf(String base64Pdf) {
        try {
            byte[] pdfBytes = Base64.getDecoder().decode(base64Pdf);
            PDDocument document = Loader.loadPDF(pdfBytes);
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);
            document.close();
            if (text.length() > 4000) {
                text = text.substring(0, 4000) + "...[document truncated]";
            }
            return text;
        } catch (Exception e) {
            System.out.println("=== PDF EXTRACTION ERROR: " + e.getMessage() + " ===");
            return "Could not extract text from PDF.";
        }
    }
}