package com.curso.fundamentos;

/**
 * Listado 3: Una interfaz funcional válida con métodos adicionales.
 * Demuestra que una interfaz funcional solo requiere un único método abstracto (SAM).
 * Métodos de Object, métodos default, static y private no cuentan contra el SAM.
 */
public class Listado03Transformador {

    @FunctionalInterface
    public interface Transformador {
        // El único método abstracto (SAM)
        String aplicar(String texto);

        // Método público de Object: NO cuenta
        @Override
        boolean equals(Object otro);

        // Método default: NO cuenta
        default Transformador luego(Transformador siguiente) {
            return texto -> siguiente.aplicar(aplicar(texto));
        }

        // Método static: NO cuenta
        static Transformador identidad() {
            return texto -> texto;
        }

        // Método private: NO cuenta
        private static String recortar(String t) {
            return t.strip();
        }

        // Método static auxiliar que delega a método privado mediante referencia
        static Transformador limpiar() {
            return Transformador::recortar;
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Listado 3: Interfaz funcional con métodos adicionales ===");
        Transformador gritar = texto -> texto.toUpperCase() + "!";
        Transformador flujo = Transformador.limpiar().luego(gritar);

        System.out.println(flujo.aplicar("  hola, lambdas  "));
        System.out.println(Transformador.identidad().aplicar("sin cambios"));
    }
}
