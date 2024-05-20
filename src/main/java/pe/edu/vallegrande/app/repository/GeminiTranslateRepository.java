package pe.edu.vallegrande.app.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import pe.edu.vallegrande.app.model.GeminiTranslate;
import reactor.core.publisher.Flux;

public interface GeminiTranslateRepository extends ReactiveCrudRepository<GeminiTranslate, String> {
    @Query("SELECT * FROM gemini_translate ORDER BY id DESC")
    Flux<GeminiTranslate> getAll();
}
