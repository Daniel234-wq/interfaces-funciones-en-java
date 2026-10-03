package com.curso.retos;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

/**
 * Reto 4: Patrón Reintentar con Supplier e interfaces funcionales.
 * Objetivo: Implementar un Reintentar.de(Supplier<T>, int intentos) que vuelva a
 * ejecutar el proveedor si lanza una excepción.
 */
public class Reto4ReintentosSupplier {

    /**
     * Excepción lanzada cuando se agotan todos los intentos configurados.
     */
    public static class ReintentosAgotadosException extends RuntimeException {
        public ReintentosAgotadosException(String mensaje, Throwable causa) {
            super(mensaje, causa);
        }
    }

    public static class Reintentar {
        private Reintentar() {}

        /**
         * Ejecuta la acción provista por el Supplier. Si falla lanzando una RuntimeException,
         * reintenta hasta el número máximo de intentos indicado.
         *
         * @param proveedor Acción funcional proveedora del valor
         * @param intentos  Cantidad máxima de intentos permitidos (>= 1)
         * @param <T>       Tipo del resultado producido
         * @return El valor producido por el proveedor si tiene éxito
         * @throws IllegalArgumentException si intentos < 1
         * @throws ReintentosAgotadosException si se agotan todos los intentos sin éxito
         */
        public static <T> T de(Supplier<T> proveedor, int intentos) {
            Objects.requireNonNull(proveedor, "El proveedor no puede ser nulo");
            if (intentos < 1) {
                throw new IllegalArgumentException("La cantidad de intentos debe ser al menos 1, recibido: " + intentos);
            }

            List<Throwable> fallosPrevios = new ArrayList<>();
            for (int i = 1; i <= intentos; i++) {
                try {
                    return proveedor.get();
                } catch (RuntimeException e) {
                    fallosPrevios.add(e);
                    if (i == intentos) {
                        ReintentosAgotadosException finalEx = new ReintentosAgotadosException(
                                "Operación falló tras " + intentos + " intentos. Último error: " + e.getMessage(),
                                e
                        );
                        // Añade todos los fallos anteriores como excepciones suprimidas para trazabilidad total
                        for (Throwable previo : fallosPrevios) {
                            finalEx.addSuppressed(previo);
                        }
                        throw finalEx;
                    }
                }
            }
            throw new IllegalStateException("Estado inalcanzable");
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Reto 4: Reintentar.de(Supplier<T>, int intentos) ===");

        // Caso 1: Éxito en el primer intento
        System.out.println("\nCaso 1: Proveedor sin fallos");
        String res1 = Reintentar.de(() -> "Respuesta exitosa inmediata", 3);
        System.out.println("Resultado: " + res1);

        // Caso 2: Falla 2 veces (error transitorio de red) y tiene éxito en el intento 3
        System.out.println("\nCaso 2: Fallos transitorios y recuperación en intento 3");
        AtomicInteger llamadas = new AtomicInteger(0);
        Supplier<String> servicioInestable = () -> {
            int intentoActual = llamadas.incrementAndGet();
            System.out.println("  -> Intentando llamada #" + intentoActual);
            if (intentoActual < 3) {
                throw new IllegalStateException("Timeout transitorio en intento " + intentoActual);
            }
            return "Conexión establecida con éxito en intento #" + intentoActual;
        };

        String res2 = Reintentar.de(servicioInestable, 4);
        System.out.println("Resultado: " + res2);

        // Caso 3: Falla todos los intentos (se agota)
        System.out.println("\nCaso 3: Fallos permanentes (se agotan los 3 intentos)");
        AtomicInteger contadorAgotado = new AtomicInteger(0);
        Supplier<String> servicioCaido = () -> {
            int intento = contadorAgotado.incrementAndGet();
            System.out.println("  -> Intentando llamada #" + intento);
            throw new RuntimeException("Servicio 503 No Disponible");
        };

        try {
            Reintentar.de(servicioCaido, 3);
        } catch (ReintentosAgotadosException e) {
            System.out.println("Excepción capturada como se esperaba:");
            System.out.println("Mensaje: " + e.getMessage());
            System.out.println("Cantidad de fallos suprimidos registrados: " + e.getSuppressed().length);
        }
    }
}
