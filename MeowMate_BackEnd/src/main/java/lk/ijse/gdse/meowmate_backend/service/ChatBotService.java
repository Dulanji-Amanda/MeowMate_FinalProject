//package lk.ijse.gdse.meowmate_backend.service;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.*;
//        import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Service
//public class ChatBotService {
//
//    private static final Logger logger = LoggerFactory.getLogger(ChatBotService.class);
//    private final RestTemplate restTemplate;
//    private final String ollamaUrl;
//    private final String model;
//    private final ObjectMapper objectMapper;
//
//    public ChatBotService(@Value("${ollama.api.url:http://localhost:11434/api/generate}") String ollamaUrl,
//                          @Value("${ollama.model:llama3}") String model) {
//        this.restTemplate = new RestTemplate();
//        this.ollamaUrl = ollamaUrl;
//        this.model = model;
//        this.objectMapper = new ObjectMapper();
//        logger.info("ChatBotService initialized with Ollama URL: {} and model: {}", ollamaUrl, model);
//    }
//
//    public String ask(String prompt) {
//        logger.info("Received chat request with prompt: {}", prompt);
//
//        try {
//            // Create system context for BarkBuddy
//            String systemPrompt = "You are a helpful assistant for BarkBuddy, a dog adoption and care platform. " +
//                    "Provide helpful information about dog care, adoption, and related topics. " +
//                    "Keep responses concise and friendly. User question: " + prompt;
//
//            // Prepare request body for Ollama API
//            Map<String, Object> requestBody = new HashMap<>();
//            requestBody.put("model", model);
//            requestBody.put("prompt", systemPrompt);
//            requestBody.put("stream", false); // Get complete response at once
//
//            // Set headers
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_JSON);
//
//            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
//
//            logger.info("Sending request to Ollama at: {} with model: {}", ollamaUrl, model);
//
//            // Call Ollama API
//            ResponseEntity<String> response = restTemplate.postForEntity(ollamaUrl, entity, String.class);
//
//            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
//                // Parse response
//                JsonNode responseJson = objectMapper.readTree(response.getBody());
//                String aiResponse = responseJson.get("response").asText();
//
//                logger.info("Successfully received response from Ollama");
//                return aiResponse;
//            } else {
//                logger.error("Ollama API returned status: {}", response.getStatusCode());
//                return "I'm sorry, I'm having trouble connecting to the AI service. Please try again later.";
//            }
//
//        } catch (Exception e) {
//            logger.error("Error occurred while processing chat request: {}", e.getMessage(), e);
//            return "I'm sorry, I encountered an error while processing your request. Please make sure Ollama is running and try again.";
//        }
//    }
//}

























package lk.ijse.gdse.meowmate_backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

@Service
public class ChatBotService {

    private static final Logger logger = LoggerFactory.getLogger(ChatBotService.class);
    private final RestTemplate restTemplate;
    private final String ollamaUrl;
    private final String model;
    private final ObjectMapper objectMapper;

    public ChatBotService(@Value("${ollama.api.url}") String ollamaUrl,
                          @Value("${ollama.model}") String model) {
        this.restTemplate = new RestTemplate();
        this.ollamaUrl = ollamaUrl;
        this.model = model;
        this.objectMapper = new ObjectMapper();
        logger.info("ChatBotService initialized with Ollama URL: {} and model: {}", ollamaUrl, model);
    }

    public String ask(String prompt) {
        logger.info("Received prompt: {}", prompt);

        try {
            String systemPrompt = "You are BarkBuddy AI, a friendly assistant for dog adoption and care. Answer concisely and helpfully. User: " + prompt;

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", model);
            requestBody.put("prompt", systemPrompt);
            requestBody.put("stream", false);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(ollamaUrl, entity, String.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                JsonNode json = objectMapper.readTree(response.getBody());
                // Ollama returns "response" field
                return json.has("response") ? json.get("response").asText() : json.toString();
            } else {
                return "Sorry, AI service is unavailable.";
            }

        } catch (Exception e) {
            logger.error("Error in chat service: {}", e.getMessage(), e);
            return "Sorry, couldn't process your request. Make sure Ollama is running.";
        }
    }
}
