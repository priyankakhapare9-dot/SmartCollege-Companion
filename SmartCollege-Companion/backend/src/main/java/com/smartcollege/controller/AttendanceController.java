package com.smartcollege.controller;
import java.time.LocalDate;
import java.util.List;
import org.springframework.web.bind.annotation.*;
import com.smartcollege.entity.Attendance;
import com.smartcollege.repository.AttendanceRepository;
@RestController @RequestMapping("/api/attendance")
public class AttendanceController {
 private final AttendanceRepository repo; public AttendanceController(AttendanceRepository repo){this.repo=repo;}
 public record AttendanceRequest(String studentEmail,String subject,LocalDate date,boolean present){}
 @GetMapping public List<Attendance> all(){return repo.findAll();}
 @PostMapping public Attendance create(@RequestBody AttendanceRequest r){Attendance a=new Attendance();a.setStudentEmail(r.studentEmail());a.setSubject(r.subject());a.setDate(r.date()==null?LocalDate.now():r.date());a.setPresent(r.present());return repo.save(a);}
 @DeleteMapping("/{id}") public void delete(@PathVariable Long id){repo.deleteById(id);}
}
