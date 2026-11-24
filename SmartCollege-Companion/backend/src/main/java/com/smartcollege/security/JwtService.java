package com.smartcollege.security;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
@Service
public class JwtService {
 private final SecretKey key; private final long expiration;
 public JwtService(@Value("${app.jwt.secret}") String secret,@Value("${app.jwt.expiration-ms}") long expiration){
  this.key=Keys.hmacShaKeyFor(secret.getBytes(java.nio.charset.StandardCharsets.UTF_8)); this.expiration=expiration;
 }
 public String generate(UserDetails user){Date now=new Date();return Jwts.builder().subject(user.getUsername()).issuedAt(now).expiration(new Date(now.getTime()+expiration)).signWith(key).compact();}
 public String extractUsername(String token){return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().getSubject();}
 public boolean isValid(String token,UserDetails user){try{return extractUsername(token).equals(user.getUsername()) && Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().getExpiration().after(new Date());}catch(Exception e){return false;}}
}
