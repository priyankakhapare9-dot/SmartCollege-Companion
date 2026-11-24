package com.smartcollege.security;
import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
@Component
public class JwtAuthFilter extends OncePerRequestFilter {
 private final JwtService jwt; private final CustomUserDetailsService users;
 public JwtAuthFilter(JwtService jwt,CustomUserDetailsService users){this.jwt=jwt;this.users=users;}
 @Override protected void doFilterInternal(HttpServletRequest request,HttpServletResponse response,FilterChain chain)throws ServletException,IOException{
  String header=request.getHeader("Authorization");
  if(header!=null && header.startsWith("Bearer ")){
   try {String token=header.substring(7);String email=jwt.extractUsername(token);
    if(SecurityContextHolder.getContext().getAuthentication()==null){UserDetails user=users.loadUserByUsername(email);
     if(jwt.isValid(token,user)){var auth=new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));SecurityContextHolder.getContext().setAuthentication(auth);}
    }
   } catch(Exception ignored) { }
  }
  chain.doFilter(request,response);
 }
}
