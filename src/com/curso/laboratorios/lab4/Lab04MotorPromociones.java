package com.curso.laboratorios.lab4;

import com.curso.modelo.Catalogo;
import com.curso.modelo.Producto;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Supplier;
import java.util.function.ToDoubleBiFunction;

/**
 * Laboratorio 4: Proyecto integrador - Motor de promociones del catálogo.
 *
 * Implementa:
 * 1. Modelo Promocion(nombre, aplicaA, ajuste).
 * 2. Fábricas de ajustes funcionales: porcentaje(pct) y montoFijo(monto).
 * 3. Definición de 3 promociones de negocio.
 * 4. Supplier<Promocion> para "Sin promoción" con DoubleUnaryOperator.identity().
 * 5. Selección de mejor promoción con BinaryOperator.minBy().
 * 6. BiConsumer para impresión y ToDoubleBiFunction para acumulado de ahorro.
 */
public class Lab04MotorPromociones {

    public static DoubleUnaryOperator porcentaje(double pct) {
        return precio -> precio * (1 - pct / 100.0);
    }

    public static DoubleUnaryOperator montoFijo(double monto) {
        return precio -> Math.max(0, precio - monto);
    }

    public static void main(String[] args) {
        System.out.println("=== Laboratorio 4: Motor de Promociones del Catálogo ===");

        // Configuración regional es-CO para formato numérico con punto de miles como en la guía
        Locale localeEsCo = Locale.forLanguageTag("es-CO");

        // 3. Catálogo de promociones configurables
        List<Promocion> promociones = List.of(
                new Promocion("Tecno 10 %", p -> p.categoria().equals("tecnología"), porcentaje(10)),
                new Promocion("Bono $50.000", p -> p.precio() >= 200_000, montoFijo(50_000)),
                new Promocion("Liquidación", p -> p.stock() > 100, porcentaje(30))
        );

        // 4. Opción por defecto (sin descuento)
        Supplier<Promocion> sinPromocion =
                () -> new Promocion("Sin promoción", p -> true, DoubleUnaryOperator.identity());

        // 6. Impresión con BiConsumer y cálculo de ahorro con ToDoubleBiFunction
        BiConsumer<Producto, Promocion> imprimir = (p, promo) -> System.out.printf(
                localeEsCo,
                "%-17s %-14s $%,11.0f → $%,11.0f%n",
                p.nombre(),
                promo.nombre(),
                p.precio(),
                promo.precioCon(p)
        );

        ToDoubleBiFunction<Producto, Promocion> ahorro =
                (p, promo) -> p.precio() - promo.precioCon(p);

        double ahorroTotal = 0;

        // 5. Reducción y elección de la promoción más conveniente
        for (Producto p : Catalogo.muestra()) {
            if (!p.disponible()) {
                continue; // omite productos sin stock disponible
            }

            BinaryOperator<Promocion> masConveniente =
                    BinaryOperator.minBy(Comparator.comparingDouble(promo -> promo.precioCon(p)));

            Promocion mejor = sinPromocion.get();
            for (Promocion promo : promociones) {
                mejor = masConveniente.apply(mejor, promo);
            }

            imprimir.accept(p, mejor);
            ahorroTotal += ahorro.applyAsDouble(p, mejor);
        }

        System.out.printf(localeEsCo, "Ahorro total para el cliente: $%,.0f%n", ahorroTotal);
    }
}
