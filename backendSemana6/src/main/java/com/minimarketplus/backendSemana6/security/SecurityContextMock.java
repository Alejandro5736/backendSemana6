package com.minimarketplus.backendSemana6.security;

import com.minimarketplus.backendSemana6.model.Usuario;

public class SecurityContextMock {
    private static Usuario usuarioAutenticado;

    public static void setUsuarioAutenticado(Usuario usuario) {
        usuarioAutenticado = usuario;
    }

    public static Usuario getUsuarioAutenticado() {
        return usuarioAutenticado;
    }

    public static void clear() {
        usuarioAutenticado = null;
    }
}