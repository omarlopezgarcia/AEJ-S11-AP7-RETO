package pe.edu.vallegrande.app.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import pe.edu.vallegrande.app.model.GeminiTopicResponse;
import reactor.core.publisher.Flux;

public interface GeminiTopicRepository extends ReactiveCrudRepository<GeminiTopicResponse, String> {
    @Query("SELECT * FROM gemini_topics ORDER BY id DESC")
    Flux<GeminiTopicResponse> getAll();
}
