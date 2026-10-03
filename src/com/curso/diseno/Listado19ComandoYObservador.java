package com.curso.diseno;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Listado 19: Tareas diferidas con Runnable y suscriptores con Consumer.
 * Implementación funcional limpia de los patrones de diseño:
 * - Observador: List<Consumer<T>>
 * - Comando: List<Runnable>
 */
public class Listado19ComandoYObservador {

    public static void main(String[] args) {
        System.out.println("=== Listado 19: Patrones Comando y Observador con lambdas ===");

        // Patrón Observador
        List<Consumer<String>> suscriptores = new ArrayList<>();
        suscriptores.add(evento -> System.out.println("  [correo] " + evento));
        suscriptores.add(evento -> System.out.println("  [app]    " + evento));
        Consumer<String> publicar = evento -> suscriptores.forEach(s -> s.accept(evento));

        // Patrón Comando
        List<Runnable> cola = new ArrayList<>();
        cola.add(() -> publicar.accept("Pedido 101 recibido"));
        cola.add(() -> publicar.accept("Pedido 101 despachado"));

        System.out.println("Tareas en cola: " + cola.size() + " (aún no se ejecutan)");
        cola.forEach(Runnable::run);
    }
}
