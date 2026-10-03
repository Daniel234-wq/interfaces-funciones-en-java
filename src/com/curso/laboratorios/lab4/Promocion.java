package com.curso.laboratorios.lab4;

import com.curso.modelo.Producto;

import java.util.function.DoubleUnaryOperator;
import java.util.function.Predicate;

/**
 * Record que modela una regla de promoción comercial.
 *
 * @param nombre   Nombre descriptivo de la promoción
 * @param aplicaA  Predicado funcional que evalúa si el producto es elegible
 * @param ajuste   Operador funcional que transforma el precio original
 */
public record Promocion(String nombre, Predicate<Producto> aplicaA, DoubleUnaryOperator ajuste) {

    public double precioCon(Producto p) {
        return aplicaA.test(p) ? ajuste.applyAsDouble(p.precio()) : p.precio();
    }
}
