package com.tiendafriki.carrito.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tiendafriki.carrito.model.Carrito;
import java.util.*;

public interface CarritoRepo extends JpaRepository <Carrito, Integer> {
    Optional <Carrito> findByRutUsuarioIgnoreCase(String rutUsuario);
}