package com.curso.fundamentos;

import com.curso.modelo.Catalogo;
import com.curso.modelo.Producto;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Supplier;

/**
 * Listado 6: Los cuatro tipos de referencia a métodos.
 * 1. Método estático: Clase::estatico
 * 2. Método de instancia de un objeto concreto: objeto::metodo
 * 3. Método de instancia de un objeto arbitrario: Clase::metodo (el 1er parámetro es el receptor)
 * 4. Constructor: Clase::new
 */
public class Listado06ReferenciasMetodos {

    public static double conIva(double precio) {
        return Math.round(precio * 1.19);
    }

    public static void main(String[] args) {
        System.out.println("=== Listado 6: Cuatro tipos de referencias a métodos ===");
        Producto portatil = Catalogo.muestra().get(0);

        // 1. Método estático
        DoubleUnaryOperator iva = Listado06ReferenciasMetodos::conIva;
        System.out.println("1 · " + iva.applyAsDouble(100_000));

        // 2. Método de un objeto concreto (bound reference)
        String prefijo = "SKU-";
        Function<String, String> etiquetar = prefijo::concat;
        System.out.println("2 · " + etiquetar.apply("0042"));

        // 3. Método de instancia de un objeto arbitrario (unbound reference)
        Function<Producto, String> nombre = Producto::nombre;
        BiFunction<String, String, Boolean> empieza = String::startsWith;
        System.out.println("3 · " + nombre.apply(portatil) + " " + empieza.apply("Java", "Ja"));

        // 4. Constructor
        Supplier<List<String>> nuevaLista = ArrayList::new;
        Function<String, StringBuilder> constructor = StringBuilder::new;
        IntFunction<int[]> arreglo = int[]::new; // también arreglos

        List<String> lista = nuevaLista.get();
        lista.add("ok");
        System.out.println("4 · " + lista + " " + constructor.apply("abc").reverse()
                + " " + arreglo.apply(3).length);
    }
}
