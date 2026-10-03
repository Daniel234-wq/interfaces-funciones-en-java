package com.curso.diseno;

import java.net.URI;
import java.util.List;
import java.util.function.Function;

/**
 * Listado 15: Interfaz con throws genérico y adaptador a Function.
 * Demuestra cómo tratar excepciones verificadas en lambdas sin perder la causa
 * original del error mediante una interfaz funcional intermedia y un adaptador.
 */
public class Listado15ExcepcionesVerificadas {

    @FunctionalInterface
    public interface FuncionConExcepcion<T, R, E extends Exception> {
        R aplicar(T t) throws E; // el tipo de excepción también es genérico
    }

    public static <T, R> Function<T, R> sinVerificar(FuncionConExcepcion<T, R, ?> funcion) {
        return entrada -> {
            try {
                return funcion.aplicar(entrada);
            } catch (RuntimeException e) {
                throw e; // las no verificadas pasan intactas
            } catch (Exception e) {
                // Conserva la causa y añade contexto descriptivo
                throw new IllegalArgumentException("'" + entrada + "' → " + e.getMessage(), e);
            }
        };
    }

    public static void main(String[] args) {
        System.out.println("=== Listado 15: Manejo de excepciones verificadas ===");
        Function<String, URI> aUri = sinVerificar(URI::new); // ¡ahora sí es una Function!

        for (String texto : List.of("https://tienda.co/productos?id=42", "https://tienda .co")) {
            try {
                System.out.println("OK   host = " + aUri.apply(texto).getHost());
            } catch (IllegalArgumentException e) {
                System.out.println("ERROR " + e.getMessage());
            }
        }
    }
}
