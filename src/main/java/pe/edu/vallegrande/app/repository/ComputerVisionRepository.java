package pe.edu.vallegrande.app.repository;

import pe.edu.vallegrande.app.model.ComputerVisionResponse;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ComputerVisionRepository extends ReactiveCrudRepository<ComputerVisionResponse, Long> {
    @Query("SELECT * FROM ComputerVisionResponse ORDER BY id DESC")
    Flux<ComputerVisionResponse> getAll();

}
