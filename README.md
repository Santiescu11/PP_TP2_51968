Trabajo Práctico 2 - Sistema de Eventos Universitarios

**Universidad Tecnológica Nacional - Facultad Regional Mendoza (UTN FRM)**  
**Alumno:** Santiago Jorge Escudero  

---

##  Descripción del Proyecto
Este repositorio contiene la resolución completa del Trabajo Práctico 2.



##  Ejercicios Resueltos y Tecnologías Aplicadas

### 1. Manejo de Excepciones y Persistencia de Datos
- **Excepciones Propias:** Creación y lanzamiento de la excepción personalizada `CupoExcedidoException` para evitar inscripciones masivas.
- **Control de Flujo:** Implementación de un bloque `try-catch-finally` en la clase principal para capturar errores de forma limpia y controlada.
- **Serialización:** Uso de la interfaz `Serializable`, `FileOutputStream` y `ObjectInputStream` para guardar y recuperar el estado completo del `EventoUniversitario` (y sus objetos en cascada) en archivos `.dat`.

### 2. Interfaces y Polimorfismo
- **Creación de Interfaces:** Se incorporó la interfaz `Certificable` con sus constantes y firmas de métodos.
- **Implementación Selectiva:** Las clases `Taller` y `Curso` implementan la interfaz para emitir certificados, mientras que la clase `Charla` fue excluida por diseño.
- **Identidad de Objetos:** Uso de la palabra reservada `instanceof` para recorrer colecciones polimórficas y determinar dinámicamente qué actividades son aptas para certificación.

### 3. Genéricos (Generics) y Wildcards (Comodines)
- **Métodos Parametrizados:** Implementación de un método genérico acotado `<T extends Actividad>` para filtrar listas de actividades según su tipo exacto de clase (`Charla.class`, `Taller.class`, `Curso.class`).
- **Wildcards:** Creación de un método flexible con `<? extends Actividad>` capaz de recibir cualquier lista derivada de la clase base para calcular los costos totales de materiales.

### 4. Clases Anidadas y Concurrencia (Hilos)
- **Inner Classes:** Creación de la clase anidada `TicketDeAcceso` como miembro interno de la clase `Inscripcion`, garantizando un fuerte acoplamiento lógico.
- **Multithreading:** Implementación de la clase `EnvioTicketsThread` heredando de `Thread`. Este hilo secundario corre en paralelo simulando el envío de correos electrónicos con demoras (`Thread.sleep()`), permitiendo que el hilo principal (Main) finalice sus operaciones sin bloqueos.

---

## 📂 Estructura de Paquetes
```text
src/utn/eventos/
├── actividades/      # Clases de dominio (Actividad, Charla, Curso, Taller)
├── certificacion/    # Interfaz Certificable
├── excepciones/      # Excepciones personalizadas
├── hilos/            # Procesos concurrentes (EnvioTicketsThread)
├── modelo/           # Entidades principales (EventoUniversitario, Sala)
├── App.java          # Clase Main (Flujo de pruebas)
├── Estudiante.java   # Entidad de usuario
└── Inscripcion.java  # Clase asociativa y su clase anidada Ticket
