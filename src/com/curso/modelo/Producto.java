package com.curso.modelo;

/**
 * Modelo de datos compartido por los ejemplos de la guía.
 * Representa un producto en el catálogo de la tienda.
 */
public record Producto(String nombre, String categoria, double precio, int stock) {
    public boolean disponible() {
        return stock > 0;
    }
}
