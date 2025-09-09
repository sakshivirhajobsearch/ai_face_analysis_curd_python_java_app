package com.ai.face.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ai.face.model.FaceRecord;

@Repository
public interface FaceRecordRepository extends JpaRepository<FaceRecord, Long> {
}
