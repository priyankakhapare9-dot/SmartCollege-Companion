package com.smartcollege.controller;
import java.util.Map;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.smartcollege.repository.UserRepository;
@RestController @RequestMapping("/api/students")
public class ProfileController {
 private final UserRepository users; public ProfileController(UserRepository users){this.users=users;}
 @GetMapping("/me") public Map<String,Object> me(Authentication a){var u=users.findByEmail(a.getName()).orElseThrow();return Map.of("id",u.getId(),"name",u.getName(),"email",u.getEmail(),"role",u.getRole());}
}
