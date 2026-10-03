package com.curso.biblioteca;

import com.curso.modelo.Catalogo;
import com.curso.modelo.Producto;

import java.util.List;
import java.util.function.IntBinaryOperator;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntUnaryOperator;
import java.util.function.ObjIntConsumer;
import java.util.function.ToDoubleFunction;

/**
 * Listado 13: Interfaces primitivas en acción.
 * Demuestra el uso de especializaciones primitivas para evitar costos de autoboxing:
 * ToDoubleFunction, IntPredicate, IntUnaryOperator, IntBinaryOperator, ObjIntConsumer, IntFunction.
 */
public class Listado13EspecializacionesPrimitivas {

    public static void main(String[] args) {
        System.out.println("=== Listado 13: Interfaces primitivas ===");
        List<Producto> catalogo = Catalogo.muestra();

        ToDoubleFunction<Producto> valorEnInventario = p -> p.precio() * p.stock();
        IntPredicate esPar = n -> n % 2 == 0;
        IntUnaryOperator cuadrado = n -> n * n;
        IntBinaryOperator maximo = Math::max;
        ObjIntConsumer<String> repetir = (texto, veces) -> System.out.println(texto.repeat(veces));
        IntFunction<String> barra = "█"::repeat;

        double total = 0;
        for (Producto p : catalogo) {
            total += valorEnInventario.applyAsDouble(p);
        }
        System.out.printf("Valor del inventario: $%,.0f%n", total);

        System.out.println(esPar.test(42) + " " + cuadrado.applyAsInt(12) + " " + maximo.applyAsInt(7, 3));
        System.out.println(cuadrado.andThen(n -> n + 1).applyAsInt(5)); // también componen
        repetir.accept("=-", 8);

        for (Producto p : catalogo) {
            System.out.printf("%-17s %s%n", p.nombre(), barra.apply(p.stock() / 10));
        }
    }
}
