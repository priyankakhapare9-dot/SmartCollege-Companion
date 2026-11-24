package com.smartcollege.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.smartcollege.entity.Attendance;
public interface AttendanceRepository extends JpaRepository<Attendance,Long> {}
