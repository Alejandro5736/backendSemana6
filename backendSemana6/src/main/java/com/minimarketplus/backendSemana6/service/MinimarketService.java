package com.minimarketplus.backendSemana6.service;

import com.minimarketplus.backendSemana6.model.*;
import java.util.List;

public class MinimarketService {

    // 1. USUARIO: Autenticación
    public boolean autenticar(Usuario usuario, String passwordIngresada) {
        if (usuario == null) return false;
        return usuario.getPassword().equals(passwordIngresada);
    }

    // 2. PRODUCTO: Modificación (Solo ADMIN)
    public boolean modificarProducto(Usuario usuarioActivo, Producto producto, String nuevoNombre, double nuevoPrecio) {
        if (usuarioActivo == null || !"ADMIN".equals(usuarioActivo.getRol())) {
            throw new SecurityException("Operación no permitida: Solo el administrador puede modificar productos.");
        }
        producto.setNombre(nuevoNombre);
        producto.setPrecio(nuevoPrecio);
        return true;
    }

   // Archivo: src/main/java/com/minimarketplus/backendSemana6/service/MinimarketService.java

public Inventario registrarMovimientoInventario(Usuario usuarioActivo, Long movimientoId, Producto producto, String tipoMovimiento, int cantidad) {
    if (usuarioActivo == null || (!"ADMIN".equals(usuarioActivo.getRol()) && !"ENCARGADO".equals(usuarioActivo.getRol()))) {
        throw new SecurityException("Operación no permitida: Usuario no autorizado para mover inventario.");
    }

    if ("ENTRADA".equalsIgnoreCase(tipoMovimiento)) {
        producto.setStock(producto.getStock() + cantidad);
    } else if ("SALIDA".equalsIgnoreCase(tipoMovimiento)) {
        if (producto.getStock() < cantidad) {
            throw new IllegalArgumentException("Stock insuficiente para realizar la salida.");
        }
        producto.setStock(producto.getStock() - cantidad);
    } else {
        throw new IllegalArgumentException("Tipo de movimiento inválido.");
    }

    // Retornamos el objeto Inventario, que es lo que espera el Controller
    return new Inventario(movimientoId, producto, tipoMovimiento.toUpperCase(), cantidad, usuarioActivo.getUsername());
}

    // 4. VENTA: Generar venta (Solo CAJERO)
    public Venta generarVenta(Usuario usuarioActivo, Long ventaId, List<Producto> productos) {
        if (usuarioActivo == null || !"CAJERO".equals(usuarioActivo.getRol())) {
            throw new SecurityException("Operación no permitida: Solo los cajeros pueden generar ventas.");
        }

        double totalVenta = 0;
        for (Producto p : productos) {
            totalVenta += p.getPrecio();
        }

        return new Venta(ventaId, usuarioActivo.getUsername(), productos, totalVenta);
    }
}