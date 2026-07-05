package com.minimarketplus.backendSemana6.repository;

import com.minimarketplus.backendSemana6.model.Producto;
import java.util.Optional;

public interface ProductoRepository {
    Optional<Producto> findById(Long id);
    Producto save(Producto producto);
}