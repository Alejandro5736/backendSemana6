package com.minimarketplus.backendSemana6.repository;

import com.minimarketplus.backendSemana6.model.Usuario;
import java.util.Optional;

public interface UsuarioRepository {
    Optional<Usuario> findByUsername(String username);
}