package com.ai.face.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.annotation.PostConstruct;

@RestController
@RequestMapping("/api/images")
public class ImageController {

	// ✅ Now explicitly pointing to python-backend/uploads
	private static final Path UPLOAD_DIR = Paths.get("python-backend/uploads").toAbsolutePath().normalize();

	@PostConstruct
	public void initUploadDir() {
		try {
			if (Files.exists(UPLOAD_DIR)) {
				if (!Files.isDirectory(UPLOAD_DIR)) {
					System.err.println(
							"⚠ Upload path exists as a file. Please delete or rename it manually: " + UPLOAD_DIR);
					return;
				}
			} else {
				Files.createDirectories(UPLOAD_DIR);
			}

			System.out.println("📂 Upload path initialized at: " + UPLOAD_DIR.toString());

		} catch (IOException e) {
			System.err.println("❌ Failed to create upload directory: " + e.getMessage());
		}
	}

	// POST /api/images
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile image) {
		try {
			String filename = Paths.get(image.getOriginalFilename()).getFileName().toString();
			Path targetPath = UPLOAD_DIR.resolve(filename).normalize();
			Files.copy(image.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
			return ResponseEntity.ok("✅ Uploaded: " + filename);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("❌ Upload failed: " + e.getMessage());
		}
	}

	// GET /api/images/{filename}
	@GetMapping("/{filename:.+}")
	public ResponseEntity<Resource> getImage(@PathVariable String filename) {
		try {
			Path filePath = UPLOAD_DIR.resolve(filename).normalize();
			if (!Files.exists(filePath)) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}

			Resource resource = new UrlResource(filePath.toUri());
			String contentType = Files.probeContentType(filePath);
			if (contentType == null) {
				contentType = "application/octet-stream";
			}

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.parseMediaType(contentType));
			return new ResponseEntity<>(resource, headers, HttpStatus.OK);

		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	// GET /api/images
	@GetMapping
	public ResponseEntity<String> defaultInfo() {
		return ResponseEntity.ok("Use POST /api/images to upload and GET /api/images/{filename} to retrieve image.");
	}
}
