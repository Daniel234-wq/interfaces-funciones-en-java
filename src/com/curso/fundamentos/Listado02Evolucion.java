package com.curso.fundamentos;

import com.curso.modelo.Catalogo;
import com.curso.modelo.Producto;

import java.util.ArrayList;
import java.util.List;

/**
 * Listado 2: Evolución de métodos duplicados a lambdas.
 * Muestra las cuatro opciones históricas en Java para parametrizar comportamiento:
 * A) Clase con nombre (Java 1.0+)
 * B) Clase anónima (Java 1.1+)
 * C) Expresión lambda (Java 8+)
 * D) Referencia a método (Java 8+)
 */
public class Listado02Evolucion {

    // Paso 1: un contrato de UNA sola operación
    @FunctionalInterface
    public interface Criterio {
        boolean cumple(Producto p);
    }

    // Paso 2: un único método de filtrado, parametrizado por comportamiento
    public static List<String> filtrar(List<Producto> productos, Criterio criterio) {
        List<String> resultado = new ArrayList<>();
        for (Producto p : productos) {
            if (criterio.cumple(p)) {
                resultado.add(p.nombre());
            }
        }
        return resultado;
    }

    // Opción A (Java 1.0+): una clase con nombre por cada criterio
    public static class EnStock implements Criterio {
        @Override
        public boolean cumple(Producto p) {
            return p.disponible();
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Listado 2: Evolución de filtrado a lambdas ===");
        List<Producto> catalogo = Catalogo.muestra();

        // Opción A: Clase con nombre
        System.out.println("A. Clase con nombre: " + filtrar(catalogo, new EnStock()));

        // Opción B: Clase anónima
        System.out.println("B. Clase anónima:    " + filtrar(catalogo, new Criterio() {
            @Override
            public boolean cumple(Producto p) {
                return p.precio() < 100_000;
            }
        }));

        // Opción C: Expresión lambda
        System.out.println("C. Lambda:           " + filtrar(catalogo, p -> p.categoria().equals("muebles")));

        // Opción D: Referencia a método
        System.out.println("D. Referencia:       " + filtrar(catalogo, Producto::disponible));
    }
}
