package com.curso.retos;

import java.util.Objects;
import java.util.function.Function;

/**
 * Reto 1: TriFunction con Currificación.
 * Objetivo: Escribir TriFunction.curry(), que convierta una TriFunction<A,B,C,R> en
 * Function<A, Function<B, Function<C, R>>>.
 */
public class Reto1TriFunctionCurry {

    @FunctionalInterface
    public interface TriFunction<A, B, C, R> {
        R aplicar(A a, B b, C c);

        /**
         * Compone esta función con otra que se ejecuta después.
         */
        default <V> TriFunction<A, B, C, V> luego(Function<? super R, ? extends V> despues) {
            Objects.requireNonNull(despues);
            return (a, b, c) -> despues.apply(aplicar(a, b, c));
        }

        /**
         * Currificación: Transforma una función ternaria f(a, b, c)
         * en una cadena de funciones unarias: a -> b -> c -> f(a, b, c).
         * Permite la aplicación parcial de argumentos paso a paso.
         */
        default Function<A, Function<B, Function<C, R>>> curry() {
            return a -> b -> c -> aplicar(a, b, c);
        }

        /**
         * Operación inversa (descurrificación): Recompone una función currificada en TriFunction.
         */
        static <A, B, C, R> TriFunction<A, B, C, R> uncurry(Function<A, Function<B, Function<C, R>>> curried) {
            Objects.requireNonNull(curried);
            return (a, b, c) -> curried.apply(a).apply(b).apply(c);
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Reto 1: Currificación con TriFunction.curry() ===");

        // Función original que calcula el costo total con impuesto y descuento:
        // f(precioBase, tasaIva, porcentajeDescuento) -> total
        TriFunction<Double, Double, Double, Double> calculadorCosto =
                (precioBase, tasaIva, pctDescuento) -> {
                    double conIva = precioBase * (1 + tasaIva);
                    return conIva * (1 - pctDescuento);
                };

        // Uso directo como TriFunction (3 argumentos a la vez):
        double totalDirecto = calculadorCosto.aplicar(100_000.0, 0.19, 0.10);
        System.out.printf("Cálculo directo (TriFunction): $%,.2f%n", totalDirecto);

        // Currificación:
        Function<Double, Function<Double, Function<Double, Double>>> curried = calculadorCosto.curry();

        // Aplicación parcial Paso 1: fijar el precio base
        Function<Double, Function<Double, Double>> precioFijado = curried.apply(100_000.0);

        // Aplicación parcial Paso 2: fijar IVA Colombia (19%)
        Function<Double, Double> tarifaColombia = precioFijado.apply(0.19);

        // Aplicación final Paso 3: evaluar con distintos descuentos de campaña
        double conDescuento10 = tarifaColombia.apply(0.10);
        double conDescuento20 = tarifaColombia.apply(0.20);
        double sinDescuento   = tarifaColombia.apply(0.0);

        System.out.printf("Currificado - Con 10%% dto:      $%,.2f%n", conDescuento10);
        System.out.printf("Currificado - Con 20%% dto:      $%,.2f%n", conDescuento20);
        System.out.printf("Currificado - Sin descuento:     $%,.2f%n", sinDescuento);

        // Verificación de la descurrificación (uncurry)
        TriFunction<Double, Double, Double, Double> restaurada = TriFunction.uncurry(curried);
        System.out.printf("Uncurried restaurado:            $%,.2f%n", restaurada.aplicar(100_000.0, 0.19, 0.10));
    }
}
