package com.curso.diseno;

import com.curso.modelo.Catalogo;

import java.util.function.Supplier;

/**
 * Listado 17: Calcular solo cuando es necesario (evaluación diferida con Supplier).
 * Demuestra cómo Supplier evita cálculos innecesarios o costosos hasta que realmente se requieren.
 */
public class Listado17EvaluacionDiferidaSupplier {
    public static int evaluaciones = 0;

    public static String reporteCostoso() {
        evaluaciones++; // simula una consulta cara
        return "inventario de " + Catalogo.muestra().size() + " productos";
    }

    public static void registrar(boolean activo, String mensaje) {
        if (activo) {
            System.out.println("[LOG] " + mensaje);
        }
    }

    public static void registrarDiferido(boolean activo, Supplier<String> mensaje) {
        if (activo) {
            System.out.println("[LOG] " + mensaje.get()); // solo aquí se calcula
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Listado 17: Evaluación diferida con Supplier ===");

        registrar(false, reporteCostoso());                  // se calcula... y se descarta
        registrarDiferido(false, Listado17EvaluacionDiferidaSupplier::reporteCostoso); // no se calcula
        registrarDiferido(true, Listado17EvaluacionDiferidaSupplier::reporteCostoso);  // se calcula porque se usa

        System.out.println("Evaluaciones: " + evaluaciones);
    }
}
