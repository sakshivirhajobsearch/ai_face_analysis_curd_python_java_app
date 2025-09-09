package com.ai.face.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ai.face.model.FaceRecord;
import com.ai.face.service.FaceService;

@RestController
@RequestMapping("/api/faces")
public class FaceRecordController {

	@Autowired
	private FaceService faceService;

	// GET all faces
	@GetMapping
	public ResponseEntity<List<FaceRecord>> getAllFaces() {
		return ResponseEntity.ok(faceService.getAllFaces());
	}

	// GET face by ID
	@GetMapping("/{id}")
	public ResponseEntity<?> getFaceById(@PathVariable Long id) {
		Optional<FaceRecord> face = faceService.getFaceById(id);
		if (face.isPresent()) {
			return ResponseEntity.ok(face.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Face not found");
		}
	}

	// POST face metadata (JSON)
	@PostMapping(consumes = "application/json")
	public ResponseEntity<FaceRecord> createFace(@RequestBody FaceRecord face) {
		FaceRecord saved = faceService.createFace(face);
		return ResponseEntity.status(HttpStatus.CREATED).body(saved);
	}

	// POST face with image upload (multipart/form-data)
	@PostMapping(path = "/upload", consumes = "multipart/form-data")
	public ResponseEntity<?> uploadFace(@RequestParam("file") MultipartFile file, @RequestParam("name") String name,
			@RequestParam(value = "age", required = false) Integer age,
			@RequestParam(value = "gender", required = false) String gender,
			@RequestParam(value = "emotion", required = false) String emotion,
			@RequestParam(value = "dominantEmotion", required = false) String dominantEmotion,
			@RequestParam(value = "emotionScoresJson", required = false) String emotionScoresJson) {
		try {
			FaceRecord saved = faceService.uploadFace(file, name, age, gender, emotion, dominantEmotion,
					emotionScoresJson);
			return ResponseEntity.status(HttpStatus.CREATED).body(saved);
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("File upload failed: " + e.getMessage());
		}
	}

	// PUT update face metadata
	@PutMapping("/{id}")
	public ResponseEntity<?> updateFace(@PathVariable Long id, @RequestBody FaceRecord updatedFace) {
		Optional<FaceRecord> updated = faceService.updateFace(id, updatedFace);
		if (updated.isPresent()) {
			return ResponseEntity.ok(updated.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Face not found");
		}
	}

	// DELETE face
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteFace(@PathVariable Long id) {
		boolean deleted = faceService.deleteFace(id);
		if (deleted) {
			return ResponseEntity.ok("Face deleted successfully");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Face not found");
		}
	}
}