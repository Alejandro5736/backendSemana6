package com.minimarketplus.backendSemana6.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import com.minimarketplus.backendSemana6.model.Usuario;
import com.minimarketplus.backendSemana6.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class) // Habilita Mockito
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuarioPrueba;

    @BeforeEach
    void setUp() {
        usuarioPrueba = new Usuario("admin", "12345", "ADMIN");
    }

    @Test
    void testAutenticacionValida() {
        // Configurar comportamiento del mock
        when(usuarioRepository.findByUsername("admin")).thenReturn(Optional.of(usuarioPrueba));

        // Ejecutar
        boolean resultado = usuarioService.autenticar("admin", "12345");

        // Verificar
        assertTrue(resultado, "La autenticación debería ser exitosa con credenciales correctas.");
    }

    @Test
    void testAutenticacionInvalidaPassword() {
        // Configurar comportamiento del mock
        when(usuarioRepository.findByUsername("admin")).thenReturn(Optional.of(usuarioPrueba));

        // Ejecutar
        boolean resultado = usuarioService.autenticar("admin", "contrasena_erronea");

        // Verificar
        assertFalse(resultado, "La autenticación debería fallar con password incorrecto.");
    }
}