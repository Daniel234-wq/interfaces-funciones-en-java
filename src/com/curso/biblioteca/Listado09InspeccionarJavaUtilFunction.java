package com.curso.biblioteca;

import java.net.URI;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Listado 9: Inspeccionar java.util.function en tiempo de ejecución.
 * Recorre el módulo java.base a través del sistema de archivos jrt:/
 * y verifica las 43 interfaces que llevan @FunctionalInterface.
 */
public class Listado09InspeccionarJavaUtilFunction {

    public static void main(String[] args) throws Exception {
        System.out.println("=== Listado 9: Inspección de java.util.function ===");
        // El sistema de archivos "jrt:" expone las clases del propio JDK
        FileSystem jrt = FileSystems.getFileSystem(URI.create("jrt:/"));
        Path paquete = jrt.getPath("modules", "java.base", "java", "util", "function");

        List<String> nombres = new ArrayList<>();
        try (DirectoryStream<Path> archivos = Files.newDirectoryStream(paquete, "*.class")) {
            for (Path archivo : archivos) {
                String nombre = archivo.getFileName().toString().replace(".class", "");
                if (!nombre.contains("$")) {
                    nombres.add(nombre); // ignora clases internas
                }
            }
        }
        nombres.sort(null);

        long anotadas = 0;
        for (String nombre : nombres) {
            Class<?> tipo = Class.forName("java.util.function." + nombre);
            if (tipo.isInterface() && tipo.isAnnotationPresent(FunctionalInterface.class)) {
                anotadas++;
            }
        }

        System.out.println("Tipos en java.util.function: " + nombres.size());
        System.out.println("Interfaces con @FunctionalInterface: " + anotadas);
        for (int i = 0; i < nombres.size(); i += 3) {
            StringBuilder fila = new StringBuilder();
            for (int j = i; j < Math.min(i + 3, nombres.size()); j++) {
                fila.append(String.format("%-22s", nombres.get(j)));
            }
            System.out.println(fila.toString().stripTrailing());
        }
    }
}
