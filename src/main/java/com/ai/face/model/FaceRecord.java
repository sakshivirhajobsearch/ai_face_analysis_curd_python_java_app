package com.ai.face.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class FaceRecord {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	private String imagePath;
	private int age;
	private String gender;
	private String emotion;
	private String dominantEmotion;

	@Column(columnDefinition = "TEXT")
	private String emotionScoresJson;

	// Getters and Setters omitted for brevity

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmotion() {
		return emotion;
	}

	public void setEmotion(String emotion) {
		this.emotion = emotion;
	}

	public String getDominantEmotion() {
		return dominantEmotion;
	}

	public void setDominantEmotion(String dominantEmotion) {
		this.dominantEmotion = dominantEmotion;
	}

	public String getEmotionScoresJson() {
		return emotionScoresJson;
	}

	public void setEmotionScoresJson(String emotionScoresJson) {
		this.emotionScoresJson = emotionScoresJson;
	}
}