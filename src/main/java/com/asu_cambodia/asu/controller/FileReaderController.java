package com.asu_cambodia.asu.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/private/files")
public class FileReaderController {

    // Use absolute path based on current working directory + folder
    private final Path uploadDir = Paths.get(System.getProperty("user.dir"), "upload", "Userprofile").toAbsolutePath().normalize();

    @GetMapping("/{fileName:.+}")
    public ResponseEntity<byte[]> getFileImageReader(@PathVariable String fileName) throws IOException {
        System.out.println("Requested file: " + fileName);
        if (fileName.contains("..")) {
            System.out.println("Invalid file path requested: " + fileName);
            return ResponseEntity.badRequest().build();
        }
        Path filePath = uploadDir.resolve(fileName).normalize();
        System.out.println("Resolved file path: " + filePath);

        if (!Files.exists(filePath) || !Files.isRegularFile(filePath)) {
            System.out.println("File not found: " + filePath);
            return ResponseEntity.notFound().build();
        }

        String mimeType = Files.probeContentType(filePath);
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }
        byte[] fileContent = Files.readAllBytes(filePath);
        System.out.println("Serving file: " + fileName + " with mimeType: " + mimeType);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(mimeType))
                .body(fileContent);
    }
}
