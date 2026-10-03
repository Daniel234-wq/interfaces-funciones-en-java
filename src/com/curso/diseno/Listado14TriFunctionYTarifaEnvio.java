package com.curso.diseno;

import java.util.Objects;
import java.util.function.Function;

/**
 * Listado 14: Una interfaz genérica de tres parámetros y una interfaz de dominio componible.
 * Incluye:
 * - TriFunction<A, B, C, R> con composición 'luego' usando comodines PECS (? super R, ? extends V).
 * - TarifaEnvio con métodos de composición de dominio (conRecargo, conMinimo) y fábrica estática.
 */
public class Listado14TriFunctionYTarifaEnvio {

    @FunctionalInterface
    public interface TriFunction<A, B, C, R> {
        R aplicar(A a, B b, C c);

        default <V> TriFunction<A, B, C, V> luego(Function<? super R, ? extends V> despues) {
            Objects.requireNonNull(despues); // falla pronto y con mensaje claro
            return (a, b, c) -> despues.apply(aplicar(a, b, c));
        }
    }

    @FunctionalInterface
    public interface TarifaEnvio {
        double calcular(double pesoKg, int distanciaKm);

        default TarifaEnvio conRecargo(double porcentaje) {
            return (peso, km) -> calcular(peso, km) * (1 + porcentaje / 100);
        }

        default TarifaEnvio conMinimo(double minimo) {
            return (peso, km) -> Math.max(minimo, calcular(peso, km));
        }

        static TarifaEnvio porPesoYDistancia(double porKg, double porKm) {
            return (peso, km) -> peso * porKg + km * porKm;
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Listado 14: Interfaces propias y diseño de API ===");

        TriFunction<String, Integer, Double, String> factura =
                (cliente, unidades, precio) -> cliente + " debe " + unidades * precio;
        System.out.println(factura.aplicar("Ana", 3, 2.5));
        System.out.println(factura.luego(String::length).aplicar("Ana", 3, 2.5) + " caracteres");

        TarifaEnvio estandar = TarifaEnvio.porPesoYDistancia(2_000, 150);
        TarifaEnvio urgente = estandar.conRecargo(50).conMinimo(15_000);

        System.out.println("   kg    km    estándar     urgente");
        double[][] envios = { {1.5, 10}, {0.2, 5}, {8, 120} };
        for (double[] e : envios) {
            System.out.printf("%5.1f %5.0f  $%,9.0f  $%,9.0f%n",
                    e[0], e[1],
                    estandar.calcular(e[0], (int) e[1]),
                    urgente.calcular(e[0], (int) e[1]));
        }
    }
}
