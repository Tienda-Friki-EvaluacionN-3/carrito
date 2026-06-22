package com.tiendafriki.carrito.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiendafriki.carrito.dto.CarritoDTO;
import com.tiendafriki.carrito.model.Carrito;
import com.tiendafriki.carrito.service.CarritoServ;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CarritoController.class)
@AutoConfigureMockMvc(addFilters = false)
class CarritoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarritoServ service;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void listarCarritos() throws Exception {

        Carrito carrito = new Carrito(
                1,
                "11111111-1",
                LocalDateTime.now(),
                null
        );

        List<Carrito> respuesta = List.of(carrito);

        when(service.listar()).thenReturn(respuesta);

        mockMvc.perform(get("/carrito/listar"))
                .andExpect(status().isOk());
    }

     @Test
    void buscarCarritoPorId() throws Exception {

        Carrito carrito = new Carrito(
                1,
                "11111111-1",
                LocalDateTime.now(),
                null
        );

        when(service.buscarxID(1))
                .thenReturn(Optional.of(carrito));

        mockMvc.perform(get("/carrito/buscarxid/1"))
                .andExpect(status().isOk());
    }

    @Test
    void calcularTotalCarrito() throws Exception {

        when(service.calcularTotal(1))
                .thenReturn(11900.0);

        mockMvc.perform(get("/carrito/total/1"))
                .andExpect(status().isOk());
    }

    @Test
    void buscarCarritoPorRut() throws Exception {

        Carrito carrito = new Carrito(
                1,
                "11111111-1",
                LocalDateTime.now(),
                null
        );

        when(service.buscarxRut("11111111-1"))
                .thenReturn(Optional.of(carrito));

        mockMvc.perform(
                get("/carrito/buscarxrut/11111111-1"))
                .andExpect(status().isOk());
    }

    @Test
    void crearCarrito() throws Exception {

        CarritoDTO dto = new CarritoDTO(
                "11111111-1"
        );

        when(service.Guardar(dto))
                .thenReturn(
                        "[+] Carrito Creado Correctamente [>_<] ... "
                );

        mockMvc.perform(post("/carrito/crear")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                objectMapper.writeValueAsString(dto)
                        ))
                .andExpect(status().isCreated());
    }

    @Test
    void eliminarCarrito() throws Exception {

        when(service.Eliminar(1))
                .thenReturn(
                        "[+] Carrito Eliminado Correctamente [>_<] ... "
                );

        mockMvc.perform(
                delete("/carrito/eliminarxid/1"))
                .andExpect(status().isOk());
    }
}
