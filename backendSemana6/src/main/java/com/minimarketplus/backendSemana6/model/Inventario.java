package com.minimarketplus.backendSemana6.model;

public class Inventario {
    private Long id;
    private Producto producto;
    private String tipoMovimiento; // "ENTRADA" o "SALIDA"
    private int cantidad;
    private String usuarioResponsable;

    // Constructor vacío
    public Inventario() {}

    // ESTE ES EL CONSTRUCTOR QUE EL SERVICE NECESITA (5 parámetros)
    public Inventario(Long id, Producto producto, String tipoMovimiento, int cantidad, String usuarioResponsable) {
        this.id = id;
        this.producto = producto;
        this.tipoMovimiento = tipoMovimiento;
        this.cantidad = cantidad;
        this.usuarioResponsable = usuarioResponsable;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }

    public String getTipoMovimiento() { return tipoMovimiento; }
    public void setTipoMovimiento(String tipoMovimiento) { this.tipoMovimiento = tipoMovimiento; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public String getUsuarioResponsable() { return usuarioResponsable; }
    public void setUsuarioResponsable(String usuarioResponsable) { this.usuarioResponsable = usuarioResponsable; }
}