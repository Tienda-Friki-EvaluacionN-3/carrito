package com.tiendafriki.carrito.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiendafriki.carrito.dto.DetalleDTO;
import com.tiendafriki.carrito.model.Carrito;
import com.tiendafriki.carrito.model.Detalle;
import com.tiendafriki.carrito.service.DetalleServ;

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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DetalleController.class)
@AutoConfigureMockMvc(addFilters = false)
class DetalleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DetalleServ service;

    @Test
    void listarDetalles() throws Exception {

        Carrito carrito = new Carrito(
                1,
                "11111111-1",
                LocalDateTime.now(),
                null
        );

        Detalle detalle = new Detalle(
                1,
                carrito,
                10,
                2,
                5000,
                10000
        );

        when(service.listar())
                .thenReturn(List.of(detalle));

        mockMvc.perform(get("/detalleCarrito/listar"))
                .andExpect(status().isOk());
    }

    @Test
    void buscarDetallePorId() throws Exception {

        Carrito carrito = new Carrito(
                1,
                "11111111-1",
                LocalDateTime.now(),
                null
        );

        Detalle detalle = new Detalle(
                1,
                carrito,
                10,
                2,
                5000,
                10000
        );

        when(service.buscarxID(1))
                .thenReturn(Optional.of(detalle));

        mockMvc.perform(get("/detalleCarrito/buscarxid/1"))
                .andExpect(status().isOk());
    }

    @Test
    void buscarDetallesPorCarrito() throws Exception {

        Carrito carrito = new Carrito(
                1,
                "11111111-1",
                LocalDateTime.now(),
                null
        );

        Detalle detalle = new Detalle(
                1,
                carrito,
                10,
                2,
                5000,
                10000
        );

        when(service.buscarxCarrito(1))
                .thenReturn(List.of(detalle));

        mockMvc.perform(get("/detalleCarrito/carrito/1"))
                .andExpect(status().isOk());
    }

    @Test
    void buscarDetallesPorProducto() throws Exception {

        Carrito carrito = new Carrito(
                1,
                "11111111-1",
                LocalDateTime.now(),
                null
        );

        Detalle detalle = new Detalle(
                1,
                carrito,
                10,
                2,
                5000,
                10000
        );

        when(service.buscarxProducto(10))
                .thenReturn(List.of(detalle));

        mockMvc.perform(get("/detalleCarrito/producto/10"))
                .andExpect(status().isOk());
    }

    @Test
    void agregarDetalle() throws Exception {

        DetalleDTO dto = new DetalleDTO(
                1,
                10,
                2
        );

        when(service.Guardar(any(DetalleDTO.class)))
                .thenReturn("[+] Detalle Agregado Exitosamente");

        mockMvc.perform(post("/detalleCarrito/agregar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());
    }

    @Test
    void actualizarDetalle() throws Exception {

        DetalleDTO dto = new DetalleDTO(
                1,
                10,
                3
        );

        when(service.Actualizar(eq(1), any(DetalleDTO.class)))
                .thenReturn("[+] Detalle Actualizado Correctamente");

        mockMvc.perform(put("/detalleCarrito/actualizar/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void eliminarDetalle() throws Exception {

        when(service.Eliminar(1))
                .thenReturn("[+] El Detalle A Sido Eliminado Con Exito");

        mockMvc.perform(delete("/detalleCarrito/eliminarxid/1"))
                .andExpect(status().isOk());
    }


}
