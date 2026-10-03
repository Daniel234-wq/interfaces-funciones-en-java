package com.curso;

import com.curso.evaluacion.AutoevaluacionYRespuestas;
import com.curso.fundamentos.*;
import com.curso.biblioteca.*;
import com.curso.diseno.*;
import com.curso.laboratorios.lab1.Lab01GeneradorSlugs;
import com.curso.laboratorios.lab2.Lab02MotorValidacion;
import com.curso.laboratorios.lab3.Lab03CalculadoraEstrategias;
import com.curso.laboratorios.lab4.Lab04MotorPromociones;
import com.curso.retos.*;

/**
 * Clase ejecutora maestra del proyecto.
 * Permite ejecutar de forma secuencial y ordenada todas las soluciones:
 * - 4 Laboratorios prácticos completos
 * - 4 Retos para profundizar
 * - Solucionario de autoevaluación y puntos de control
 * - Ejemplos de fundamentos, biblioteca estándar y diseño profesional
 */
public class EjecutorGeneral {

    public static void main(String[] args) {
        System.out.println("================================================================================");
        System.out.println("           GUÍA COMPLETA: INTERFACES FUNCIONALES EN JAVA (JAVA 21+)");
        System.out.println("================================================================================\n");

        boolean ejecutarTodo = args.length > 0 && ("--todo".equalsIgnoreCase(args[0]) || "-all".equalsIgnoreCase(args[0]));

        if (ejecutarTodo) {
            ejecutarListadosTeoricos();
        }

        ejecutarLaboratorios();
        ejecutarRetos();
        ejecutarEvaluaciones();

        System.out.println("\n================================================================================");
        System.out.println("           TODOS LOS LABORATORIOS, RETOS Y PREGUNTAS FINALIZADOS CON ÉXITO");
        System.out.println("================================================================================");
    }

    public static void ejecutarLaboratorios() {
        System.out.println(">>>>>>>>>> [PARTE 1: LABORATORIOS DE LA GUÍA] <<<<<<<<<<\n");

        System.out.println("--------------------------------------------------------------------------------");
        System.out.println(">>> LABORATORIO 1: Generador de slugs por composición");
        System.out.println("--------------------------------------------------------------------------------");
        Lab01GeneradorSlugs.main(new String[]{});

        System.out.println("\n--------------------------------------------------------------------------------");
        System.out.println(">>> LABORATORIO 2: Motor de validación componible");
        System.out.println("--------------------------------------------------------------------------------");
        Lab02MotorValidacion.main(new String[]{});

        System.out.println("\n--------------------------------------------------------------------------------");
        System.out.println(">>> LABORATORIO 3: Calculadora con estrategias");
        System.out.println("--------------------------------------------------------------------------------");
        Lab03CalculadoraEstrategias.main(new String[]{});

        System.out.println("\n--------------------------------------------------------------------------------");
        System.out.println(">>> LABORATORIO 4: Proyecto integrador - Motor de promociones");
        System.out.println("--------------------------------------------------------------------------------");
        Lab04MotorPromociones.main(new String[]{});
        System.out.println();
    }

    public static void ejecutarRetos() {
        System.out.println(">>>>>>>>>> [PARTE 2: RETOS PARA PROFUNDIZAR] <<<<<<<<<<\n");

        System.out.println("--------------------------------------------------------------------------------");
        System.out.println(">>> RETO 1: TriFunction.curry()");
        System.out.println("--------------------------------------------------------------------------------");
        Reto1TriFunctionCurry.main(new String[]{});

        System.out.println("\n--------------------------------------------------------------------------------");
        System.out.println(">>> RETO 2: Regla.o(...) y Regla.cuando(...)");
        System.out.println("--------------------------------------------------------------------------------");
        Reto2ReglaExtensiones.main(new String[]{});

        System.out.println("\n--------------------------------------------------------------------------------");
        System.out.println(">>> RETO 3: Memoización concurrente y análisis de recursión");
        System.out.println("--------------------------------------------------------------------------------");
        try {
            Reto3MemoizacionConcurrente.main(new String[]{});
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("\n--------------------------------------------------------------------------------");
        System.out.println(">>> RETO 4: Patrón Reintentar.de(Supplier<T>, intentos)");
        System.out.println("--------------------------------------------------------------------------------");
        Reto4ReintentosSupplier.main(new String[]{});
        System.out.println();
    }

    public static void ejecutarEvaluaciones() {
        System.out.println(">>>>>>>>>> [PARTE 3: PUNTOS DE CONTROL Y AUTOEVALUACIÓN] <<<<<<<<<<\n");
        AutoevaluacionYRespuestas.main(new String[]{});
    }

    public static void ejecutarListadosTeoricos() {
        System.out.println(">>>>>>>>>> [PARTE 0: LISTADOS DE EJEMPLO DE LA GUÍA] <<<<<<<<<<\n");
        try {
            Listado02Evolucion.main(new String[]{});
            Listado03Transformador.main(new String[]{});
            Listado04TiposDestino.main(new String[]{});
            Listado05SignificadoThis.main(new String[]{});
            Listado06ReferenciasMetodos.main(new String[]{});
            Listado07CapturaVariables.main(new String[]{});
            Listado08NueveInterfacesCentrales.main(new String[]{});
            Listado09InspeccionarJavaUtilFunction.main(new String[]{});
            Listado10ComposicionFunction.main(new String[]{});
            Listado11ComposicionPredicate.main(new String[]{});
            Listado12ComposicionConsumerYComparadores.main(new String[]{});
            Listado13EspecializacionesPrimitivas.main(new String[]{});
            Listado14TriFunctionYTarifaEnvio.main(new String[]{});
            Listado15ExcepcionesVerificadas.main(new String[]{});
            Listado16OrdenSuperiorYCurrificacion.main(new String[]{});
            Listado17EvaluacionDiferidaSupplier.main(new String[]{});
            Listado18Memoizacion.main(new String[]{});
            Listado19ComandoYObservador.main(new String[]{});
            Listado20MetodosSinteticosJavac.main(new String[]{});
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("\n--------------------------------------------------------------------------------\n");
    }
}
