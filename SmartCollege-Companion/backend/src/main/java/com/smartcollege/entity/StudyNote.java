package com.smartcollege.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity public class StudyNote {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 private String title; private String subject; private String originalFileName; private String storedFileName; private LocalDateTime uploadedAt=LocalDateTime.now(); private String uploadedBy;
 public Long getId(){return id;} public void setId(Long v){id=v;} public String getTitle(){return title;} public void setTitle(String v){title=v;} public String getSubject(){return subject;} public void setSubject(String v){subject=v;} public String getOriginalFileName(){return originalFileName;} public void setOriginalFileName(String v){originalFileName=v;} public String getStoredFileName(){return storedFileName;} public void setStoredFileName(String v){storedFileName=v;} public LocalDateTime getUploadedAt(){return uploadedAt;} public String getUploadedBy(){return uploadedBy;} public void setUploadedBy(String v){uploadedBy=v;}
}
