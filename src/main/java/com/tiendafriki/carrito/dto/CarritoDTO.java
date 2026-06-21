package com.tiendafriki.carrito.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarritoDTO {
    
    @NotBlank(message = "[ERROR] El Rut Del Usuario No Puede Quedar Vacio [>_<] ... ")
    @Pattern(
    regexp = "^\\d{1,2}\\.\\d{3}\\.\\d{3}-[\\dkK]$|^\\d{7,8}-[\\dkK]$",
    message = "[ERROR] El Formato Del Rut Es Invalido [>_<] ... ")
    private String rutUsuario;

}