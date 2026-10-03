package com.curso.laboratorios.lab1;

import java.text.Normalizer;
import java.util.List;
import java.util.function.Function;
import java.util.function.UnaryOperator;

/**
 * Laboratorio 1: Generador de slugs por composición.
 * Objetivo: Construir una transformación compleja componiendo funciones pequeñas.
 *
 * Pasos requeridos:
 * 1. Constantes UnaryOperator<String>: RECORTAR, MINUSCULAS, SIN_TILDES, SOLO_VALIDOS, GUIONES.
 * 2. Normalización NFD para quitar tildes con Normalizer y eliminar marcas \p{M}.
 * 3. tuberia(List<? extends Function<String, String>> pasos) iniciando en Function.identity() y encadenando con andThen.
 * 4. Prueba con las 3 cadenas esperadas y análisis del cambio de orden.
 */
public class Lab01GeneradorSlugs {

    // 1 & 2. Cinco pasos atómicos definidos como operadores unarios
    public static final UnaryOperator<String> RECORTAR = String::strip;
    public static final UnaryOperator<String> MINUSCULAS = String::toLowerCase;
    public static final UnaryOperator<String> SIN_TILDES =
            s -> Normalizer.normalize(s, Normalizer.Form.NFD).replaceAll("\\p{M}", "");
    public static final UnaryOperator<String> SOLO_VALIDOS =
            s -> s.replaceAll("[^a-z0-9\\s-]", "");
    public static final UnaryOperator<String> GUIONES =
            s -> s.strip().replaceAll("[\\s-]+", "-");

    // 3. Tubería componible basada en Function.identity() y andThen
    public static Function<String, String> tuberia(List<? extends Function<String, String>> pasos) {
        Function<String, String> resultado = Function.identity();
        for (Function<String, String> paso : pasos) {
            resultado = resultado.andThen(paso);
        }
        return resultado;
    }

    public static void main(String[] args) {
        System.out.println("=== Laboratorio 1: Generador de Slugs ===");

        Function<String, String> slug =
                tuberia(List.of(RECORTAR, MINUSCULAS, SIN_TILDES, SOLO_VALIDOS, GUIONES));

        List<String> nombresPrueba = List.of(
                " Silla Ergonómica ",
                "Audífonos Bluetooth 5.3",
                "¡Oferta! Portátil i7 -- 16GB"
        );

        for (String nombre : nombresPrueba) {
            System.out.printf("%-32s → %s%n", "\"" + nombre + "\"", slug.apply(nombre));
        }

        // 4. Demostración y análisis de cambio de orden:
        System.out.println("\n--- Análisis de Cambio de Orden de Pasos ---");

        // Experimento A: Ejecutar SOLO_VALIDOS ANTES de MINUSCULAS
        Function<String, String> ordenInvertidoA =
                tuberia(List.of(RECORTAR, SOLO_VALIDOS, MINUSCULAS, SIN_TILDES, GUIONES));
        System.out.println("Experimento A (SOLO_VALIDOS antes de MINUSCULAS):");
        System.out.println("Entrada: \"¡Oferta! Portátil i7 -- 16GB\"");
        System.out.println("Salida:  " + ordenInvertidoA.apply("¡Oferta! Portátil i7 -- 16GB"));
        System.out.println("Explicación: SOLO_VALIDOS filtra con regex [^a-z0-9\\s-]. Si no se ha ejecutado");
        System.out.println("MINUSCULAS primero, las letras mayúsculas ('O', 'P') NO encajan en [a-z] y son");
        System.out.println("ELIMINADAS incorrectamente, produciendo 'ferta-orttil-i7-16gb'.\n");

        // Experimento B: Ejecutar SOLO_VALIDOS ANTES de SIN_TILDES
        Function<String, String> ordenInvertidoB =
                tuberia(List.of(RECORTAR, MINUSCULAS, SOLO_VALIDOS, SIN_TILDES, GUIONES));
        System.out.println("Experimento B (SOLO_VALIDOS antes de SIN_TILDES):");
        System.out.println("Entrada: \" Silla Ergonómica \"");
        System.out.println("Salida:  " + ordenInvertidoB.apply(" Silla Ergonómica "));
        System.out.println("Explicación: Las letras con tilde ('ó') no son parte de [a-z0-9]. Si SOLO_VALIDOS");
        System.out.println("se ejecuta antes de quitar tildes, la letra completa 'ó' es eliminada en vez de");
        System.out.println("convertirse en 'o', produciendo 'silla-ergonmica'. Por tanto, la composición");
        System.out.println("NO es conmutativa y el orden de los pasos es crítico.");
    }
}
