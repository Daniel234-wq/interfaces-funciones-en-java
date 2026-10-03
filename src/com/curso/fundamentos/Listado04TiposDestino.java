package com.curso.fundamentos;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Listado 4: Una lambda, varios tipos destino.
 * Demuestra cómo una lambda por sí sola no tiene tipo propio, sino que el compilador
 * lo infiere del tipo destino (target type) en cada contexto.
 */
public class Listado04TiposDestino {

    @FunctionalInterface
    public interface Verificador {
        boolean verificar(String s);
    }

    public static void main(String[] args) throws Exception {
        System.out.println("=== Listado 4: Tipos destino ===");

        // Mismo lambda s -> s.isEmpty(), tres tipos destino distintos:
        Predicate<String> p = s -> s.isEmpty();
        Function<String, Boolean> f = s -> s.isEmpty();
        Verificador v = s -> s.isEmpty();
        System.out.println(p.test("") + " " + f.apply("x") + " " + v.verificar(""));

        // Contextos que aportan tipo destino: asignación, retorno, Callable y Supplier
        Callable<String> c = () -> "desde Callable";
        Supplier<String> su = () -> "desde Supplier";
        System.out.println(c.call() + " | " + su.get());

        // Cast como tipo destino explícito
        Object o = (Runnable) () -> System.out.println("cast como tipo destino");
        ((Runnable) o).run();

        // Una expresión que devuelve valor (boolean de List.add) también sirve donde se espera void (Consumer)
        List<String> registro = new ArrayList<>();
        Consumer<String> guardar = s -> registro.add(s); // el boolean se descarta
        Predicate<String> guardarYConfirmar = s -> registro.add(s); // se conserva el boolean
        guardar.accept("a");
        System.out.println(guardarYConfirmar.test("b") + " " + registro);
    }
}
