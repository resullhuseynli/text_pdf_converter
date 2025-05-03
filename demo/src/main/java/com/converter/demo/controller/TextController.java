package com.converter.demo.controller;

import com.converter.demo.service.PDFConverterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@RestController
public class TextController {

    private final PDFConverterService pdfConverterService;

    public TextController(PDFConverterService pdfConverterService) {
        this.pdfConverterService = pdfConverterService;
    }


    @PostMapping("/convert")
    public ResponseEntity<byte[]> convertTxtToPdf(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty() || !Objects.requireNonNull(file.getOriginalFilename()).endsWith(".txt")) {
            return ResponseEntity.badRequest().body(null);
        }

        return pdfConverterService.convertTxtToPdf(file);
    }

}
