package com.smartcollege.controller;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.smartcollege.entity.Notice;
import com.smartcollege.repository.NoticeRepository;
@RestController @RequestMapping("/api/notices")
public class NoticeController {
 private final NoticeRepository repo; public NoticeController(NoticeRepository repo){this.repo=repo;}
 @GetMapping public List<Notice> all(){return repo.findAll();}
 @PostMapping public Notice create(@RequestBody Notice n,Authentication a){n.setId(null);n.setCreatedBy(a.getName());return repo.save(n);}
 @DeleteMapping("/{id}") public void delete(@PathVariable Long id){repo.deleteById(id);}
}
