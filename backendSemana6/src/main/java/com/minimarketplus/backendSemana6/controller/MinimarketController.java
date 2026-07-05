package com.minimarketplus.backendSemana6.controller;

import com.minimarketplus.backendSemana6.model.*;
import com.minimarketplus.backendSemana6.service.MinimarketService;
import com.minimarketplus.backendSemana6.security.SecurityContextMock;
import java.util.List;

public class MinimarketController {

    private final MinimarketService minimarketService;

    public MinimarketController(MinimarketService minimarketService) {
        this.minimarketService = minimarketService;
    }

    // Endpoint para modificar producto
    public String modificarProducto(Producto producto, String nuevoNombre, double nuevoPrecio) {
        Usuario usuarioActivo = SecurityContextMock.getUsuarioAutenticado();
        try {
            minimarketService.modificarProducto(usuarioActivo, producto, nuevoNombre, nuevoPrecio);
            return "Producto modificado exitosamente.";
        } catch (SecurityException e) {
            return "Error de seguridad: " + e.getMessage();
        }
    }

    // Endpoint para inventario
    public String registrarInventario(Long movimientoId, Producto producto, String tipo, int cantidad) {
        Usuario usuarioActivo = SecurityContextMock.getUsuarioAutenticado();
        try {
            // Este método debe coincidir exactamente en parámetros con el del Servicio
            minimarketService.registrarMovimientoInventario(usuarioActivo, movimientoId, producto, tipo, cantidad);
            return "Movimiento de inventario registrado con éxito.";
        } catch (Exception e) {
            return "Error en inventario: " + e.getMessage();
        }
    }

    // Endpoint para generar venta
    public String realizarVenta(Long ventaId, List<Producto> productos) {
        Usuario usuarioActivo = SecurityContextMock.getUsuarioAutenticado();
        try {
            minimarketService.generarVenta(usuarioActivo, ventaId, productos);
            return "Venta procesada correctamente.";
        } catch (SecurityException e) {
            return "Error en venta: " + e.getMessage();
        }
    }
}