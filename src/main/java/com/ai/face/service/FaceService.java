package com.ai.face.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ai.face.model.FaceRecord;
import com.ai.face.repository.FaceRecordRepository;

@Service
public class FaceService {

	@Autowired
	private FaceRecordRepository repository;

	private final String uploadDir = "uploads/";

	public List<FaceRecord> getAllFaces() {
		return repository.findAll();
	}

	public Optional<FaceRecord> getFaceById(Long id) {
		return repository.findById(id);
	}

	public FaceRecord createFace(FaceRecord face) {
		return repository.save(face);
	}

	public FaceRecord uploadFace(MultipartFile file, String name, Integer age, String gender, String emotion,
			String dominantEmotion, String emotionScoresJson) throws IOException {
		File dir = new File(uploadDir);
		if (!dir.exists())
			dir.mkdirs();

		String filePath = uploadDir + file.getOriginalFilename();
		file.transferTo(new File(filePath));

		FaceRecord face = new FaceRecord();
		face.setName(name);
		face.setAge(age != null ? age : 0);
		face.setGender(gender);
		face.setEmotion(emotion);
		face.setDominantEmotion(dominantEmotion);
		face.setEmotionScoresJson(emotionScoresJson);
		face.setImagePath(filePath);

		return repository.save(face);
	}

	public Optional<FaceRecord> updateFace(Long id, FaceRecord updatedFace) {
		Optional<FaceRecord> faceOpt = repository.findById(id);
		faceOpt.ifPresent(face -> {
			face.setName(updatedFace.getName());
			face.setAge(updatedFace.getAge());
			face.setGender(updatedFace.getGender());
			face.setEmotion(updatedFace.getEmotion());
			face.setDominantEmotion(updatedFace.getDominantEmotion());
			face.setEmotionScoresJson(updatedFace.getEmotionScoresJson());
			repository.save(face);
		});
		return faceOpt;
	}

	public boolean deleteFace(Long id) {
		if (repository.existsById(id)) {
			repository.deleteById(id);
			return true;
		}
		return false;
	}
}