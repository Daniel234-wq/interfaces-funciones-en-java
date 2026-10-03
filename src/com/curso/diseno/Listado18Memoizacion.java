package com.curso.diseno;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Listado 18: Envolver cualquier función con una caché (Memoización).
 * Demuestra el uso de computeIfAbsent para recordar resultados de funciones costosas.
 */
public class Listado18Memoizacion {

    public static <T, R> Function<T, R> memorizar(Function<T, R> funcion) {
        Map<T, R> cache = new HashMap<>();
        return entrada -> cache.computeIfAbsent(entrada, funcion);
    }

    public static int calculosReales = 0;

    public static long tarifaPorCiudad(String ciudad) { // imagina una consulta lenta
        calculosReales++;
        return ciudad.length() * 1_000L;
    }

    public static void main(String[] args) {
        System.out.println("=== Listado 18: Memoización de funciones ===");
        Function<String, Long> tarifa = memorizar(Listado18Memoizacion::tarifaPorCiudad);
        List<String> consultas = List.of("Bogotá", "Cali", "Bogotá", "Medellín", "Cali", "Bogotá");

        for (String ciudad : consultas) {
            System.out.print(ciudad + "=" + tarifa.apply(ciudad) + " ");
        }
        System.out.println();
        System.out.println("Cálculos reales: " + calculosReales + " de " + consultas.size() + " consultas");
    }
}
