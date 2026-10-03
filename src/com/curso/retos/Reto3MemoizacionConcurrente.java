package com.curso.retos;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

/**
 * Reto 3: Memoización segura para hilos con ConcurrentHashMap y análisis de funciones recursivas.
 *
 * Objetivo:
 * 1. Implementar 'memorizar' thread-safe con ConcurrentHashMap.
 * 2. Demostrar su comportamiento concurrente y atómico.
 * 3. Analizar y explicar qué sucede cuando la función a memorizar es recursiva:
 *    - Causa: ConcurrentHashMap.computeIfAbsent bloquea el 'bin' o bucket de la tabla hash.
 *    - En Java 9+: Si la función recursiva intenta invocar computeIfAbsent sobre el mismo mapa,
 *      la JVM lanza java.lang.IllegalStateException: Recursive update (o causa Deadlock si
 *      múltiples hilos se entrelazan).
 * 4. Presentar la solución adecuada para funciones recursivas.
 */
public class Reto3MemoizacionConcurrente {

    /**
     * Memoizador genérico seguro para hilos usando ConcurrentHashMap.
     */
    public static <T, R> Function<T, R> memorizarConcurrente(Function<T, R> funcion) {
        ConcurrentMap<T, R> cache = new ConcurrentHashMap<>();
        return entrada -> cache.computeIfAbsent(entrada, funcion);
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Reto 3: Memoización Concurrente y Análisis de Recursión ===");

        // --- 1. Prueba de Concurrencia y Thread-Safety ---
        System.out.println("\n1. Demostración Concurrente (10 hilos simultáneos solicitando la misma clave):");
        AtomicInteger calculosReales = new AtomicInteger(0);

        Function<String, String> operacionCostosa = ciudad -> {
            calculosReales.incrementAndGet();
            try {
                Thread.sleep(50); // Simula latencia de red o base de datos
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return "DATOS_" + ciudad.toUpperCase();
        };

        Function<String, String> cacheada = memorizarConcurrente(operacionCostosa);

        int hilos = 10;
        ExecutorService pool = Executors.newFixedThreadPool(hilos);
        CountDownLatch disparo = new CountDownLatch(1);
        CountDownLatch fin = new CountDownLatch(hilos);

        for (int i = 0; i < hilos; i++) {
            pool.submit(() -> {
                try {
                    disparo.await(); // Todos los hilos esperan aquí para dispararse exactamente a la vez
                    cacheada.apply("Medellín");
                } catch (InterruptedException ignored) {
                } finally {
                    fin.countDown();
                }
            });
        }

        disparo.countDown(); // Señal de inicio concurrente
        fin.await();
        pool.shutdown();

        System.out.println("Hilos que consultaron: " + hilos);
        System.out.println("Cálculos reales ejecutados: " + calculosReales.get() + " (¡Exactamente 1 cálculo atómico!)");

        // --- 2. Análisis del problema con Funciones Recursivas ---
        System.out.println("\n2. ¿Qué pasa si la función es recursiva con ConcurrentHashMap?");
        System.out.println("Explicación técnica:");
        System.out.println("`ConcurrentHashMap.computeIfAbsent` bloquea el bucket de la clave durante el cálculo.");
        System.out.println("Si la función dentro de computeIfAbsent se llama a sí misma recursivamente y vuelve");
        System.out.println("a invocar computeIfAbsent sobre el mismo mapa:");
        System.out.println("  a) En Java 9+ detecta la reentrancia en el mismo bin y lanza IllegalStateException: Recursive update.");
        System.out.println("  b) Si se distribuye entre bins y varios hilos participan, genera un DEADLOCK circular irreversible.");

        // Demostración práctica controlada de la excepción:
        ConcurrentMap<String, String> mapaReentrante = new ConcurrentHashMap<>();
        try {
            System.out.println("Intentando reentrancia en computeIfAbsent con la misma clave...");
            mapaReentrante.computeIfAbsent("clave", k -> mapaReentrante.computeIfAbsent("clave", k2 -> "valor"));
        } catch (IllegalStateException e) {
            System.out.println("  -> Excepción capturada con éxito: " + e.getClass().getName() + ": " + e.getMessage());
        }

        // --- 3. Solución adecuada para Recursión ---
        System.out.println("\n3. Solución correcta para memoización recursiva segura:");
        System.out.println("Se comprueba primero la presencia con cache.get(clave); si falta, se calcula fuera");
        System.out.println("del lock de computeIfAbsent y se inserta con putIfAbsent:");

        ConcurrentMap<Integer, Long> cacheSegura = new ConcurrentHashMap<>();
        Function<Integer, Long>[] fibSeguro = new Function[1];
        fibSeguro[0] = n -> {
            if (n <= 1) return (long) n;
            Long cached = cacheSegura.get(n);
            if (cached != null) return cached;
            long val = fibSeguro[0].apply(n - 1) + fibSeguro[0].apply(n - 2);
            cacheSegura.putIfAbsent(n, val);
            return val;
        };

        long res = fibSeguro[0].apply(20);
        System.out.println("Fibonacci(20) calculado de forma segura y memoizada = " + res);
    }
}
