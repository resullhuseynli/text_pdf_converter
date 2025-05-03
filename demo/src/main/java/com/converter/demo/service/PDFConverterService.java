package com.converter.demo.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static java.awt.SystemColor.text;

@Service
public class PDFConverterService {

    public ResponseEntity<byte[]> convertTxtToPdf(MultipartFile file) {

        try (PDDocument document = new PDDocument();
             BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {

            InputStream fontStream = getClass().getResourceAsStream("/fonts/DejaVuSans.ttf");
            PDType0Font font = PDType0Font.load(document, fontStream);
            float margin = 50;
            float fontSize = 11;
            float leading = 24f;
            float yStart = 700;
            float yPosition = yStart;

            PDPage page = new PDPage();
            document.addPage(page);
            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.setFont(font, fontSize);
            contentStream.setLeading(leading);
            contentStream.beginText();
            contentStream.newLineAtOffset(margin, yPosition);

            String line;
            while ((line = reader.readLine()) != null) {
                List<String> wrappedLines = wrapText(line, font, fontSize, 500);
                for (String wrappedLine : wrappedLines) {
                    if (yPosition <= margin + leading) {
                        contentStream.endText();
                        contentStream.close();

                        page = new PDPage();
                        document.addPage(page);
                        contentStream = new PDPageContentStream(document, page);
                        contentStream.setFont(font, fontSize);
                        contentStream.setLeading(leading);
                        yPosition = yStart;
                        contentStream.beginText();
                        contentStream.newLineAtOffset(margin, yPosition);
                    }

                    contentStream.showText(wrappedLine);
                    contentStream.newLine();
                    yPosition -= leading;
                }
            }

            contentStream.endText();
            contentStream.close();


            ByteArrayOutputStream out = new ByteArrayOutputStream();
            document.save(out);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", file.getOriginalFilename() + ".pdf");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(out.toByteArray());

        } catch (Exception e) {
            System.err.println(e.getMessage());
            return ResponseEntity.status(500).body(null);
        }
    }

    private List<String> wrapText(String text, PDType0Font font, float fontSize, float maxWidth) throws IOException {
        List<String> lines = new ArrayList<>();
        String[] words = text.split(" ");
        StringBuilder currentLine = new StringBuilder();

        for (String word : words) {
            String lineWithWord = currentLine.isEmpty() ? word : currentLine + " " + word;
            float width = font.getStringWidth(lineWithWord) / 1000 * fontSize;

            if (width <= maxWidth) {
                currentLine.append(currentLine.isEmpty() ? word : " " + word);
            } else {
                lines.add(currentLine.toString());
                currentLine = new StringBuilder(word);
            }
        }
        lines.add(currentLine.toString()); // add the last line
        return lines;
    }
}