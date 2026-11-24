package com.smartcollege.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.smartcollege.entity.TimetableEntry;
public interface TimetableEntryRepository extends JpaRepository<TimetableEntry,Long> {}
