package com.curso.fundamentos;

/**
 * Listado 5: El significado de 'this'.
 * Demuestra la diferencia de alcance semántico entre una clase anónima y una lambda.
 * En la clase anónima, 'this' apunta a la propia instancia anónima.
 * En la expresión lambda, 'this' apunta al objeto de la clase contenedora (enclosing instance).
 */
public class Listado05SignificadoThis {
    private final String nombre = "Main";

    public void demostrar() {
        Runnable anonima = new Runnable() {
            private final String nombre = "clase anónima"; // puede tener estado propio
            @Override
            public void run() {
                System.out.println("this en anónima → " + this.nombre);
            }
        };

        Runnable lambda = () -> System.out.println("this en lambda → " + this.nombre);

        anonima.run();
        lambda.run();
    }

    public static void main(String[] args) {
        System.out.println("=== Listado 5: Significado de this ===");
        new Listado05SignificadoThis().demostrar();
    }
}
