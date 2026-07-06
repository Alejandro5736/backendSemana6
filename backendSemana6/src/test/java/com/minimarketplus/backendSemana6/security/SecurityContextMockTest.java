package com.minimarketplus.backendSemana6.security;

import com.minimarketplus.backendSemana6.model.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SecurityContextMockTest {

    @BeforeEach
    public void setUp() {
        // Limpiamos el contexto antes de cada test
        SecurityContextMock.clear();
    }

    @Test
    public void testSetAndGetUsuarioAutenticado() {
        Usuario usuario = new Usuario();
        
        SecurityContextMock.setUsuarioAutenticado(usuario);
        
        assertEquals(usuario, SecurityContextMock.getUsuarioAutenticado(), 
            "El usuario recuperado debe ser el mismo que se autenticó.");
    }

    @Test
    public void testClearContext() {
        Usuario usuario = new Usuario();
        SecurityContextMock.setUsuarioAutenticado(usuario);
        
        SecurityContextMock.clear();
        
        assertNull(SecurityContextMock.getUsuarioAutenticado(), 
            "El contexto de seguridad debería quedar en null tras el clear.");
    }
}