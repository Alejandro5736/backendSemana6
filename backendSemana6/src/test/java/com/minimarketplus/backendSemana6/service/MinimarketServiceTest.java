package com.minimarketplus.backendSemana6.service;

import com.minimarketplus.backendSemana6.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MinimarketServiceTest {

    private MinimarketService minimarketService;
    private Producto productoEjemplo;

    @BeforeEach
    public void setUp() {
        minimarketService = new MinimarketService();
        // Inicializamos un producto con ID 1, nombre, precio 2.5 y stock inicial de 10 unidades
        productoEjemplo = new Producto(1L, "Leche", 2.5, 10);
    }

    // ==========================================
    // 1. PRUEBAS DE AUTENTICACIÓN (USUARIO)
    // ==========================================
    @Test
    public void testAutenticarExitoso() {
        Usuario usuario = new Usuario("juan123", "clave123", "CAJERO");
        assertTrue(minimarketService.autenticar(usuario, "clave123"));
    }

    @Test
    public void testAutenticarFallido() {
        Usuario usuario = new Usuario("juan123", "clave123", "CAJERO");
        assertFalse(minimarketService.autenticar(usuario, "clave_incorrecta"));
    }

    // ==========================================
    // 2. PRUEBAS DE PRODUCTO (ROLES)
    // ==========================================
    @Test
    public void testModificarProductoComoAdminExitoso() {
        Usuario admin = new Usuario("admin01", "adminpass", "ADMIN");
        boolean resultado = minimarketService.modificarProducto(admin, productoEjemplo, "Leche Descremada", 3.0);
        
        assertTrue(resultado);
        assertEquals("Leche Descremada", productoEjemplo.getNombre());
        assertEquals(3.0, productoEjemplo.getPrecio());
    }

    @Test
    public void testModificarProductoComoNoAdminFalla() {
        Usuario cajero = new Usuario("cajero01", "cajeropass", "CAJERO");
        
        assertThrows(SecurityException.class, () -> {
            minimarketService.modificarProducto(cajero, productoEjemplo, "Intento Hacker", 99.0);
        });
    }

    // ==========================================
    // 3. PRUEBAS DE INVENTARIO
    // ==========================================
    @Test
    public void testRegistrarEntradaInventarioExitoso() {
        Usuario encargado = new Usuario("pedro", "pass", "ENCARGADO");
        Inventario inv = minimarketService.registrarMovimientoInventario(encargado, 101L, productoEjemplo, "ENTRADA", 5);
        
        assertNotNull(inv);
        assertEquals(15, productoEjemplo.getStock()); // 10 iniciales + 5 entrada
    }

    @Test
    public void testRegistrarSalidaInventarioInsuficienteFalla() {
        Usuario admin = new Usuario("admin01", "adminpass", "ADMIN");
        
        assertThrows(IllegalArgumentException.class, () -> {
            // Se intentan sacar 20 unidades pero solo hay 10
            minimarketService.registrarMovimientoInventario(admin, 102L, productoEjemplo, "SALIDA", 20);
        });
    }

    // ==========================================
    // 4. PRUEBAS DE VENTA Y ESCENARIOS COMPLEJOS
    // ==========================================
    @Test
    public void testGenerarVentaComoCajeroYReduceStock() {
        Usuario cajero = new Usuario("cajero01", "cajeropass", "CAJERO");
        List<Producto> carrito = new ArrayList<>();
        carrito.add(productoEjemplo); // Stock inicial es 10

        Venta venta = minimarketService.generarVenta(cajero, 500L, carrito);
        
        assertNotNull(venta);
        assertEquals(2.5, venta.getTotal());
        assertEquals(9, productoEjemplo.getStock()); // Validamos que restó 1 unidad del stock
    }

    @Test
    public void testGenerarVentaFallaPorStockCritico() {
        Usuario cajero = new Usuario("cajero01", "cajeropass", "CAJERO");
        
        // Seteamos el stock críticamente en 0
        productoEjemplo.setStock(0); 
        
        List<Producto> carrito = new ArrayList<>();
        carrito.add(productoEjemplo);

        // Debe lanzar la excepción debido a la validación de stock <= 0
        assertThrows(IllegalArgumentException.class, () -> {
            minimarketService.generarVenta(cajero, 501L, carrito);
        });
    }

    @Test
    public void testGenerarVentaRolInvalidoFalla() {
        Usuario cliente = new Usuario("cliente01", "pass", "CLIENTE");
        List<Producto> carrito = new ArrayList<>();
        carrito.add(productoEjemplo);

        assertThrows(SecurityException.class, () -> {
            minimarketService.generarVenta(cliente, 502L, carrito);
        });
    }

    @Test
    public void testGenerarVentaProductoInexistenteFalla() {
        Usuario cajero = new Usuario("cajero01", "cajeropass", "CAJERO");
        List<Producto> carrito = new ArrayList<>();
        
        // Simulamos un producto fantasma con un ID que no está mapeado en la base de datos simulada
        Producto productoFantasma = new Producto(999L, "Producto Inexistente", 10.0, 0);
        carrito.add(productoFantasma);

        // Debe lanzar una excepción porque rompe las reglas de consistencia de inventario real
        assertThrows(IllegalArgumentException.class, () -> {
            minimarketService.generarVenta(cajero, 503L, carrito);
        });
    }
}