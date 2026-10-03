package com.curso.biblioteca;

import com.curso.modelo.Catalogo;
import com.curso.modelo.Producto;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Listado 11: Combinar criterios sin escribir condiciones anidadas.
 * Métodos de composición de Predicate:
 * - and(...)
 * - or(...)
 * - negate()
 * - Predicate.not(...)
 * - Predicate.isEqual(...)
 */
public class Listado11ComposicionPredicate {

    public static List<String> filtrar(List<Producto> productos, Predicate<Producto> criterio) {
        List<String> nombres = new ArrayList<>();
        for (Producto p : productos) {
            if (criterio.test(p)) {
                nombres.add(p.nombre());
            }
        }
        return nombres;
    }

    public static void main(String[] args) {
        System.out.println("=== Listado 11: Composición de Predicate ===");
        List<Producto> catalogo = Catalogo.muestra();

        Predicate<Producto> disponible = Producto::disponible;
        Predicate<Producto> tecnologia = p -> p.categoria().equals("tecnología");
        Predicate<Producto> caro = p -> p.precio() >= 500_000;

        System.out.println("and   " + filtrar(catalogo, tecnologia.and(disponible)));
        System.out.println("or    " + filtrar(catalogo, caro.or(disponible.negate())));
        System.out.println("not   " + filtrar(catalogo, Predicate.not(tecnologia)));

        Predicate<String> esMouse = Predicate.isEqual("Mouse");
        System.out.println("equal " + esMouse.test("Mouse") + " " + esMouse.test("mouse"));
    }
}
