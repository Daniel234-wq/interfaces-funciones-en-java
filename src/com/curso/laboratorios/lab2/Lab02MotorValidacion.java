package com.curso.laboratorios.lab2;

import java.util.List;

/**
 * Laboratorio 2: Motor de validación componible.
 * Ejecuta la verificación de registros con la regla combinada:
 * usuarioValido.y(correoValido).y(edadValida)
 */
public class Lab02MotorValidacion {

    public static void main(String[] args) {
        System.out.println("=== Laboratorio 2: Motor de Validación Componible ===");

        // Reglas de usuario sobre String
        Regla<String> usuarioTexto = Regla.<String>exigir(s -> !s.isBlank(), "usuario: obligatorio")
                .y(Regla.exigir(s -> s.length() >= 4, "usuario: mínimo 4 caracteres"));

        // Proyección sobre el campo del registro
        Regla<Registro> usuarioValido = Regla.campo(Registro::usuario, usuarioTexto);

        // Regla de correo electrónico
        Regla<Registro> correoValido = Regla.exigir(
                r -> r.correo().matches("[^@\\s]+@[^@\\s]+\\.[a-z]{2,}"),
                "correo: formato inválido"
        );

        // Regla de edad mínima
        Regla<Registro> edadValida = Regla.exigir(r -> r.edad() >= 14, "edad: mínimo 14 años");

        // Composición completa con .y(...)
        Regla<Registro> todas = usuarioValido.y(correoValido).y(edadValida);

        List<Registro> registros = List.of(
                new Registro("camila_r", "camila@correo.co", 19),
                new Registro("", "sin-arroba", 12),
                new Registro("ana", "ana@correo", 15)
        );

        for (Registro r : registros) {
            List<String> errores = todas.validar(r);
            String usuario = r.usuario().isEmpty() ? "(vacío)" : r.usuario();
            System.out.printf("%-9s %-10s %s%n",
                    errores.isEmpty() ? "VÁLIDO" : "INVÁLIDO",
                    usuario,
                    errores);
        }
    }
}
