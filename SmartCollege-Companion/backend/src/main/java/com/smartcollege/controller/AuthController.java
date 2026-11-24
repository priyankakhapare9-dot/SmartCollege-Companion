package com.smartcollege.controller;
import java.util.Map;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.smartcollege.entity.*;
import com.smartcollege.repository.UserRepository;
import com.smartcollege.security.JwtService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
@RestController @RequestMapping("/api/auth")
public class AuthController {
 private final UserRepository users; private final PasswordEncoder encoder; private final AuthenticationManager auth; private final JwtService jwt;
 public AuthController(UserRepository users,PasswordEncoder encoder,AuthenticationManager auth,JwtService jwt){this.users=users;this.encoder=encoder;this.auth=auth;this.jwt=jwt;}
 public record RegisterRequest(@NotBlank String name,@Email @NotBlank String email,@Size(min=6) String password){}
 public record LoginRequest(@Email String email,@NotBlank String password){}
 @PostMapping("/register") public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest r){
  if(users.existsByEmail(r.email()))return ResponseEntity.badRequest().body(Map.of("message","Email already registered"));
  AppUser u=new AppUser();u.setName(r.name());u.setEmail(r.email());u.setPassword(encoder.encode(r.password()));u.setRole(Role.STUDENT);users.save(u);
  return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message","Registration successful"));
 }
 @PostMapping("/login") public Map<String,Object> login(@Valid @RequestBody LoginRequest r){
  auth.authenticate(new UsernamePasswordAuthenticationToken(r.email(),r.password()));
  AppUser u=users.findByEmail(r.email()).orElseThrow();
  var principal=User.withUsername(u.getEmail()).password(u.getPassword()).roles(u.getRole().name()).build();
  return Map.of("token",jwt.generate(principal),"name",u.getName(),"email",u.getEmail(),"role",u.getRole().name());
 }
}
