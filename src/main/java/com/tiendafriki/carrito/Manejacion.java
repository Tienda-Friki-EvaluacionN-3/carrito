package com.tiendafriki.carrito;

import com.tiendafriki.carrito.dto.ErrorDTO;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import jakarta.servlet.http.HttpServletRequest;
import java.time.*;
import java.util.*;

@RestControllerAdvice

public class Manejacion {

    @ExceptionHandler(MethodArgumentNotValidException.class)

    public ResponseEntity<ErrorDTO> ErrorValidacion(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        Map<String, String> Errores = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
        Errores.put(error.getField(),error.getDefaultMessage()
        ));

        ErrorDTO errorDTO = new ErrorDTO(

                LocalDateTime.now(),
                400,
                "[ERROR] : 400 Error En La Validacion [>_<] ... ",
                Errores,
                request.getRequestURI());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }

    @ExceptionHandler(IllegalArgumentException.class)

    public ResponseEntity<ErrorDTO> ErrorSolicitud(
            IllegalArgumentException ex,
            HttpServletRequest request) {

        ErrorDTO error = new ErrorDTO(

                LocalDateTime.now(),

                400,

                ex.getMessage(),

                null,

                request.getRequestURI());

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(NoSuchElementException.class)

    public ResponseEntity<ErrorDTO> ErrorNoEncontrado(
            NoSuchElementException ex,
            HttpServletRequest request
    ) {

        ErrorDTO error =
                new ErrorDTO(

                        LocalDateTime.now(),

                        404,

                        "[ERROR] : 404 Recurso No Encontrado [>_<] ... ",

                        null,

                        request.getRequestURI()
                );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }

        @ExceptionHandler(RuntimeException.class)

        public ResponseEntity<ErrorDTO> manejarErrorInterno(
                RuntimeException ex,
                HttpServletRequest request) {

        Map<String, String> errores = new HashMap<>();

        errores.put("error", ex.getMessage());

        ErrorDTO errorDTO = new ErrorDTO(

                LocalDateTime.now(),

                500,

                "[ERROR] Error Interno del Servidor [X_X]",

                errores,

                request.getRequestURI());

        return ResponseEntity.status(500).body(errorDTO);
        }

}
