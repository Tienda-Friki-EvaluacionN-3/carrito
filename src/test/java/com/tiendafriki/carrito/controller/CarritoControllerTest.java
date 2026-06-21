package com.tiendafriki.carrito.controller;

import com.tiendafriki.carrito.model.Carrito;
import com.tiendafriki.carrito.service.CarritoServ;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CarritoController.class)
class CarritoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarritoServ service;

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
}
