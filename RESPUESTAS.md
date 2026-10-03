# Guía Técnica: Interfaces Funcionales en Java (Java 21 LTS - Java 26)

Solución integral, verificada y estructurada del documento técnico **"Interfaces funcionales en Java: El contrato de un solo método que convirtió al comportamiento en un dato"**.

Este repositorio contiene la implementación completa del modelo de datos, los 20 listados de código, los 4 laboratorios prácticos, los 4 retos para profundizar y el solucionario con justificaciones teóricas de los puntos de control y la autoevaluación.

---

## 📁 Estructura del Proyecto

El código fuente está modularizado en paquetes limpios y expresivos dentro de `src/`:

```
src/
├── Main.java                                              # Punto de entrada directo delegador a EjecutorGeneral
└── com/
    └── curso/
        ├── EjecutorGeneral.java                           # Orquestador maestro para correr todo el proyecto
        │
        ├── modelo/
        │   ├── Producto.java                              # Record de catálogo (Listado 1)
        │   └── Catalogo.java                              # Colección de muestra (Listado 2)
        │
        ├── fundamentos/                                   # Parte I: Fundamentos y sintaxis SAM
        │   ├── Listado02Evolucion.java                    # 4 estilos: clase, anónima, lambda y referencia
        │   ├── Listado03Transformador.java                # SAM con default, static, private y Object
        │   ├── Listado04TiposDestino.java                 # Inferencia por tipo destino (target type)
        │   ├── Listado05SignificadoThis.java              # Alcance léxico de 'this' vs clase anónima
        │   ├── Listado06ReferenciasMetodos.java           # Los 4 tipos de referencias a métodos (::)
        │   └── Listado07CapturaVariables.java             # Closures y variables efectivamente finales
        │
        ├── biblioteca/                                    # Parte II: Paquete java.util.function
        │   ├── Listado08NueveInterfacesCentrales.java     # Las 9 interfaces clave aplicadas al catálogo
        │   ├── Listado09InspeccionarJavaUtilFunction.java # Reflexión en runtime con jrt:/ (43 interfaces)
        │   ├── Listado10ComposicionFunction.java          # andThen, compose e identity
        │   ├── Listado11ComposicionPredicate.java         # and, or, negate, not, isEqual
        │   ├── Listado12ComposicionConsumerYComparadores.java # Consumer.andThen, minBy, maxBy, Comparator
        │   └── Listado13EspecializacionesPrimitivas.java  # Interfaces primitivas sin autoboxing
        │
        ├── diseno/                                        # Parte III: Diseño y práctica profesional
        │   ├── Listado14TriFunctionYTarifaEnvio.java      # Interfaces de dominio y comodines PECS
        │   ├── Listado15ExcepcionesVerificadas.java       # Manejo de checked exceptions sin ocultar causa
        │   ├── Listado16OrdenSuperiorYCurrificacion.java  # Fábricas de funciones y currificación
        │   ├── Listado17EvaluacionDiferidaSupplier.java   # Evaluación perezosa (lazy) con Supplier
        │   ├── Listado18Memoizacion.java                  # Caché en memoria con computeIfAbsent
        │   ├── Listado19ComandoYObservador.java           # Patrones Comando y Observador con lambdas
        │   └── Listado20MetodosSinteticosJavac.java       # Métodos sintéticos e invokedynamic
        │
        ├── laboratorios/                                  # Laboratorios obligatorios de la guía
        │   ├── lab1/
        │   │   └── Lab01GeneradorSlugs.java               # Lab 1: Pipeline de slugs + análisis de orden
        │   ├── lab2/
        │   │   ├── Regla.java                             # Interfaz funcional genérica con composición
        │   │   ├── Registro.java                          # Record de datos para validación
        │   │   └── Lab02MotorValidacion.java              # Lab 2: Motor de validación componible
        │   ├── lab3/
        │   │   └── Lab03CalculadoraEstrategias.java       # Lab 3: Calculadora con mapa + OCP vs switch
        │   └── lab4/
        │       ├── Promocion.java                         # Record de promoción comercial
        │       └── Lab04MotorPromociones.java             # Lab 4: Proyecto integrador de descuentos
        │
        ├── retos/                                         # Retos para profundizar (Página 33)
        │   ├── Reto1TriFunctionCurry.java                 # Reto 1: TriFunction.curry() y uncurry()
        │   ├── Reto2ReglaExtensiones.java                 # Reto 2: Métodos o(...) y cuando(...)
        │   ├── Reto3MemoizacionConcurrente.java           # Reto 3: ConcurrentHashMap y análisis recursivo
        │   └── Reto4ReintentosSupplier.java               # Reto 4: Reintentar.de(Supplier, intentos)
        │
        └── evaluacion/                                    # Solucionario técnico verificado
            └── AutoevaluacionYRespuestas.java             # Puntos de control, autoevaluación y reflexiones
```

---

## 🚀 Cómo Ejecutar

### Opción 1: Ejecutar Todo el Proyecto (Orquestador)
Compilar y ejecutar la suite completa:
```powershell
javac -d bin (Get-ChildItem -Path "src" -Recurse -Filter "*.java").FullName
java "-Dfile.encoding=UTF-8" "-Dstdout.encoding=UTF-8" -cp bin Main
```
O incluyendo también la ejecución en vivo de los 20 listados teóricos:
```powershell
java "-Dfile.encoding=UTF-8" "-Dstdout.encoding=UTF-8" -cp bin com.curso.EjecutorGeneral --todo
```

### Opción 2: Ejecutar los Laboratorios Individualmente
- **Laboratorio 1 (Generador de Slugs):**
  ```powershell
  java "-Dfile.encoding=UTF-8" "-Dstdout.encoding=UTF-8" -cp bin com.curso.laboratorios.lab1.Lab01GeneradorSlugs
  ```
- **Laboratorio 2 (Motor de Validación):**
  ```powershell
  java "-Dfile.encoding=UTF-8" "-Dstdout.encoding=UTF-8" -cp bin com.curso.laboratorios.lab2.Lab02MotorValidacion
  ```
- **Laboratorio 3 (Calculadora con Estrategias):**
  ```powershell
  java "-Dfile.encoding=UTF-8" "-Dstdout.encoding=UTF-8" -cp bin com.curso.laboratorios.lab3.Lab03CalculadoraEstrategias
  ```
- **Laboratorio 4 (Motor de Promociones - Integrador):**
  ```powershell
  java "-Dfile.encoding=UTF-8" "-Dstdout.encoding=UTF-8" -cp bin com.curso.laboratorios.lab4.Lab04MotorPromociones
  ```

### Opción 3: Ejecutar los Retos de Profundización
- **Reto 1 (Currificación TriFunction):**
  ```powershell
  java "-Dfile.encoding=UTF-8" "-Dstdout.encoding=UTF-8" -cp bin com.curso.retos.Reto1TriFunctionCurry
  ```
- **Reto 2 (Reglas Avanzadas: `o` y `cuando`):**
  ```powershell
  java "-Dfile.encoding=UTF-8" "-Dstdout.encoding=UTF-8" -cp bin com.curso.retos.Reto2ReglaExtensiones
  ```
- **Reto 3 (Memoización Concurrente y Recursión):**
  ```powershell
  java "-Dfile.encoding=UTF-8" "-Dstdout.encoding=UTF-8" -cp bin com.curso.retos.Reto3MemoizacionConcurrente
  ```
- **Reto 4 (Patrón Reintentar con Supplier):**
  ```powershell
  java "-Dfile.encoding=UTF-8" "-Dstdout.encoding=UTF-8" -cp bin com.curso.retos.Reto4ReintentosSupplier
  ```

### Opción 4: En IntelliJ IDEA
1. Abrir la carpeta raíz del proyecto en IntelliJ IDEA.
2. Hacer clic derecho sobre cualquiera de las clases con método `main` y seleccionar **Run**.
3. O ejecutar directamente `Main.java` en la raíz de `src/`.

---

## 🧪 Resumen de Soluciones a Laboratorios y Retos

### Laboratorio 1: Generador de slugs por composición
- **Salida verificada:**
  ```
  " Silla Ergonómica "             → silla-ergonomica
  "Audífonos Bluetooth 5.3"        → audifonos-bluetooth-53
  "¡Oferta! Portátil i7 -- 16GB"   → oferta-portatil-i7-16gb
  ```
- **Paso 4 (Análisis de orden):** La composición con `andThen` no es conmutativa. Si `SOLO_VALIDOS` (`[^a-z0-9\s-]`) se ejecuta antes de `MINUSCULAS`, las mayúsculas son eliminadas por la regex en lugar de convertirse a minúsculas. Si se ejecuta antes de `SIN_TILDES`, las vocales con tildes son destruidas en lugar de ser descompuestas a su carácter base ASCII.

### Laboratorio 2: Motor de validación componible
- **Salida verificada:**
  ```
  VÁLIDO    camila_r   []
  INVÁLIDO  (vacío)    [usuario: obligatorio, usuario: mínimo 4 caracteres, correo: formato inválido, edad: mínimo 14 años]
  INVÁLIDO  ana        [usuario: mínimo 4 caracteres, correo: formato inválido]
  ```
- **Técnica:** Diseño de `@FunctionalInterface interface Regla<T>` con combinador `y(Regla<? super T>)` respetando PECS y proyecciones de campos `campo(...)`.

### Laboratorio 3: Calculadora con estrategias
- **Salida verificada:**
  ```
  12 + 30  = 42.0
  2 ^ 10   = 1024.0
  7 / 2    = 3.5
  9 max 4  = 9.0
  5 / 0    → error: división por cero
  3 % 2    → error: operador desconocido: %
  Operadores disponibles: [+, -, *, /, ^, max]
  ```
- **Paso 5 (Reflexión Open/Closed Principle):** Para añadir `%`, en el enfoque funcional basta invocar `OPERACIONES.put("%", (a, b) -> a % b);` en tiempo de ejecución sin modificar `evaluar()`. Con `switch`, es obligatorio editar el código fuente del método, recompilar y asumir el riesgo de regresión.

### Laboratorio 4: Motor de promociones (Proyecto Integrador)
- **Salida verificada:**
  ```
  Portátil          Tecno 10 %     $  3.200.000 → $  2.880.000
  Mouse             Tecno 10 %     $     85.000 → $     76.500
  Audífonos         Bono $50.000   $    240.000 → $    190.000
  Lápiz             Liquidación    $      2.500 → $      1.750
  Silla ergonómica  Bono $50.000   $    890.000 → $    840.000
  Ahorro total para el cliente: $429.250
  ```
- **Técnica:** Desacoplamiento total entre las reglas de promoción y el motor de cálculo de precios, usando `Predicate<Producto>`, `DoubleUnaryOperator`, `BinaryOperator.minBy(...)`, `Supplier<Promocion>`, `BiConsumer` y `ToDoubleBiFunction`.

---

## 💡 Solución de los Retos para Profundizar (Pág. 33)

1. **Reto 1 (`TriFunction.curry()`):**
   Transforma una función ternaria `(a, b, c) -> R` en funciones de un solo argumento encadenadas: `a -> b -> c -> R`. Permite la aplicación parcial de argumentos (fijar precio, fijar tasa impositiva, evaluar con diferentes descuentos). Se implementó también `uncurry(...)`.
2. **Reto 2 (`Regla.o(...)` y `Regla.cuando(...)`):**
   - `o(...)`: Disyunción lógica. Si la primera regla valida con éxito, el valor es aceptado; si no, evalúa la alternativa. Si ambas fallan, recopila los errores combinados.
   - `cuando(...)`: Validación condicional. Si la condición dada es verdadera, ejecuta la regla; si es falsa, la regla se omite y se considera válida (lista vacía de errores).
3. **Reto 3 (Memoización Concurrente y Recursión):**
   - Concurrencia atómica con `ConcurrentHashMap.computeIfAbsent` garantizando exactamente un solo cálculo para solicitudes simultáneas multihilo.
   - **Análisis de Recursión:** `ConcurrentHashMap.computeIfAbsent` bloquea el bucket de la tabla hash. En Java 9+, si la función llamada intenta reingresar recursivamente para la misma clave o sobre el mismo bucket, la JVM lanza `java.lang.IllegalStateException: Recursive update` o genera un interbloqueo (*deadlock*) irreversible. Se demostró la excepción y se presentó la solución segura con verificación de clave previa y `putIfAbsent`.
4. **Reto 4 (`Reintentar.de(Supplier<T>, int intentos)`):**
   Patrón de tolerancia a fallos con reintentos configurables. Si el proveedor lanza excepciones durante los intentos intermedios, las acumula como excepciones suprimidas (`addSuppressed`) y propaga `ReintentosAgotadosException` con trazabilidad completa si se agotan los intentos.

---

## 📋 Solucionario de Autoevaluación y Puntos de Control

### Puntos de Control (Página 6)
1. **¿Una interfaz con un método abstracto, dos default y `String toString()` es funcional?**
   **Sí.** Los métodos default no son abstractos y `toString()` es método público de `Object`. Los métodos que sobreescriben métodos públicos de `Object` no cuentan para el SAM (JLS §9.8).
2. **¿Qué ventaja aporta `@FunctionalInterface` si la interfaz ya es funcional sin ella?**
   Garantiza validación en tiempo de compilación para que nadie agregue otro método abstracto por error y documenta formalmente la intención de diseño.
3. **¿Por qué una clase abstracta con un solo método abstracto no puede recibir una lambda?**
   Por restricción del lenguaje Java (JLS §15.27). Las clases abstractas pueden contener constructores con estado e inicializadores que las implementaciones ligeras generadas con `invokedynamic` no soportan.

### Autoevaluación (Páginas 32-33)
| # | Pregunta | Respuesta Correcta | Justificación |
|---|----------|--------------------|---------------|
| 1 | ¿Cuál es interfaz funcional? | **b) `interface B { void x(); boolean equals(Object o); }`** | `equals(Object)` es de Object y no cuenta; queda únicamente `void x()`. |
| 2 | ¿Qué ocurre con `var f = x -> x + 1;`? | **b) Error: la lambda necesita tipo destino** | `var` necesita que la expresión aporte el tipo, pero una lambda no tiene tipo propio sin un contexto destino. |
| 3 | Dentro de una lambda, `this` se refiere a... | **b) La instancia que la contiene** | Las lambdas adoptan el ámbito léxico del contenedor (`enclosing instance`), a diferencia de clases anónimas. |
| 4 | `String::length` como `Function<String, Integer>` es... | **c) Método de instancia de un objeto arbitrario** | Referencia no ligada (tipo 3): el primer parámetro es el receptor de la llamada. |
| 5 | ¿Qué calcula `f.andThen(g).apply(x)`? | **b) `g(f(x))`** | Ejecuta primero `f` y al resultado le aplica `g`. |
| 6 | ¿Interfaz para `Producto -> double` sin autoboxing? | **b) `ToDoubleFunction<Producto>`** | Su descriptor funcional primitivo es `T -> double` con `applyAsDouble`. |
| 7 | ¿Qué pasa con `contador++` sobre variable local? | **b) Error: debe ser efectivamente final** | Las variables locales capturadas por lambdas no pueden ser modificadas. |
| 8 | `Function<String, URI> f = s -> new URI(s);` | **b) Error: excepción verificada no reportada** | `URI(String)` lanza `URISyntaxException`, no permitida en `Function.apply`. |
| 9 | ¿Para qué es especialmente útil `Supplier<T>`? | **a) Evaluación diferida de valores** | Ejecuta o genera el valor solo cuando es estrictamente necesario (lazy evaluation). |
| 10 | `ejecutar(Supplier<String>)` y `ejecutar(Callable<String>)` con `() -> "x"` | **c) Error: llamada ambigua** | Ambas firmas coinciden con la expresión lambda y el compilador no puede decidir el tipo destino. |
