package com.converter.demo.controller;

import com.converter.demo.service.PDFConverterService;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/converter/upload")
public class TextController {

    private final PDFConverterService pdfConverterService;

    public TextController(PDFConverterService pdfConverterService) {
        this.pdfConverterService = pdfConverterService;
    }


    @PostMapping("/text-to-pdf")
    public ResponseEntity<byte[]> convertTxtToPdf(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty() || !Objects.requireNonNull(file.getOriginalFilename()).endsWith(".txt")) {
            return ResponseEntity.badRequest().body(null);
        }

        return pdfConverterService.convertTxtToPdf(file);
    }

    @PostMapping("/pdf-to-txt")
    public ResponseEntity<Resource> convertPdfToTxt(@RequestParam("file") MultipartFile file) {

        if (file.isEmpty() || !Objects.requireNonNull(file.getOriginalFilename()).toLowerCase().endsWith(".pdf")) {
            return ResponseEntity.badRequest().body(null);
        }

        return pdfConverterService.convertPdfToTxt(file);
    }



}
