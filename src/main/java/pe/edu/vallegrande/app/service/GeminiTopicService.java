package pe.edu.vallegrande.app.service;

import pe.edu.vallegrande.app.model.GeminiTopicResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface GeminiTopicService {
    Mono<String> askQuestion(String topic, String question) throws Exception;
    Flux<GeminiTopicResponse> getAllResponses();
}
