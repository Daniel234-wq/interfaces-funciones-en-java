package com.curso.diseno;

import com.curso.modelo.Catalogo;
import com.curso.modelo.Producto;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleFunction;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Predicate;

/**
 * Listado 16: Métodos que devuelven funciones y currificación.
 * Demuestra:
 * - Fábricas de predicados y operadores
 * - Currificación (transformar función de varios parámetros en cadena de funciones de 1 parámetro)
 */
public class Listado16OrdenSuperiorYCurrificacion {

    // Fábrica: cada llamada configura y devuelve un criterio nuevo
    public static Predicate<Producto> precioEntre(double minimo, double maximo) {
        return p -> p.precio() >= minimo && p.precio() <= maximo;
    }

    public static DoubleUnaryOperator descuento(double porcentaje) {
        return precio -> precio * (1 - porcentaje / 100);
    }

    public static void main(String[] args) {
        System.out.println("=== Listado 16: Funciones de orden superior y currificación ===");
        List<String> rango = new ArrayList<>();
        for (Producto p : Catalogo.muestra()) {
            if (precioEntre(50_000, 500_000).test(p)) {
                rango.add(p.nombre());
            }
        }
        System.out.println("Entre $50.000 y $500.000: " + rango);

        // Currificación: porcentaje -> (precio -> resultado)
        DoubleFunction<DoubleUnaryOperator> descuentoCurry = pct -> precio -> precio * (1 - pct / 100);
        DoubleUnaryOperator blackFriday = descuentoCurry.apply(40);

        System.out.println(descuento(15).applyAsDouble(200_000) + " | " + blackFriday.applyAsDouble(200_000));
    }
}
