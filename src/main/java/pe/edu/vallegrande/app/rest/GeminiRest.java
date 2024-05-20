package pe.edu.vallegrande.app.rest;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pe.edu.vallegrande.app.model.GeminiImageAnalysis;
import pe.edu.vallegrande.app.model.GeminiTranslate;
import pe.edu.vallegrande.app.service.impl.GeminiAnalysisServiceImpl;
import pe.edu.vallegrande.app.service.impl.GeminiTranslateServiceImpl;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@CrossOrigin
@RequestMapping("/gemini")
public class GeminiRest {

    private final GeminiAnalysisServiceImpl geminiAnalysisService;
    private final GeminiTranslateServiceImpl geminiTranslateService;

    public GeminiRest(GeminiAnalysisServiceImpl geminiAnalysisService, GeminiTranslateServiceImpl geminiTranslateService) {
        this.geminiAnalysisService = geminiAnalysisService;
        this.geminiTranslateService = geminiTranslateService;
    }

    @PostMapping("/upload/image")
    public Mono<GeminiImageAnalysis> uploadFile(@RequestParam("file") MultipartFile file,
                                                @RequestParam("language") String language) {
        return geminiAnalysisService.processImage(file, language);
    }

    @GetMapping("/images")
    public Flux<GeminiImageAnalysis> getAllImages() {
        return geminiAnalysisService.getAll();
    }

    @GetMapping("/translations")
    public Flux<GeminiTranslate> getAllTranslations() {
        return geminiTranslateService.getAll();
    }

    @PostMapping("/translate")
    public Mono<GeminiTranslate> translateText(@RequestBody GeminiTranslate request) {
        return geminiTranslateService.translateText(request.getEntered_text(), request.getLanguages());
    }
}
