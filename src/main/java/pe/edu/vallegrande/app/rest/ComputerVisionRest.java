package pe.edu.vallegrande.app.rest;

import pe.edu.vallegrande.app.dto.ComputerVisionImageAnalysisDTO;
import pe.edu.vallegrande.app.model.ComputerVisionResponse;
import pe.edu.vallegrande.app.service.ComputerVisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@CrossOrigin
@RequestMapping("/computer-vision")
public class ComputerVisionRest {

    @Autowired
    private ComputerVisionService visionService;

    @PostMapping("/analyze/image")
    public Mono<ComputerVisionResponse> analyzeImage(@RequestBody ComputerVisionImageAnalysisDTO request) {

        return visionService.save(request.getImageUrl());
    }

    @GetMapping
    public Flux<ComputerVisionResponse> getAll() {
        return visionService.getAll();
    }
}
