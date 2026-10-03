package com.curso.laboratorios.lab3;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.DoubleBinaryOperator;

/**
 * Laboratorio 3: Calculadora con estrategias.
 * Reemplaza estructuras monolíticas 'switch' por un mapa de estrategias funcionales
 * (DoubleBinaryOperator).
 *
 * Incluye:
 * 1. Map<String, DoubleBinaryOperator> con LinkedHashMap.
 * 2. Operador '/' con validación de división por cero (ArithmeticException).
 * 3. Método evaluar(String expresion) con validación de tokens y operador.
 * 4. Prueba con las seis expresiones esperadas atrapando excepciones.
 * 5. Reflexión y demostración de extensibilidad OCP al agregar el operador '%'.
 */
public class Lab03CalculadoraEstrategias {

    public static final Map<String, DoubleBinaryOperator> OPERACIONES = new LinkedHashMap<>();

    static {
        OPERACIONES.put("+", Double::sum);
        OPERACIONES.put("-", (a, b) -> a - b);
        OPERACIONES.put("*", (a, b) -> a * b);
        OPERACIONES.put("/", (a, b) -> {
            if (b == 0) {
                throw new ArithmeticException("división por cero");
            }
            return a / b;
        });
        OPERACIONES.put("^", Math::pow);
        OPERACIONES.put("max", Math::max);
    }

    public static double evaluar(String expresion) {
        String[] partes = expresion.strip().split("\\s+");
        if (partes.length != 3) {
            throw new IllegalArgumentException("formato esperado: a op b");
        }
        DoubleBinaryOperator operacion = OPERACIONES.get(partes[1]);
        if (operacion == null) {
            throw new IllegalArgumentException("operador desconocido: " + partes[1]);
        }
        return operacion.applyAsDouble(Double.parseDouble(partes[0]), Double.parseDouble(partes[2]));
    }

    public static void registrarOperacion(String simbolo, DoubleBinaryOperator operador) {
        OPERACIONES.put(simbolo, operador);
    }

    public static void main(String[] args) {
        System.out.println("=== Laboratorio 3: Calculadora con Estrategias ===");

        List<String> expresiones = List.of(
                "12 + 30",
                "2 ^ 10",
                "7 / 2",
                "9 max 4",
                "5 / 0",
                "3 % 2"
        );

        for (String e : expresiones) {
            try {
                System.out.printf("%-8s = %s%n", e, evaluar(e));
            } catch (RuntimeException ex) {
                System.out.printf("%-8s → error: %s%n", e, ex.getMessage());
            }
        }

        System.out.println("Operadores disponibles: " + OPERACIONES.keySet());

        // 5. Reflexión comparativa: Extensión con operador '%'
        System.out.println("\n--- Reflexión Paso 5: Agregar '%' vs switch ---");
        System.out.println("Para agregar el operador '%' con el mapa de estrategias:");
        System.out.println("Basta con invocar: OPERACIONES.put(\"%\", (a, b) -> a % b);");
        System.out.println("El método 'evaluar' NO se modifica en absoluto (Principio Open/Closed - OCP).");
        System.out.println("En cambio, con un 'switch':");
        System.out.println("1. Habría que modificar el código fuente dentro del método switch.");
        System.out.println("2. Recompilar todo el componente.");
        System.out.println("3. No se podrían registrar nuevos operadores dinámicamente en tiempo de ejecución.");

        // Demostración dinámica:
        registrarOperacion("%", (a, b) -> {
            if (b == 0) throw new ArithmeticException("división por cero");
            return a % b;
        });
        System.out.println("\nRegistrando dinámicamente '%'...");
        System.out.printf("%-8s = %s%n", "3 % 2", evaluar("3 % 2"));
        System.out.println("Operadores ahora disponibles: " + OPERACIONES.keySet());
    }
}
