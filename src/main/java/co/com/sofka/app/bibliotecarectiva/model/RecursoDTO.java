package co.com.sofka.app.bibliotecarectiva.model;

import co.com.sofka.app.bibliotecarectiva.utils.AreaTematica;
import co.com.sofka.app.bibliotecarectiva.utils.TipoRecurso;
import lombok.Data;

import java.util.UUID;

@Data
public class RecursoDTO {

    private String id = UUID.randomUUID().toString().substring(0, 10);
    private String nombre;
    private TipoRecurso tipoRecurso;
    private AreaTematica areaTematica;
    private Boolean prestado = false;
    private String fechaPrestamo;

    public RecursoDTO() {
    }

    public RecursoDTO(String id, String nombre, TipoRecurso tipoRecurso, AreaTematica areaTematica, Boolean prestado, String fechaPrestamo) {
        this.id = id;
        this.nombre = nombre;
        this.tipoRecurso = tipoRecurso;
        this.areaTematica = areaTematica;
        this.prestado = prestado;
        this.fechaPrestamo = fechaPrestamo;
    }

    public RecursoDTO(String nombre, TipoRecurso tipoRecurso, AreaTematica areaTematica, Boolean prestado, String fechaPrestamo) {
        this.nombre = nombre;
        this.tipoRecurso = tipoRecurso;
        this.areaTematica = areaTematica;
        this.prestado = prestado;
        this.fechaPrestamo = fechaPrestamo;
    }
}
