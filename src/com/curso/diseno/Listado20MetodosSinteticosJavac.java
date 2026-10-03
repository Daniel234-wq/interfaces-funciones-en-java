package com.curso.diseno;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Listado 20: Los métodos sintéticos que genera javac.
 * Demuestra mediante reflexión cómo javac genera métodos privados sintéticos
 * para los cuerpos de las lambdas y cómo se invocan vía invokedynamic / LambdaMetafactory
 * sin crear clases anónimas tradicionales.
 */
public class Listado20MetodosSinteticosJavac {

    public static void main(String[] args) {
        System.out.println("=== Listado 20: Métodos sintéticos e invokedynamic ===");

        Supplier<String> saludo = () -> "hola";
        Function<Integer, Integer> doble = x -> x * 2;
        Supplier<String> vacio = String::new; // referencia: no genera método sintético nuevo

        List<String> sinteticos = new ArrayList<>();
        for (Method m : Listado20MetodosSinteticosJavac.class.getDeclaredMethods()) {
            if (m.isSynthetic()) {
                sinteticos.add(m.getName());
            }
        }
        Collections.sort(sinteticos);

        System.out.println("Métodos sintéticos en la clase: " + sinteticos);
        System.out.println("¿Clase anónima? " + saludo.getClass().isAnonymousClass());
        System.out.println(saludo.get() + " " + doble.apply(21) + " [" + vacio.get() + "]");
    }
}
