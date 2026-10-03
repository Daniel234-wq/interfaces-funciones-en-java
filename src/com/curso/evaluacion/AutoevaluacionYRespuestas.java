package com.curso.evaluacion;

import java.util.List;

/**
 * Solucionario exhaustivo de los Puntos de Control, Autoevaluación y Reflexiones
 * planteados a lo largo de la Guía Técnica de Interfaces Funcionales en Java.
 */
public class AutoevaluacionYRespuestas {

    public record Pregunta(int numero, String enunciado, List<String> opciones, String respuestaCorrecta, String explicacion) {}

    public static void main(String[] args) {
        System.out.println("================================================================================");
        System.out.println("   SOLUCIONARIO OFICIAL: PUNTOS DE CONTROL, AUTOEVALUACIÓN Y REFLEXIONES");
        System.out.println("================================================================================\n");

        mostrarPuntosDeControl();
        mostrarAutoevaluacion();
        mostrarReflexionesLaboratorios();
    }

    private static void mostrarPuntosDeControl() {
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("1. PUNTOS DE CONTROL (Página 6)");
        System.out.println("--------------------------------------------------------------------------------");

        System.out.println("Pregunta 1: Una interfaz tiene un método abstracto, dos default y declara String toString(). ¿Es funcional?");
        System.out.println("Respuesta: SÍ.");
        System.out.println("Explicación: Los métodos default tienen implementación, por lo que no son abstractos. Por su parte,");
        System.out.println("'toString()' es un método público de java.lang.Object; la especificación de Java (JLS §9.8) establece");
        System.out.println("que los métodos abstractos que sobreescriben métodos públicos de Object NO cuentan para el cálculo");
        System.out.println("del SAM (Single Abstract Method) porque cualquier clase que implemente la interfaz ya los hereda");
        System.out.println("implementados. Al quedar exactamente un único método abstracto propio, la interfaz es funcional.\n");

        System.out.println("Pregunta 2: ¿Qué ventaja concreta aporta @FunctionalInterface si la interfaz ya es funcional sin ella?");
        System.out.println("Respuesta: Validación temprana en tiempo de compilación y documentación de contrato.");
        System.out.println("Explicación: La anotación hace que el compilador de Java emita un error directo e inmediato si alguien");
        System.out.println("añade un segundo método abstracto o elimina el único existente. Además, comunica de forma explícita");
        System.out.println("a los consumidores del código la intención de diseño de que dicha interfaz está pensada para lambdas.\n");

        System.out.println("Pregunta 3: ¿Por qué una clase abstracta con un solo método abstracto no puede recibir una lambda?");
        System.out.println("Respuesta: Por restricción de la especificación del lenguaje (JLS §15.27).");
        System.out.println("Explicación: Java limita los 'tipos destino' (target types) de lambdas exclusivamente a interfaces.");
        System.out.println("Una clase abstracta puede declarar constructores con estado interno, inicializadores de instancia y");
        System.out.println("jerarquía de herencia única. Las lambdas de Java se compilan mediante invokedynamic y LambdaMetafactory");
        System.out.println("para crear implementaciones ligeras y sin estado sobre interfaces, no sobre clases abstractas.\n");
    }

    private static void mostrarAutoevaluacion() {
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("2. AUTOEVALUACIÓN DE 10 PREGUNTAS (Páginas 32 - 33)");
        System.out.println("--------------------------------------------------------------------------------");

        List<Pregunta> preguntas = List.of(
                new Pregunta(1, "¿Cuál de estas es una interfaz funcional?",
                        List.of("a) interface A { void x(); void y(); }",
                                "b) interface B { void x(); boolean equals(Object o); }",
                                "c) interface C { default void x() { } }",
                                "d) abstract class D { abstract void x(); }"),
                        "b", "equals(Object) es método público de Object y no cuenta. Solo queda void x() como único SAM."),

                new Pregunta(2, "¿Qué ocurre con: var f = x -> x + 1; ?",
                        List.of("a) Compila como Function",
                                "b) Error: la lambda necesita tipo destino",
                                "c) Compila como IntUnaryOperator",
                                "d) Error de sintaxis en la flecha"),
                        "b", "var infiere el tipo a partir de la expresión, pero una expresión lambda no tiene tipo propio; necesita un tipo destino explícito del contexto."),

                new Pregunta(3, "Dentro del cuerpo de una lambda, this se refiere a...",
                        List.of("a) La propia lambda",
                                "b) La instancia que la contiene",
                                "c) null",
                                "d) La interfaz funcional"),
                        "b", "Las lambdas comparten el ámbito léxico de su entorno contenedor (enclosing instance); no crean un nuevo 'this' como sí lo hacen las clases anónimas."),

                new Pregunta(4, "String::length usada como Function<String, Integer> es una referencia a...",
                        List.of("a) Método estático",
                                "b) Método de un objeto concreto",
                                "c) Método de instancia de un objeto arbitrario",
                                "d) Constructor"),
                        "c", "Es una referencia no ligada (tipo 3): el primer parámetro de Function (el objeto String) es el receptor sobre el que se invoca length()."),

                new Pregunta(5, "¿Qué calcula f.andThen(g).apply(x) ?",
                        List.of("a) f(g(x))",
                                "b) g(f(x))",
                                "c) f(x) + g(x)",
                                "d) Depende de los tipos"),
                        "b", "andThen se lee en orden secuencial: primero aplica f sobre x, y al resultado le aplica g."),

                new Pregunta(6, "¿Qué interfaz convierte un Producto en double sin autoboxing?",
                        List.of("a) Function<Producto, Double>",
                                "b) ToDoubleFunction<Producto>",
                                "c) DoubleFunction<Producto>",
                                "d) DoubleSupplier"),
                        "b", "ToDoubleFunction<T> tiene como método 'double applyAsDouble(T value)', retornando directamente el tipo primitivo double."),

                new Pregunta(7, "Una lambda hace contador++ sobre una variable local. ¿Qué pasa?",
                        List.of("a) Compila y funciona",
                                "b) Error: debe ser efectivamente final",
                                "c) Excepción en ejecución",
                                "d) Solo compila si es static"),
                        "b", "Cualquier variable local capturada por una lambda debe ser final o efectivamente final (no reasignable)."),

                new Pregunta(8, "Function<String, URI> f = s -> new URI(s);",
                        List.of("a) Compila sin problemas",
                                "b) Error: excepción verificada no reportada",
                                "c) Compila con una advertencia",
                                "d) Falla solo en ejecución"),
                        "b", "new URI(...) declara throws URISyntaxException (excepción verificada), la cual no está permitida por el descriptor de Function.apply()."),

                new Pregunta(9, "¿Para qué es especialmente útil Supplier<T> ?",
                        List.of("a) Evaluación diferida de valores",
                                "b) Filtrar colecciones",
                                "c) Recibir dos parámetros",
                                "d) Comparar objetos"),
                        "a", "Permite posponer o condicionar la ejecución de cálculos costosos (evaluación perezosa / lazy evaluation)."),

                new Pregunta(10, "Existen ejecutar(Supplier<String>) y ejecutar(Callable<String>). ¿Qué pasa con ejecutar(() -> \"x\") ?",
                        List.of("a) Llama a la versión Supplier",
                                "b) Llama a la versión Callable",
                                "c) Error: llamada ambigua",
                                "d) Error en ejecución"),
                        "c", "Ambas sobrecargas esperan una interfaz funcional sin parámetros que retorna String. El compilador no puede decidir el tipo destino.")
        );

        for (Pregunta p : preguntas) {
            System.out.printf("Pregunta %d: %s%n", p.numero(), p.enunciado());
            for (String opt : p.opciones()) {
                System.out.println("   " + opt);
            }
            System.out.printf(">> Respuesta Correcta: [%s]%n", p.respuestaCorrecta());
            System.out.printf("   Justificación: %s%n%n", p.explicacion());
        }
    }

    private static void mostrarReflexionesLaboratorios() {
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("3. REFLEXIONES DE LOS LABORATORIOS");
        System.out.println("--------------------------------------------------------------------------------");

        System.out.println("Laboratorio 1 - Paso 4: ¿Por qué cambia el resultado al invertir el orden de los pasos?");
        System.out.println("Explicación: La composición de funciones NO es conmutativa (f.andThen(g) != g.andThen(f)).");
        System.out.println("Si SOLO_VALIDOS se ejecuta antes de MINUSCULAS, la expresión regular borra todas las");
        System.out.println("letras mayúsculas porque no coinciden con [a-z]. Si se ejecuta antes de SIN_TILDES,");
        System.out.println("elimina las vocales acentuadas por no ser ASCII básico.\n");

        System.out.println("Laboratorio 3 - Paso 5: Agregar el operador '%' vs switch");
        System.out.println("Explicación: Con el mapa de estrategias de orden superior, agregar '%' respeta el");
        System.out.println("principio Open/Closed (OCP): basta registrar 'OPERACIONES.put(\"%\", (a, b) -> a % b)'");
        System.out.println("sin tocar ni una sola línea de 'evaluar()'. Con 'switch', es obligatorio modificar el");
        System.out.println("código interno del método existente, recompilar y arriesgar regresiones.");
        System.out.println("--------------------------------------------------------------------------------\n");
    }
}
