package co.com.sofka.app.bibliotecarectiva.controller;

import co.com.sofka.app.bibliotecarectiva.model.RecursoDTO;
import co.com.sofka.app.bibliotecarectiva.utils.AreaTematica;
import co.com.sofka.app.bibliotecarectiva.utils.TipoRecurso;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RecursoControllerTest {

    private final WebTestClient webClient;

    @Test
    @DisplayName("Test para crear un recurso")
    public void crearRecursoTest() throws Exception {
        RecursoDTO recursoRequest = new RecursoDTO(
                "La Odisea",
                TipoRecurso.LIBRO,
                AreaTematica.LITERATURA,
                false,
                null
                );

        webClient.post().uri("/api/biblioteca/recurso")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(Mono.just(recursoRequest), RecursoDTO.class)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.id").isNotEmpty()
                .jsonPath("$.nombre").isNotEmpty()
                .jsonPath("$.nombre").isEqualTo("La Odisea");
    }

    @Test
    @DisplayName("Verificar disponibilidad del recurso")
    public void verificarDisponibilidadTest() throws Exception {
        webClient.get()
                .uri("/api/biblioteca/recurso/{id}", "c0962dfd-9")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(response ->
                        Assertions.assertThat(response.getResponseBody()).isNotNull());
    }

}