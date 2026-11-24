package com.smartcollege.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.smartcollege.entity.Notice;
public interface NoticeRepository extends JpaRepository<Notice,Long> {}
