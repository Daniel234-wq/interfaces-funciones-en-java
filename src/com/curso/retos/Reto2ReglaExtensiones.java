package com.curso.retos;

import com.curso.laboratorios.lab2.Regla;

import java.util.List;

/**
 * Reto 2: Extensión de Regla con operadores disyuntivos 'o' y condicionales 'cuando'.
 *
 * Casos de demostración:
 * 1. Método o(...):
 *    Un identificador fiscal puede ser NIT (empresa: 9 dígitos) O Cédula (persona: 8 a 10 dígitos).
 * 2. Método cuando(...):
 *    Si el producto es de categoría "químicos", se EXIGE que tenga número de registro sanitario.
 *    Para cualquier otra categoría, el registro sanitario es opcional (no genera error si falta).
 */
public class Reto2ReglaExtensiones {

    public record Documento(String tipo, String numero) {}

    public record ProductoInventario(String nombre, String categoria, String registroSanitario) {}

    public static void main(String[] args) {
        System.out.println("=== Reto 2: Métodos o(...) y cuando(...) en Regla ===");

        // --- DEMOSTRACIÓN 1: Regla.o(...) ---
        System.out.println("\n1. Demostración de disyunción: Regla.o(...)");
        Regla<String> esNit = Regla.exigir(
                s -> s != null && s.matches("\\d{9}"),
                "no cumple formato NIT (9 dígitos)"
        );
        Regla<String> esCedula = Regla.exigir(
                s -> s != null && s.matches("\\d{8,10}"),
                "no cumple formato Cédula (8 a 10 dígitos)"
        );

        Regla<String> documentoValido = esNit.o(esCedula);

        List<String> pruebasDoc = List.of(
                "900123456",    // Válido como NIT y Cédula
                "10203040",     // Válido como Cédula (8 dígitos)
                "abc12345",     // Inválido para ambos
                "123"           // Demasiado corto para ambos
        );

        for (String doc : pruebasDoc) {
            List<String> errores = documentoValido.validar(doc);
            System.out.printf("Doc '%s' -> %s %s%n",
                    doc,
                    errores.isEmpty() ? "VÁLIDO" : "INVÁLIDO",
                    errores);
        }

        // --- DEMOSTRACIÓN 2: Regla.cuando(...) ---
        System.out.println("\n2. Demostración de validación condicional: Regla.cuando(...)");
        Regla<ProductoInventario> exigirSanitarioParaQuimicos =
                Regla.<ProductoInventario>exigir(
                        p -> p.registroSanitario() != null && !p.registroSanitario().isBlank(),
                        "químicos: registro sanitario obligatorio"
                ).cuando(p -> "químicos".equalsIgnoreCase(p.categoria()));

        List<ProductoInventario> productos = List.of(
                new ProductoInventario("Jabón industrial", "químicos", "INVIMA-2024-Q12"), // Cumple condición y regla
                new ProductoInventario("Ácido clorhídrico", "químicos", ""),               // Cumple condición, falla regla
                new ProductoInventario("Mesa de oficina", "muebles", "")                  // Condición falsa: regla omitida
        );

        for (ProductoInventario prod : productos) {
            List<String> errores = exigirSanitarioParaQuimicos.validar(prod);
            System.out.printf("Producto '%s' (%s) -> %s %s%n",
                    prod.nombre(),
                    prod.categoria(),
                    errores.isEmpty() ? "VÁLIDO" : "INVÁLIDO",
                    errores);
        }
    }
}
