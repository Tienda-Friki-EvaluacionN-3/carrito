package com.tiendafriki.carrito.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tiendafriki.carrito.model.Detalle;
import java.util.List;

public interface DetalleRepo extends JpaRepository <Detalle, Integer> {
    
    List <Detalle> findByCarrito_Id(Integer carritoId);
    List <Detalle> findByProductoId(Integer productoId);
}