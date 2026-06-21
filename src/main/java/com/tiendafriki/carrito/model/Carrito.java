package com.tiendafriki.carrito.model;

import jakarta.persistence.*;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.time.*;
import lombok.*;

@Table(name = "Carrito")
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data

public class Carrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 12, unique = true)
    private String rutUsuario;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @OneToMany(mappedBy = "carrito", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List <Detalle> detalles;

}