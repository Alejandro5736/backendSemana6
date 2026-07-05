package com.minimarketplus.backendSemana6.service;

import static org.junit.jupiter.api.Assertions.*;
import com.minimarketplus.backendSemana6.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

public class MinimarketServiceTest {

    private MinimarketService service;
    private Usuario admin;
    private Usuario cajero;
    private Usuario encargado;
    private Producto producto;

    @BeforeEach
    void setUp() {
        service = new MinimarketService();
        admin = new Usuario("admin", "123", "ADMIN");
        cajero = new Usuario("cajero", "123", "CAJERO");
        encargado = new Usuario("encargado", "123", "ENCARGADO");
        producto = new Producto(1L, "Leche", 10.0, 50);
    }

    @Test
    void testModificarProductoSoloAdmin() {
        // Prueba exitosa
        assertDoesNotThrow(() -> service.modificarProducto(admin, producto, "Leche Entera", 12.0));
        assertEquals("Leche Entera", producto.getNombre());

        // Prueba de fallo (no es admin)
        assertThrows(SecurityException.class, () -> service.modificarProducto(cajero, producto, "Error", 0.0));
    }

    @Test
    void testMovimientosInventario() {
        // Prueba de entrada
        service.registrarMovimientoInventario(encargado, 1L, producto, "ENTRADA", 10);
        assertEquals(60, producto.getStock());

        // Prueba de salida (encargado tiene permiso)
        service.registrarMovimientoInventario(encargado, 2L, producto, "SALIDA", 5);
        assertEquals(55, producto.getStock());

        // Prueba de seguridad
        assertThrows(SecurityException.class, () -> service.registrarMovimientoInventario(cajero, 3L, producto, "SALIDA", 1));
    }

    @Test
    void testGenerarVentaSoloCajero() {
        List<Producto> carrito = Arrays.asList(producto);

        // Prueba exitosa
        Venta venta = service.generarVenta(cajero, 100L, carrito);
        assertNotNull(venta);
        assertEquals(10.0, venta.getTotal());

        // Prueba de fallo (admin no debe generar venta si la regla es exclusiva de cajeros)
        assertThrows(SecurityException.class, () -> service.generarVenta(admin, 101L, carrito));
    }
}