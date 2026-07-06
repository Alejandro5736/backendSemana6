package com.minimarketplus.backendSemana6.controller;

import com.minimarketplus.backendSemana6.model.*;
import com.minimarketplus.backendSemana6.service.MinimarketService;
import com.minimarketplus.backendSemana6.security.SecurityContextMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MinimarketControllerTest {

    private MinimarketService minimarketService;
    private MinimarketController minimarketController;
    private Usuario usuarioDummy;

    @BeforeEach
    public void setUp() {
        // 1. Inicializar el Mock del servicio
        minimarketService = mock(MinimarketService.class);
        // 2. Inyectarlo en el controlador a probar
        minimarketController = new MinimarketController(minimarketService);
        
        // 3. Simular que hay un usuario autenticado en el Mock de Seguridad
        usuarioDummy = new Usuario();
        SecurityContextMock.setUsuarioAutenticado(usuarioDummy);
    }

    @Test
    public void testModificarProductoExitoso() {
        Producto producto = new Producto();
        
        String resultado = minimarketController.modificarProducto(producto, "Arroz Premium", 1500.0);
        
        assertEquals("Producto modificado exitosamente.", resultado);
        verify(minimarketService, times(1)).modificarProducto(usuarioDummy, producto, "Arroz Premium", 1500.0);
    }

    @Test
    public void testModificarProductoErrorSeguridad() {
        Producto producto = new Producto();
        // Forzamos al servicio a lanzar la excepción de seguridad que tu controlador captura
        doThrow(new SecurityException("Permisos insuficientes"))
            .when(minimarketService).modificarProducto(any(), any(), anyString(), anyDouble());

        String resultado = minimarketController.modificarProducto(producto, "Arroz Premium", 1500.0);
        
        assertTrue(resultado.contains("Error de seguridad: Permisos insuficientes"));
    }

    @Test
    public void testRegistrarInventarioExitoso() {
        Producto producto = new Producto();
        
        String resultado = minimarketController.registrarInventario(101L, producto, "ENTRADA", 50);
        
        assertEquals("Movimiento de inventario registrado con éxito.", resultado);
        verify(minimarketService, times(1)).registrarMovimientoInventario(usuarioDummy, 101L, producto, "ENTRADA", 50);
    }

    @Test
    public void testRegistrarInventarioError() {
        Producto producto = new Producto();
        doThrow(new RuntimeException("Stock no disponible"))
            .when(minimarketService).registrarMovimientoInventario(any(), anyLong(), any(), anyString(), anyInt());

        String resultado = minimarketController.registrarInventario(101L, producto, "SALIDA", 500);
        
        assertTrue(resultado.contains("Error en inventario: Stock no disponible"));
    }

    @Test
    public void testRealizarVentaExitosa() {
        List<Producto> productos = new ArrayList<>();
        
        String resultado = minimarketController.realizarVenta(5001L, productos);
        
        assertEquals("Venta procesada correctamente.", resultado);
        verify(minimarketService, times(1)).generarVenta(usuarioDummy, 5001L, productos);
    }

    @Test
    public void testRealizarVentaErrorSeguridad() {
        List<Producto> productos = new ArrayList<>();
        doThrow(new SecurityException("Cliente no válido"))
            .when(minimarketService).generarVenta(any(), anyLong(), anyList());

        String resultado = minimarketController.realizarVenta(5001L, productos);
        
        assertTrue(resultado.contains("Error en venta: Cliente no válido"));
    }
}