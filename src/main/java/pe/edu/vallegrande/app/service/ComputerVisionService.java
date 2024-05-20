package pe.edu.vallegrande.app.service;

import pe.edu.vallegrande.app.model.ComputerVisionResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ComputerVisionService {
    Mono<ComputerVisionResponse> save(String imageUrl);

    Flux<ComputerVisionResponse> getAll();

}
