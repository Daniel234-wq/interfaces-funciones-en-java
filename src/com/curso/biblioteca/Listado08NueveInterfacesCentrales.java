package com.curso.biblioteca;

import com.curso.modelo.Catalogo;
import com.curso.modelo.Producto;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

/**
 * Listado 8: Las nueve interfaces centrales aplicadas al catálogo.
 * Cubre: Function, BiFunction, UnaryOperator, BinaryOperator,
 * Predicate, BiPredicate, Consumer, BiConsumer, Supplier.
 */
public class Listado08NueveInterfacesCentrales {

    public static void main(String[] args) {
        System.out.println("=== Listado 8: Nueve interfaces centrales ===");
        List<Producto> catalogo = Catalogo.muestra();
        Producto mouse = catalogo.get(1);

        Function<Producto, String> etiqueta = p -> p.nombre() + " (" + p.categoria() + ")";
        BiFunction<Producto, Integer, Double> subtotal = (p, cantidad) -> p.precio() * cantidad;
        UnaryOperator<String> mayus = String::toUpperCase;
        BinaryOperator<Integer> sumar = Integer::sum;
        Predicate<Producto> barato = p -> p.precio() < 100_000;
        BiPredicate<Producto, String> esDe = (p, cat) -> p.categoria().equals(cat);
        Consumer<String> imprimir = texto -> System.out.println(" · " + texto);
        BiConsumer<String, Double> linea = (n, v) -> System.out.printf(" · %s: $%,.0f%n", n, v);
        Supplier<Producto> porDefecto = () -> new Producto("Genérico", "otros", 0, 0);

        System.out.println("Function        " + etiqueta.apply(mouse));
        System.out.println("UnaryOperator   " + mayus.apply("lambda"));
        System.out.println("BinaryOperator  " + sumar.apply(20, 22));
        System.out.println("Predicate       " + barato.test(mouse));
        System.out.println("BiPredicate     " + esDe.test(mouse, "tecnología"));
        System.out.println("Supplier        " + porDefecto.get().nombre());
        System.out.println("Consumer y BiConsumer:");
        imprimir.accept("sin valor de retorno");
        linea.accept("3 × Mouse", subtotal.apply(mouse, 3));
    }
}
