package co.com.sofka.app.bibliotecarectiva.controller;

import co.com.sofka.app.bibliotecarectiva.model.RecursoDTO;
import co.com.sofka.app.bibliotecarectiva.service.RecursoService;
import co.com.sofka.app.bibliotecarectiva.utils.AreaTematica;
import co.com.sofka.app.bibliotecarectiva.utils.TipoRecurso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/biblioteca/recurso")
public class RecursoController {

    @Autowired
    RecursoService recursoService;

    @GetMapping
    public ResponseEntity<Flux<RecursoDTO>> findAll(){
        return new ResponseEntity<Flux<RecursoDTO>>(recursoService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mono<RecursoDTO>> findById(@PathVariable("id") String id){
        return new ResponseEntity<Mono<RecursoDTO>>(recursoService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Mono<RecursoDTO>> save(@RequestBody RecursoDTO recursoDTO){
        return new ResponseEntity<Mono<RecursoDTO>>(recursoService.save(recursoDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mono<RecursoDTO>> update(@PathVariable("id")String id, @RequestBody RecursoDTO recursoDTO){
        return new ResponseEntity<Mono<RecursoDTO>>(recursoService.update(id, recursoDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Mono<RecursoDTO>> delete(@PathVariable("id")String id){
        return new ResponseEntity<Mono<RecursoDTO>>(recursoService.delete(id), HttpStatus.OK);
    }

    @GetMapping("/disponibilidad/{id}")
    public ResponseEntity<Mono<String>> checkAvailability(@PathVariable("id") String id){
        return new ResponseEntity<Mono<String>>(recursoService.checkAvailability(id), HttpStatus.OK);
    }

    @PutMapping("/prestar/{id}")
    public ResponseEntity<Mono<String>> prestar(@PathVariable("id")String id){
        return new ResponseEntity<Mono<String>>(recursoService.lend(id), HttpStatus.OK);
    }

    @GetMapping("/recomendar/{tematica}/{tipo}")
    public ResponseEntity<Flux<RecursoDTO>> recomendar(@PathVariable("tematica")AreaTematica areaTematica,
                                                       @PathVariable("tipo")TipoRecurso tipoRecurso){
        return new ResponseEntity<Flux<RecursoDTO>>(recursoService.recommend(areaTematica, tipoRecurso), HttpStatus.OK);
    }

    @PutMapping("/devolver/{id}")
    public ResponseEntity<Mono<RecursoDTO>> retornarRecurso(@PathVariable("id")String id){
        return new ResponseEntity<Mono<RecursoDTO>>(recursoService.returnResource(id), HttpStatus.OK);
    }
}
