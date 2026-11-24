package com.smartcollege.controller;
import java.util.List;
import org.springframework.web.bind.annotation.*;
import com.smartcollege.entity.TimetableEntry;
import com.smartcollege.repository.TimetableEntryRepository;
@RestController @RequestMapping("/api/timetable")
public class TimetableController {
 private final TimetableEntryRepository repo; public TimetableController(TimetableEntryRepository repo){this.repo=repo;}
 @GetMapping public List<TimetableEntry> all(){return repo.findAll();}
 @PostMapping public TimetableEntry create(@RequestBody TimetableEntry e){e.setId(null);return repo.save(e);}
 @DeleteMapping("/{id}") public void delete(@PathVariable Long id){repo.deleteById(id);}
}
