package com.curso.biblioteca;

import com.curso.modelo.Catalogo;
import com.curso.modelo.Producto;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;

/**
 * Listado 12: Encadenar efectos, elegir extremos y ordenar por varios criterios.
 * Demuestra:
 * - Consumer.andThen: encadenar efectos secundarios en orden
 * - BinaryOperator.minBy / maxBy: selección de extremos usando un Comparator
 * - Comparator: comparing, thenComparing, reverseOrder
 */
public class Listado12ComposicionConsumerYComparadores {

    public static void main(String[] args) {
        System.out.println("=== Listado 12: Consumer, BinaryOperator y Comparator ===");
        List<Producto> catalogo = Catalogo.muestra();

        List<String> bitacora = new ArrayList<>();
        Consumer<Producto> registrar = p -> bitacora.add(p.nombre());
        Consumer<Producto> alertar = p -> {
            if (p.stock() < 5) {
                System.out.println("Stock bajo: " + p.nombre());
            }
        };

        // andThen encadena consumidores ejecutándolos en secuencia
        catalogo.forEach(registrar.andThen(alertar));
        System.out.println("Registrados: " + bitacora.size());

        // BinaryOperator con minBy y maxBy
        Comparator<Producto> porPrecio = Comparator.comparingDouble(Producto::precio);
        BinaryOperator<Producto> masBarato = BinaryOperator.minBy(porPrecio);
        BinaryOperator<Producto> masCaro = BinaryOperator.maxBy(porPrecio);

        Producto min = catalogo.get(0);
        Producto max = catalogo.get(0);
        for (Producto p : catalogo) {
            min = masBarato.apply(min, p);
            max = masCaro.apply(max, p);
        }
        System.out.println("Extremos: " + min.nombre() + " / " + max.nombre());

        // Ordenamiento multinivel con Comparator
        Comparator<Producto> orden = Comparator.comparing(Producto::categoria)
                .thenComparing(Producto::precio, Comparator.reverseOrder());
        List<Producto> copia = new ArrayList<>(catalogo);
        copia.sort(orden);
        copia.forEach(p -> System.out.printf(" %-11s %s%n", p.categoria(), p.nombre()));
    }
}
