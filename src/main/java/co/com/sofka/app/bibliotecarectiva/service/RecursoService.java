package co.com.sofka.app.bibliotecarectiva.service;

import co.com.sofka.app.bibliotecarectiva.collection.Recurso;
import co.com.sofka.app.bibliotecarectiva.model.RecursoDTO;
import co.com.sofka.app.bibliotecarectiva.repository.RecursoRepository;
import co.com.sofka.app.bibliotecarectiva.utils.AreaTematica;
import co.com.sofka.app.bibliotecarectiva.utils.TipoRecurso;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class RecursoService {

    @Autowired
    RecursoRepository recursoRepository;

    ModelMapper modelMapper = new ModelMapper();

    public Flux<RecursoDTO> findAll(){
        return recursoRepository.findAll()
                .flatMap(r -> Mono.just(modelMapper.map(r, RecursoDTO.class)));
    }

    public Mono<RecursoDTO> findById(String id){
        return recursoRepository.findById(id)
                .flatMap(r -> Mono.just(modelMapper.map(r, RecursoDTO.class)))
                .switchIfEmpty(Mono.empty());
    }

    public Mono<RecursoDTO> save(RecursoDTO recursoDTO){
        System.out.println(recursoDTO.toString());
        return recursoRepository.save(modelMapper.map(recursoDTO, Recurso.class))
                .flatMap(r -> Mono.just(modelMapper.map(r, RecursoDTO.class)));
    }

    public Mono<RecursoDTO> update(String id, RecursoDTO recursoDTO){
        return findById(id).
                flatMap(rDto -> {
                    recursoDTO.setId(rDto.getId());
                    return save(recursoDTO);
                })
                .switchIfEmpty(Mono.empty());
    }

    public Mono<RecursoDTO> delete(String id){
        return findById(id)
                .flatMap(r -> recursoRepository.deleteById(r.getId()).thenReturn(r));
    }

    public Mono<String> checkAvailability(String id){
        return findById(id)
                .switchIfEmpty(Mono.empty())
                .flatMap(r -> r.getPrestado() ?
                        Mono.just(String.format("El recurso %s NO esta disponible y fue prestado en %s", r.getNombre(), r.getFechaPrestamo())) :
                        Mono.just(String.format("El recurso %s esta disponible", r.getNombre()))
                );
    }

    public Mono<String> lend(String id){
        return findById(id)
                .flatMap(rDto -> {
                    if(!rDto.getPrestado()){
                        rDto.setPrestado(true);
                        rDto.setFechaPrestamo(LocalDate.now().format(DateTimeFormatter.ISO_DATE));
                        return save(rDto).then(Mono.just("Recurso prestado exitosamente"));
                    }
                    return Mono.just("Recurso no disponible, ya prestado.");
                });
    }

    public Flux<RecursoDTO> recommend(AreaTematica areaTematica, TipoRecurso tipoRecurso){
        return findAll()
                .filter(r -> r.getTipoRecurso().equals(tipoRecurso) || r.getAreaTematica().equals(areaTematica))
                .switchIfEmpty(Flux.empty());
    }

    public Mono<RecursoDTO> returnResource(String id){
        return findById(id)
                .flatMap(r -> {
                    r.setPrestado(false);
                    return save(r);
                });
    }
}
