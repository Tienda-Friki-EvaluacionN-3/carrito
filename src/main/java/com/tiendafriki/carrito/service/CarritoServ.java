package com.tiendafriki.carrito.service;

import com.tiendafriki.carrito.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.tiendafriki.carrito.dto.CarritoDTO;
import com.tiendafriki.carrito.model.*;
import java.time.*;
import java.util.*;

@Service

public class CarritoServ {

    private final CarritoRepo cr;

    private final DetalleRepo dr;

    CarritoServ(CarritoRepo cr, DetalleRepo dr) {
        this.cr = cr;
        this.dr = dr;
    }

    public List<Carrito> listar() {

        return cr.findAll();
    }

    public Optional<Carrito> buscarxID(Integer id) {

        return cr.findById(id);
    }

    public Optional<Carrito> buscarxRut(String rut) {

        return cr.findByRutUsuarioIgnoreCase(rut);
    }

    public Double calcularTotal(Integer carritoId) {

        if (!cr.existsById(carritoId)) {

            throw new NoSuchElementException(
                    "[ERROR] Carrito No Encontrado [>_<] ... "
            );
        }

        List<Detalle> detalles =
                dr.findByCarrito_Id(carritoId);

        double subtotal =
                detalles.stream()
                        .mapToDouble(Detalle::getSubtotal)
                        .sum();

        double iva = subtotal * 0.19;

        return subtotal + iva;
    }

@SuppressWarnings("unchecked")
public String Guardar(CarritoDTO dto) {

    Optional<Carrito> existente =
            cr.findByRutUsuarioIgnoreCase(
                    dto.getRutUsuario()
            );

    if (existente.isPresent()) {

        throw new IllegalArgumentException(
                "[ERROR]  Ya Existe Un Carrito Para El Usuario "
                        + dto.getRutUsuario()
                        + " [>_<] ... "
        );
    }

    RestTemplate rt = new RestTemplate();

    String url =
            "http://localhost:8080/auth/buscarxrutusuario/"
                    + dto.getRutUsuario();

    try {

        Map<String, Object> usuario =
                rt.getForObject(url, Map.class);

        if (usuario == null || usuario.isEmpty()) {

            throw new NoSuchElementException(
                    "[ERROR]  Usuario No Existe [>_<] ... "
            );
        }

    }

    catch (HttpClientErrorException.NotFound e) {

        throw new NoSuchElementException(
                "[ERROR]  Usuario No Existe [>_<] ... "
        );
    }

    catch (Exception e) {

        e.printStackTrace();

        throw new RuntimeException(
                "[ERROR] Falla Al Conectarse Con Auth [>_<] ... "
        );
    }

    Carrito c = new Carrito();

    c.setRutUsuario(dto.getRutUsuario());

    c.setFecha(LocalDateTime.now());

    cr.save(c);

    return "[+] Carrito Creado Correctamente Para "
            + dto.getRutUsuario()
            + " [>_<] ... ";
}

    public String Eliminar(Integer id) {

        Optional<Carrito> ct =
                cr.findById(id);

        if (ct.isEmpty()) {

            throw new NoSuchElementException(
                    "[ERROR] Carrito Con El ID : "
                            + id
                            + " No Ha Sido Encontrado [>_<] ... "
            );
        }

        cr.deleteById(id);

        return "[+] Carrito Eliminado Correctamente [>_<] ... ";
    }

}