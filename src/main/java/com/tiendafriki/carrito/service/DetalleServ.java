package com.tiendafriki.carrito.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.tiendafriki.carrito.repository.*;
import com.tiendafriki.carrito.model.*;
import com.tiendafriki.carrito.dto.*;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class DetalleServ {

    private final DetalleRepo dr;

    private final CarritoRepo cr;

    DetalleServ(DetalleRepo dr, CarritoRepo cr) {
        this.dr = dr;
        this.cr = cr;
    }

    private Integer ObtenerPrecio(Integer productoId) {

        RestTemplate rt = new RestTemplate();

        String url =
                "http://localhost:8081/catalogo/buscarxid/"
                        + productoId;

        try {

            Map<?, ?> response =
                    rt.getForObject(
                            url,
                            Map.class
                    );

            if (response == null
                    || !response.containsKey("precio")) {

                throw new RuntimeException(
                        "[ERROR] El Producto No Existe En El Catalogo [>_<] ... "
                );
            }

            return (Integer) response.get("precio");

        } catch (Exception e) {

            throw new RuntimeException(
                    "[ERROR] Fallo Al Consultar Catalogo [>_<] ... "
            );
        }
    }

    public List<Detalle> listar() {

        return dr.findAll();
    }

    public Optional<Detalle> buscarxID(Integer id) {

        return dr.findById(id);
    }

    public List<Detalle> buscarxCarrito(Integer carritoId) {

        return dr.findByCarrito_Id(carritoId);
    }

    public List<Detalle> buscarxProducto(Integer productoId) {

        return dr.findByProductoId(productoId);
    }

    public String Guardar(DetalleDTO dto) {

        Optional<Carrito> ct =
                cr.findById(dto.getCarritoId());

        if (ct.isEmpty()) {

            throw new NoSuchElementException(
                    "[ERROR] El Carrito Con El ID : "
                            + dto.getCarritoId()
                            + " No Existe [>_<] ... "
            );
        }

        Integer precioxUnidad =
                ObtenerPrecio(dto.getProductoId());

        Integer subtotal =
                dto.getCantidad() * precioxUnidad;

        Detalle dt = new Detalle();

        dt.setCarrito(ct.get());

        dt.setProductoId(dto.getProductoId());

        dt.setCantidad(dto.getCantidad());

        dt.setPrecioxUnidad(precioxUnidad);

        dt.setSubtotal(subtotal);

        dr.save(dt);

        Carrito carrito = ct.get();

        carrito.setFecha(LocalDateTime.now());

        cr.save(carrito);

        return "[+] Detalle Agregado Exitosamente [>_<] ... "
                + "[+] Cantidad : "
                + dto.getCantidad()
                + " | "
                + "[+] PrecioxUnidad : $"
                + precioxUnidad
                + " | "
                + "[+] Subtotal : $"
                + subtotal
                + " [>_<] ... ";
    }

    public String Actualizar(
            Integer id,
            DetalleDTO dto
    ) {

        Optional<Detalle> dt =
                dr.findById(id);

        if (dt.isEmpty()) {

            throw new NoSuchElementException(
                    "[ERROR] Detalle Con El ID : "
                            + id
                            + " No Encontrado [>_<] ... "
            );
        }

        Optional<Carrito> ct =
                cr.findById(dto.getCarritoId());

        if (ct.isEmpty()) {

            throw new NoSuchElementException(
                    "[ERROR] El Carrito No Existe [>_<] ... "
            );
        }

        Integer precioxUnidad =
                ObtenerPrecio(dto.getProductoId());

        Integer subtotal =
                dto.getCantidad() * precioxUnidad;

        Detalle detalle = dt.get();

        detalle.setCarrito(ct.get());

        detalle.setProductoId(dto.getProductoId());

        detalle.setCantidad(dto.getCantidad());

        detalle.setPrecioxUnidad(precioxUnidad);

        detalle.setSubtotal(subtotal);

        dr.save(detalle);

        Carrito carrito = ct.get();

        carrito.setFecha(LocalDateTime.now());

        cr.save(carrito);

        return "[+] Detalle Actualizado Correctamente "
                + "[+] Cantidad : "
                + dto.getCantidad()
                + " | "
                + "[+] PrecioxUnidad : $"
                + precioxUnidad
                + " | "
                + "[+] Subtotal : $"
                + subtotal
                + " [>_<] ... ";
    }

    public String Eliminar(Integer id) {

        Optional<Detalle> dt = dr.findById(id);

        if (dt.isEmpty()) {

            throw new NoSuchElementException(
                    "[ERROR] Detalle Con El ID : "
                            + id
                            + " No A Sido Encontrado [>_<] ... "
            );
        }

        dr.deleteById(id);

        return "[+] El Detalle A Sido Eliminado Con Exito [>_<] ... ";
    }

    

}