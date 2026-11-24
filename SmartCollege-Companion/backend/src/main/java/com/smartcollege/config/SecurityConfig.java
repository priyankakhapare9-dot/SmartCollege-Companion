package com.smartcollege.config;
import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.*;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.smartcollege.security.JwtAuthFilter;
@Configuration
public class SecurityConfig {
 @Bean PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder();}
 @Bean AuthenticationManager authenticationManager(AuthenticationConfiguration c)throws Exception{return c.getAuthenticationManager();}
 @Bean SecurityFilterChain filterChain(HttpSecurity http,JwtAuthFilter jwt)throws Exception{
  return http.csrf(c->c.disable()).cors(c->{}).sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
   .authorizeHttpRequests(a->a.requestMatchers("/api/auth/**").permitAll()
    .requestMatchers(HttpMethod.OPTIONS,"/**").permitAll()
    .requestMatchers(HttpMethod.POST,"/api/attendance","/api/timetable","/api/notices","/api/notes").hasAnyRole("FACULTY","ADMIN")
    .anyRequest().authenticated()).addFilterBefore(jwt,UsernamePasswordAuthenticationFilter.class).build();
 }
 @Bean org.springframework.web.cors.CorsConfigurationSource corsConfigurationSource(){
  var c=new org.springframework.web.cors.CorsConfiguration();c.setAllowedOrigins(java.util.List.of("http://localhost:5173"));c.setAllowedMethods(java.util.List.of("GET","POST","PUT","DELETE","OPTIONS"));c.setAllowedHeaders(java.util.List.of("*"));c.setAllowCredentials(true);
  var s=new org.springframework.web.cors.UrlBasedCorsConfigurationSource();s.registerCorsConfiguration("/**",c);return s;
 }
}
