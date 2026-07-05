package com.minimarketplus.backendSemana6.service;

import com.minimarketplus.backendSemana6.model.Usuario;
import com.minimarketplus.backendSemana6.repository.UsuarioRepository;
import java.util.Optional;

public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public boolean autenticar(String username, String passwordIngresada) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByUsername(username);
        if (usuarioOpt.isEmpty()) {
            return false;
        }
        return usuarioOpt.get().getPassword().equals(passwordIngresada);
    }
}