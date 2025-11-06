# 🧠 Prácticos de Programación en Java – 2025

Este repositorio contiene los trabajos prácticos desarrollados en Java durante el año 2025, correspondientes a la materia **Informática III**.
Cada práctico aborda distintos conceptos fundamentales de la programación orientada a objetos y estructuras de datos.

---

## 📂 Estructura del repositorio

```
/
├── Practico1_GestorTareas/
│   ├── src/
│   └── README.md
│
├── Practico2_Recursividad/
│   ├── src/
│   └── README.md
│
├── Practico3_PilasColas/
│   ├── src/
│   └── README.md
│
├── Practico4_ListasEnlazadas/
│   ├── src/
│   └── README.md
│
├── Practico5_ArbolAVL/
│   ├── src/
│   └── README.md
│
├── Practico6_ArbolRojinegro/
│   ├── src/
│   └── README.md
│
├── Practico_Extra_MonticuloBinario/
│   ├── src/
│   └── README.md
│
└── Proyecto_Ordenamiento/
    ├── src/
    └── README.md
```

Cada carpeta contiene el código fuente y una breve descripción de los ejercicios correspondientes.

---

## 📘 Práctico 1 – Sistema de Gestión de Tareas Personales

**Objetivo:** Implementar un sistema básico de gestión de tareas utilizando clases, objetos y estructuras dinámicas (`ArrayList`).

**Requerimientos principales:**

* Clase `Tarea` con atributos `descripcion` y `estado`.
* Clase `GestorTareas` para administrar las tareas.
* Menú interactivo en consola con opciones para agregar, listar, completar y eliminar tareas.
* Validación de entradas del usuario.
* Opción adicional: guardar/cargar las tareas desde un archivo de texto.

**Temas:**

> Estructuras dinámicas, manejo de archivos, control de flujo, programación estructurada y modularidad.

---

## 🔁 Práctico 2 – Recursividad

**Objetivo:** Implementar funciones recursivas para resolver distintos problemas matemáticos y de cadenas.

**Ejercicios destacados:**

1. Conteo de dígitos de un número.
2. Invertir una cadena.
3. Suma y promedio de elementos de un arreglo.
4. Cálculo del MCD (Euclides).
5. Conversión a binario.
6. Verificación de palíndromos.
7. Serie de Fibonacci con optimización (memoización).
8. Búsqueda recursiva en un arreglo.

**Temas:**

> Recursión directa, recursión de cola, caso base, eficiencia, y optimización de algoritmos recursivos.

---

## 🧱 Práctico 3 – Pilas y Colas con Arreglos

**Objetivo:** Implementar las estructuras de datos **Pila** y **Cola** utilizando arreglos estáticos.

**Ejercicios principales:**

* `PilaArreglo`: `push`, `pop`, `top`, `isEmpty`, `isFull`.
* `ColaArreglo`: `enqueue`, `dequeue`, `top`, `isEmpty`, `isFull`.
* Inversión de cadenas con pila.
* Simulación de turnos en un banco con cola.
* Verificación de palíndromos usando pila y cola.
* Sistema de deshacer/rehacer.
* Simulación de impresora.
* Cola circular para gestión de llamadas.

**Temas:**

> Arreglos, estructuras lineales, algoritmos LIFO y FIFO, simulaciones y abstracción de datos.

---

## 🔗 Práctico 4 – Listas Enlazadas Simples

**Objetivo:** Implementar y manipular una lista enlazada simple desde cero.

**Ejercicios incluidos:**

1. Creación de nodos.
2. Inserción al inicio y al final.
3. Eliminación por valor.
4. Búsqueda de elementos.
5. Conteo de nodos.
6. Inversión de la lista.
7. Inserción en una posición específica.
8. Eliminación de duplicados.
9. Aplicación práctica: registro de alumnos con nombre y legajo.

**Temas:**

> Nodos, punteros, enlaces, recorrido de listas, operaciones dinámicas y abstracción de datos.

---

## 🔢 Práctico 5 – Árbol AVL

**Objetivo:** Implementar un árbol AVL que se mantenga balanceado automáticamente tras inserciones y eliminaciones.

**Ejercicios destacados:**

1. Inserciones y FE paso a paso (casos LL, RR).
2. Inserciones con rotación doble (LR, RL).
3. Análisis de secuencias ordenadas y desbalanceo.
4. Eliminación con rebalanceo.
5. Método `esAVL()` para validar la propiedad del árbol.
6. Implementación guiada de rotaciones simples y dobles.
7. Análisis de altura O(log n) y comparación con ABB y Rojinegro.
8. Pruebas unitarias con secuencias crecientes, decrecientes y aleatorias.

**Temas:**

> Árboles balanceados, rotaciones, altura logarítmica, eficiencia en búsqueda e inserción.

---

## 🟥 Práctico 6 – Árbol Rojinegro

**Objetivo:** Implementar un árbol rojinegro con balance automático mediante reglas de coloración y rotaciones.

**Ejercicios destacados:**

1. Creación de nodos `RBNode` y uso del nodo `NIL` sentinel.
2. Rotaciones izquierda y derecha.
3. Inserción tipo ABB sin balance.
4. Clasificador de casos (TÍO_ROJO, LL, RR, LR, RL).
5. Recoloreo y balanceo en inserción (`fixInsert`).
6. Implementación de `successor` y `predecessor`.
7. Consulta por rango `[a,b]` mediante recorrido acotado.
8. Verificadores de invariantes: `raizNegra`, `sinRojoRojo`, `alturaNegra`.

**Temas:**

> Árboles balanceados por color, rotaciones dobles, propiedades de balance y complejidad O(log n).

---

## ⛏️ Práctico Extra – Montículo Binario (Heaps)

**Objetivo:** Implementar un **montículo binario** mínimo y máximo, junto con aplicaciones prácticas.

**Ejercicios incluidos:**

1. Implementación de `MinHeap` con arreglo.
2. Métodos `add`, `poll`, `peek`, `isEmpty`.
3. Métodos `percolateUp` y `percolateDown`.
4. Mostrar el heap como árbol (`printTree`).
5. Construcción desde arreglo (`heapify`).
6. Implementación de `heapsort`.
7. Implementación de `MaxHeap`.
8. Cola de prioridad médica usando `MinHeap`.
9. Seguimiento del estado interno (`printArray`).
10. Integrador: Agenda de tareas con prioridad.

**Temas:**

> Montículos binarios, heapify, prioridad, ordenamiento y simulaciones.

---

## 🍕 Proyecto – Sistema de Gestión de Pedidos (Ordenamiento)

**Objetivo:** Desarrollar un sistema que gestione los pedidos de una pizzería aplicando tres algoritmos de ordenamiento: Inserción, Shellsort y Quicksort.

**Estructura del proyecto:**

```
/gestion-pizzeria
│
├── src
│   ├── Main.java
│   ├── Pedido.java
│   ├── Pizzeria.java
│   ├── Ordenador.java
│   └── TiempoOrdenamiento.java
│
└── README.md
```

**Funcionalidades principales:**

* Agregar, eliminar y actualizar pedidos.
* Ordenar por:

  * Tiempo de preparación → Inserción.
  * Precio total → Shellsort.
  * Nombre del cliente → Quicksort.
* Medición de tiempos de ejecución con diferentes tamaños de lista (100, 1000, 10000).

**Temas:**

> Ordenamiento por intercambio, partición y mejora de eficiencia; análisis de complejidad y aplicación en contexto real.

---


🏥 Proyecto Integrador –  Sistema de Gestión Hospitalaria  

## ⚙️ Cómo ejecutar los proyectos

Este proyecto implementa un sistema integral para la **gestión de turnos médicos, planificación de quirófanos y reportes hospitalarios**, utilizando estructuras de datos eficientes, en base a los requerimientos del **Trabajo Práctico Integrador de Informática 3 (IUA, 2025)**.

**Estructura del proyecto:**
```
/src
├── agenda
│ └── AgendaMedicoAVL.java
├── importer
│ └── ImportadorCSV.java
├── modelo
│ ├── Medico.java
│ ├── Paciente.java
│ └── Turno.java
├── quirfano
│ ├── PlanificadorQuirofanoSimple.java
│ └── SolicitudCirugia.java
├── reportes
│ └── ReporteTurnos.java
├── utilidades
│ └── Comparadores.java
└── InteractiveMain.java
/csv
├── medicos.csv
├── pacientes.csv
└── turnos.csv
```


### 1️⃣ Agenda médica (AVL)

📚 Implementación de una **Agenda por Médico** con inserción y búsqueda en `O(log n)`  
📌 Control de turnos sin choques exactos  
🟢 AVL balanceado automáticamente

void addTurno(Turno t);     // Inserción: O(log n)
List<Turno> listar();       // Listado cronológico: O(n)
###2️⃣ Listado ordenado
Ordenamiento de turnos por fecha y hora usando inOrder() sobre el AVL.
🕒 Tiempo: O(n)

###3️⃣ Búsqueda de hueco libre
✔️ Busca primer hueco ≥ durMin desde t0, sin escanear toda la agenda.
🧠 Tiempo: O(log n + k) (AVL + turnos saltados)

###4️⃣ Importador CSV
📥 Carga masiva desde /csv/.csv con validación de encabezado
🎯 Mapas y listas preparadas para agenda, quirófanos y pacientes

###5️⃣ Undo / Redo
📌 Control de cambios sobre la agenda con dos stacks (undoStack, redoStack)
⚡ Tiempo: O(1) por operación

###6️⃣ Hash por paciente
🔍 Mapa DNI → Lista de Turnos
🏎️ Acceso constante: O(1) promedio

###7️⃣ Merge y deduplicación
scss
Copiar código
merge(agendaLocal, agendaNube) → agendaUnificada
📍 Reglas:

Misma ID: se guarda uno

Mismo médico + mismo horario: log de conflicto

🕓 Complejidad: O(n + m)

###8️⃣ Reportes (sorts)
Reporte	Algoritmo	Estable
Por hora	Inserción	✅
Por duración	Shellsort	❌
Por apellido	Quicksort (Lomuto)	❌

Incluye mediciones con datasets de 1K, 10K y 50K turnos.

###9️⃣ Quirófanos + Top-K
🧱 Estructuras clave:

PriorityQueue (Min-Heap) → quirófanos por próxima disponibilidad

HashMap → minutos totales por médico

1. Clonar el repositorio:

   ```bash
   git clone https://github.com/CacheIua/Informatica-III.git
   cd estructuras-java-2025
   ```
2. Abrir cada práctico en tu IDE (Eclipse, IntelliJ IDEA o VS Code).
3. Compilar y ejecutar desde la clase `Main.java` correspondiente.

---

## 💬 Commits recomendados

```bash
feat: crear clases principales y estructura base
feat: implementar funcionalidades principales
fix: corregir validaciones o errores lógicos
refactor: mejorar legibilidad y organización del código
```

---

## 👨‍💻 Autor

**Francisco Martínez**
📅 Año: 2025
Materia: *Informática III* – Estructuras de Datos en Java
Institución: *[Agregar nombre de la institución si querés]*

---

## 🪶 Licencia

Este proyecto se distribuye con fines educativos.
Podés usar, modificar y compartir el código libremente citando la fuente.
