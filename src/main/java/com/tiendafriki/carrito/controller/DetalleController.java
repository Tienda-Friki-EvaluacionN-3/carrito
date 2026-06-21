package com.tiendafriki.carrito.controller;

import com.tiendafriki.carrito.service.DetalleServ;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.tiendafriki.carrito.dto.DetalleDTO;
import com.tiendafriki.carrito.model.Detalle;
import jakarta.validation.Valid;
import java.util.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/detalleCarrito")
public class DetalleController {

        private final DetalleServ s;

        DetalleController(DetalleServ s) {
                this.s = s;
        }

        @Operation(summary = "Listar detalles", description = "Devuelve una lista con todos los detalles de carritos registrados")
        @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
        })
        @GetMapping("/listar")
        public List<Detalle> listar() {
                return s.listar();
        }

        @Operation(summary = "Buscar detalle por ID", description = "Busca un detalle específico utilizando su identificador")
        @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa"),
            @ApiResponse(responseCode = "404", description = "Detalle no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
        })
        @GetMapping("/buscarxid/{id}")
        public Detalle buscarxId(@PathVariable Integer id) {
                return s.buscarxID(id)
                        .orElseThrow(() ->
                                new NoSuchElementException(
                                        "[+] Detalle No Encontrado [>_<] ... "
                                )
                        );
        }

        @Operation(summary = "Buscar detalles por ID de carrito", description = "Devuelve la lista de detalles pertenecientes a un carrito específico")
        @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
        })
        @GetMapping("/carrito/{carritoId}")
        public List<Detalle> buscarxCarrito(@PathVariable Integer carritoId) {
                return s.buscarxCarrito(carritoId);
        }

        @Operation(summary = "Buscar detalles por ID de producto", description = "Devuelve la lista de detalles asociados a un producto específico")
        @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
        })
        @GetMapping("/producto/{productoId}")
        public List<Detalle> buscarxProducto(@PathVariable Integer productoId) {
                return s.buscarxProducto(productoId);
        }

        @Operation(summary = "Agregar detalle", description = "Registra un nuevo producto y su cantidad dentro del detalle de un carrito")
        @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Detalle agregado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
        })
        @PostMapping("/agregar")
        public ResponseEntity<?> Agregar(@Valid @RequestBody DetalleDTO dto) {
                return ResponseEntity
                        .status(201)
                        .body(s.Guardar(dto));
        }

        @Operation(summary = "Actualizar detalle", description = "Modifica los datos de un detalle existente mediante su ID")
        @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Detalle actualizado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
        })
        @PutMapping("/actualizar/{id}")
        public ResponseEntity<?> Actualizar(
                @PathVariable Integer id,
                @Valid @RequestBody DetalleDTO dto
        ) {
                return ResponseEntity.ok(
                        s.Actualizar(id, dto)
                );
        }

        @Operation(summary = "Eliminar detalle", description = "Elimina un registro de detalle permanentemente utilizando su ID")
        @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Detalle eliminado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
        })
        @DeleteMapping("/eliminarxid/{id}")
        public ResponseEntity<?> Eliminar(@PathVariable Integer id) {
                return ResponseEntity.ok(
                        s.Eliminar(id)
                );
        }
}