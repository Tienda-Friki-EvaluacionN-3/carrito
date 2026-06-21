package com.tiendafriki.carrito.controller;

import com.tiendafriki.carrito.service.CarritoServ;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.tiendafriki.carrito.dto.CarritoDTO;
import com.tiendafriki.carrito.model.Carrito;
import jakarta.validation.*;
import java.util.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/carrito")
public class CarritoController {

    private final CarritoServ s;

    CarritoController(CarritoServ s) {
        this.s = s;
    }

    @Operation(summary = "Listar carritos", description = "Devuelve una lista con todos los carritos registrados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operación exitosa"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/listar")
    public List<Carrito> listar() {
        return s.listar();
    }

    @Operation(summary = "Buscar carrito por ID", description = "Busca y devuelve un carrito específico utilizando su identificador")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operación exitosa"),
        @ApiResponse(responseCode = "404", description = "Carrito no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/buscarxid/{id}")
    public Carrito buscarxID(@PathVariable Integer id) {
        return s.buscarxID(id)
                .orElseThrow(() ->
                        new NoSuchElementException(
                                "[+] Carrito No Encontrado [>_<] ... "
                        )
                );
    }

    @Operation(summary = "Calcular total del carrito", description = "Calcula el costo total acumulado dentro de un carrito por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operación exitosa"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/total/{id}")
    public ResponseEntity<?> total(@PathVariable Integer id) {
        return ResponseEntity.ok(
                s.calcularTotal(id)
        );
    }

    @Operation(summary = "Buscar carrito por RUT", description = "Busca y devuelve un carrito asociado al RUT de un cliente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operación exitosa"),
        @ApiResponse(responseCode = "404", description = "Carrito no encontrado"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/buscarxrut/{rut}")
    public Carrito buscarxRut(@PathVariable String rut) {
        return s.buscarxRut(rut)
                .orElseThrow(() ->
                        new NoSuchElementException(
                                "[+] Carrito No Encontrado [>_<] ... "
                        )
                );
    }

    @Operation(summary = "Crear carrito", description = "Registra un nuevo carrito de compras en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Carrito creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/crear")
    public ResponseEntity<?> Crear(
            @Valid @RequestBody CarritoDTO dto
    ) {
        return ResponseEntity
                .status(201)
                .body(s.Guardar(dto));
    }

    @Operation(summary = "Eliminar carrito", description = "Elimina de forma permanente un carrito de compras utilizando su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Carrito eliminado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/eliminarxid/{id}")
    public ResponseEntity<?> Eliminar(
            @PathVariable Integer id
    ) {
        return ResponseEntity.ok(
                s.Eliminar(id)
        );
    }
}