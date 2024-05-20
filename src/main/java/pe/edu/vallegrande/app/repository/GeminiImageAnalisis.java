package pe.edu.vallegrande.app.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import pe.edu.vallegrande.app.model.GeminiImageAnalysis;
import reactor.core.publisher.Flux;

@Repository
public interface GeminiImageAnalisis extends ReactiveCrudRepository<GeminiImageAnalysis, Long> {
    @Query("SELECT * FROM gemini_analisis_image ORDER BY id DESC")
    Flux<GeminiImageAnalysis> getAll();
}
