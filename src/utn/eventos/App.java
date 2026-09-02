package utn.eventos;

public class App {

    public static void main(String[] args) {

        // a. Registrar estudiantes
        System.out.println("--- a. Registrando Estudiantes ---");
        Estudiante est1 = new Estudiante("51968", "Juan Perez");
        Estudiante est2 = new Estudiante("52000", "Maria Gomez");
        Estudiante est3 = new Estudiante("52001", "Carlos Lopez");

        // b. Construir eventos
        System.out.println("--- b. Creando Evento Universitario ---");
        // Costo base de 1500, marcamos 'false' para que NO sea gratuito y aplique impuestos
        EventoUniversitario eventoUTN = new EventoUniversitario("E001", "Jornadas de Sistemas 2026", 1500.0, false);

        // c. Asignar una sala a cada evento
        System.out.println("--- c. Asignando Sala ---");
        Sala salaMagna = new Sala(1, "Aula Magna");
        eventoUTN.asignarSala(salaMagna);

        // d. Crear actividades para cada evento del tipo Charla y/o Taller
        System.out.println("--- d. Creando Actividades (Polimorfismo) ---");
        // Ahora pasamos un cuarto parámetro ("Charla" o "Taller")
        eventoUTN.crearActividad(101, "Charla de Inteligencia Artificial", 2, "Charla");
        eventoUTN.crearActividad(102, "Taller de Java POO", 15, "Taller");

        // e. Inscribir estudiantes en cada actividad
        System.out.println("--- e. Inscribiendo alumnos ---");
        Actividad charla = eventoUTN.getActividades().get(0);
        Actividad taller = eventoUTN.getActividades().get(1);

        charla.inscribir(est1);
        charla.inscribir(est2);
        charla.inscribir(est3); // Este rebotará por el cupo máximo de 2

        taller.inscribir(est3);

        // f. Mostrar el resumen de datos (recorriendo actividades de forma polimórfica)
        System.out.println("\n--- f. Resumen de Datos ---");
        // Al ejecutar esto, verás que llama a "mostrarIdentificacion()" de Actividad,
        // pero dinámicamente sabe si es Charla o Taller para imprimir el "Tipo"
        eventoUTN.mostrarDatos();

        System.out.println("\n--- Detalle de Inscriptos ---");
        charla.mostrarInscripciones();
        taller.mostrarInscripciones();

        // g. Mostrar el total de eventos creados
        System.out.println("\n--- g. Total de eventos creados ---");
        System.out.println("El sistema tiene registrados " + EventoUniversitario.getCantidadEventos() + " evento(s).");
    }
}
