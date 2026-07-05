package com.minimarketplus.backendSemana6.model;

import java.util.List;

public class Venta {
    private Long id;
    private String cajeroUsername;
    private List<Producto> productosVendidos;
    private double total;

    public Venta() {}

    public Venta(Long id, String cajeroUsername, List<Producto> productosVendidos, double total) {
        this.id = id;
        this.cajeroUsername = cajeroUsername;
        this.productosVendidos = productosVendidos;
        this.total = total;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCajeroUsername() { return cajeroUsername; }
    public void setCajeroUsername(String cajeroUsername) { this.cajeroUsername = cajeroUsername; }

    public List<Producto> getProductosVendidos() { return productosVendidos; }
    public void setProductosVendidos(List<Producto> productosVendidos) { this.productosVendidos = productosVendidos; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
}