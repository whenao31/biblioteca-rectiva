package co.com.sofka.app.bibliotecarectiva.repository;

import co.com.sofka.app.bibliotecarectiva.collection.Recurso;
import co.com.sofka.app.bibliotecarectiva.model.RecursoDTO;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface RecursoRepository extends ReactiveMongoRepository<Recurso, String> {
    Flux<RecursoDTO> findByNombre(String nombre);
}
