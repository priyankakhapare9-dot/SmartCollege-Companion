package com.smartcollege.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.smartcollege.entity.StudyNote;
public interface StudyNoteRepository extends JpaRepository<StudyNote,Long> {}
