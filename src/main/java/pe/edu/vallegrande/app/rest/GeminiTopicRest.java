package pe.edu.vallegrande.app.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.app.model.GeminiTopicResponse;
import pe.edu.vallegrande.app.service.GeminiTopicService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/gemini-topic")
public class GeminiTopicRest {

    @Autowired
    private GeminiTopicService geminiService;

    @PostMapping("/ask")
    public Mono<String> askQuestion(@RequestBody GeminiTopicResponse request) throws Exception {
        return geminiService.askQuestion(request.getTopic(), request.getQuestion());
    }

    @GetMapping("/all")
    public Flux<GeminiTopicResponse> getAllResponses() {
        return geminiService.getAllResponses();
    }
}
