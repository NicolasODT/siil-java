package com.siil.app.controller;

import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    @Autowired
    private ResourceLoader resourceLoader;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file) {
        try {
            byte[] bytes = file.getBytes();
            Path imagesDir = Paths.get("src/main/resources/image");
            if (!Files.exists(imagesDir)) {
                Files.createDirectories(imagesDir);
            }
            Path imagePath = imagesDir.resolve(Objects.requireNonNull(file.getOriginalFilename()));
            Files.write(imagePath, bytes);

            return ResponseEntity.ok().body("{\"message\": \"Image uploaded successfully: " + file.getOriginalFilename() + "\"}");
        } catch (IOException e) {
            // Retournez une erreur au format JSON
            return ResponseEntity.badRequest().body("{\"error\": \"Failed to upload image: " + e.getMessage() + "\"}");
        }
    }
}
