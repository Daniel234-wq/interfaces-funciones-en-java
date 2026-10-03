package com.curso.fundamentos;

import java.util.function.IntSupplier;
import java.util.function.Supplier;

/**
 * Listado 7: Captura de variables locales y de campos (closures).
 * Demuestra la regla de 'efectivamente final' para variables locales
 * y cómo los campos de instancia sí pueden modificarse porque se captura 'this'.
 */
public class Listado07CapturaVariables {
    private int contador = 0; // campo de instancia

    public static Supplier<String> crearSaludo(String nombre) {
        String saludo = "Hola, " + nombre; // efectivamente final
        return () -> saludo + "!";         // la lambda captura su valor
    }

    public IntSupplier crearContador() {
        return () -> ++contador; // un campo SÍ puede cambiar: se captura this
    }

    public static void main(String[] args) {
        System.out.println("=== Listado 7: Captura de variables y closures ===");

        Supplier<String> s = crearSaludo("Ana");
        System.out.println(s.get()); // crearSaludo ya terminó, pero la closure conserva el valor

        IntSupplier c = new Listado07CapturaVariables().crearContador();
        c.getAsInt();
        c.getAsInt();
        System.out.println("Contador: " + c.getAsInt());
    }
}
