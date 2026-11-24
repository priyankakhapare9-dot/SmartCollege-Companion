package com.smartcollege.controller;
import java.nio.file.*;
import java.util.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.*;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.smartcollege.entity.StudyNote;
import com.smartcollege.repository.StudyNoteRepository;
@RestController @RequestMapping("/api/notes")
public class NotesController {
 private final StudyNoteRepository repo; private final Path uploadPath;
 public NotesController(StudyNoteRepository repo,@Value("${app.upload.dir}") String dir){this.repo=repo;this.uploadPath=Paths.get(dir).toAbsolutePath().normalize();}
 @GetMapping public List<StudyNote> all(){return repo.findAll();}
 @PostMapping(consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
 public StudyNote upload(@RequestParam String title,@RequestParam String subject,@RequestParam MultipartFile file,Authentication auth)throws Exception{
  if(file.isEmpty())throw new IllegalArgumentException("Please select a file");
  Files.createDirectories(uploadPath);String stored=UUID.randomUUID().toString();Path target=uploadPath.resolve(stored).normalize();
  if(!target.startsWith(uploadPath))throw new SecurityException("Invalid file path");Files.copy(file.getInputStream(),target,StandardCopyOption.REPLACE_EXISTING);
  StudyNote n=new StudyNote();n.setTitle(title);n.setSubject(subject);n.setOriginalFileName(file.getOriginalFilename());n.setStoredFileName(stored);n.setUploadedBy(auth.getName());return repo.save(n);
 }
 @GetMapping("/{id}/download") public ResponseEntity<Resource> download(@PathVariable Long id)throws Exception{
  StudyNote n=repo.findById(id).orElseThrow();Path p=uploadPath.resolve(n.getStoredFileName()).normalize();Resource r=new UrlResource(p.toUri());
  if(!r.exists())return ResponseEntity.notFound().build();
  return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\""+n.getOriginalFileName().replace("\"","")+"\").body(r);
 }
 @DeleteMapping("/{id}") public void delete(@PathVariable Long id)throws Exception{StudyNote n=repo.findById(id).orElseThrow();Files.deleteIfExists(uploadPath.resolve(n.getStoredFileName()));repo.delete(n);}
}
