package com.curso.biblioteca;

import java.util.List;
import java.util.function.Function;

/**
 * Listado 10: El orden de composición cambia el resultado.
 * Demuestra:
 * - f.andThen(g) : primero f, luego g -> g(f(x))
 * - f.compose(g) : primero g, luego f -> f(g(x))
 * - Function.identity() : elemento neutro para construir tuberías dinámicas
 */
public class Listado10ComposicionFunction {

    public static void main(String[] args) {
        System.out.println("=== Listado 10: Composición de Function ===");

        Function<Integer, Integer> bono = precio -> precio - 10_000;      // descuento fijo
        Function<Integer, Integer> iva = precio -> precio * 119 / 100;    // +19 %

        System.out.println("IVA y luego bono: " + iva.andThen(bono).apply(100_000));
        System.out.println("Bono y luego IVA: " + iva.compose(bono).apply(100_000));
        System.out.println("Identidad:        " + Function.<Integer>identity().apply(100_000));

        // identity() es el "elemento neutro": ideal para encadenar una lista de pasos
        List<Function<Integer, Integer>> pasos = List.of(bono, iva, bono);
        Function<Integer, Integer> tuberia = Function.identity();
        for (Function<Integer, Integer> paso : pasos) {
            tuberia = tuberia.andThen(paso);
        }
        System.out.println("Tubería de 3 pasos: " + tuberia.apply(100_000));
    }
}
