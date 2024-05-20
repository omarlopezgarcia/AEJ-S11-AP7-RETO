package pe.edu.vallegrande.app.service;

import pe.edu.vallegrande.app.model.GeminiTranslate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface GeminiTranslateService {
    Mono<GeminiTranslate> translateText(String text, String languages);
    Flux<GeminiTranslate> getAll();
}