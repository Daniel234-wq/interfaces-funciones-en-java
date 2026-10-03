package com.curso.laboratorios.lab2;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Interfaz funcional central del Laboratorio 2: Motor de validación componible.
 * Permite validar un valor de tipo T retornando una lista de mensajes de error.
 */
@FunctionalInterface
public interface Regla<T> {

    List<String> validar(T valor);

    /**
     * Combina esta regla con otra mediante conjunción (AND).
     * Ejecuta ambas y une los errores resultantes.
     */
    default Regla<T> y(Regla<? super T> otra) {
        Objects.requireNonNull(otra);
        return valor -> {
            List<String> errores = new ArrayList<>(validar(valor));
            errores.addAll(otra.validar(valor));
            return errores;
        };
    }

    /**
     * Combina esta regla con otra mediante disyunción (OR).
     * Si la regla actual es válida (sin errores), el resultado es válido.
     * Si falla, se evalúa 'otra'. Si 'otra' es válida, el resultado es válido.
     * Si ambas fallan, se retornan los errores de ambas.
     */
    default Regla<T> o(Regla<? super T> otra) {
        Objects.requireNonNull(otra);
        return valor -> {
            List<String> erroresActuales = validar(valor);
            if (erroresActuales.isEmpty()) {
                return List.of();
            }
            List<String> erroresOtra = otra.validar(valor);
            if (erroresOtra.isEmpty()) {
                return List.of();
            }
            List<String> combinados = new ArrayList<>(erroresActuales);
            combinados.addAll(erroresOtra);
            return combinados;
        };
    }

    /**
     * Aplica la regla únicamente cuando la condición dada se cumple.
     * Si la condición es falsa, la validación se aprueba automáticamente (lista vacía).
     */
    default Regla<T> cuando(Predicate<? super T> condicion) {
        Objects.requireNonNull(condicion);
        return valor -> condicion.test(valor) ? validar(valor) : List.of();
    }

    /**
     * Fábrica estática que crea una regla a partir de una condición lógica.
     */
    static <T> Regla<T> exigir(Predicate<? super T> condicion, String mensaje) {
        Objects.requireNonNull(condicion);
        Objects.requireNonNull(mensaje);
        return valor -> condicion.test(valor) ? List.of() : List.of(mensaje);
    }

    /**
     * Fábrica estática que proyecta una regla de un campo interno sobre el objeto contenedor.
     */
    static <T, C> Regla<T> campo(Function<? super T, ? extends C> extractor, Regla<? super C> regla) {
        Objects.requireNonNull(extractor);
        Objects.requireNonNull(regla);
        return valor -> regla.validar(extractor.apply(valor));
    }
}
