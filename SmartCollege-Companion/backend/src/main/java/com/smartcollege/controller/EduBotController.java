package com.smartcollege.controller;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;
@RestController @RequestMapping("/api/edubot")
public class EduBotController {
 private final RestClient client; private final String model;
 public EduBotController(@Value("${app.ollama.url}") String url,@Value("${app.ollama.model}") String model){this.client=RestClient.builder().baseUrl(url).build();this.model=model;}
 @PostMapping("/chat") public Map<String,String> chat(@RequestBody Map<String,String> body){
  String question=body.getOrDefault("message","").trim();if(question.isEmpty())return Map.of("reply","Please enter a question.");
  try {
   Map<?,?> result=client.post().uri("/api/generate").contentType(MediaType.APPLICATION_JSON).body(Map.of("model",model,"prompt","You are EduBot, a helpful college academic assistant. Answer clearly.\nStudent: "+question,"stream",false)).retrieve().body(Map.class);
   return Map.of("reply",String.valueOf(result.get("response")));
  } catch(Exception e){return Map.of("reply","EduBot is unavailable. Start Ollama and pull the configured model ("+model+"), then try again.");}
 }
}
