package utn.eventos;

import utn.eventos.actividades.Charla;
import utn.eventos.actividades.Actividad;
import utn.eventos.actividades.Taller;
import utn.eventos.modelo.EventoUniversitario;
import utn.eventos.actividades.Curso;
import utn.eventos.modelo.Sala;
import utn.eventos.excepciones.CupoExcedidoException;
import utn.eventos.certificacion.Certificable;
import utn.eventos.hilos.EnvioTicketsThread; // Importación del Hilo para el Ejercicio 4
import java.util.List;

public class App {

    public static void main(String[] args) {

        System.out.println("--- a. Registrando Estudiantes ---");
        Estudiante est1 = new Estudiante("51968", "Juan Perez");
        Estudiante est2 = new Estudiante("52000", "Maria Gomez");
        Estudiante est3 = new Estudiante("52001", "Carlos Lopez");

        System.out.println("--- b. Creando Evento Universitario ---");
        EventoUniversitario eventoUTN = new EventoUniversitario("E001", "Jornadas de Sistemas 2026", 1500.0, false);

        System.out.println("--- c. Asignando Sala ---");
        Sala salaMagna = new Sala(1, "Aula Magna");
        eventoUTN.asignarSala(salaMagna);

        System.out.println("--- d. Creando Actividades (Polimorfismo e Interfaces) ---");
        eventoUTN.crearActividad(101, "Charla de Inteligencia Artificial", 2, "Charla");
        eventoUTN.crearActividad(102, "Taller de Java POO", 15, "Taller");
        eventoUTN.crearActividad(103, "Curso de Spring Boot", 20, "Curso");

        Actividad charla = eventoUTN.getActividades().get(0);
        Actividad taller = eventoUTN.getActividades().get(1);
        Actividad curso = eventoUTN.getActividades().get(2);

        System.out.println("\n--- Iniciando Flujo de Pruebas TP2 (Excepciones y Persistencia) ---");
        try {
            System.out.println("\n1. Intentando inscripciones exitosas...");
            charla.inscribir(est1);
            charla.inscribir(est2);
            taller.inscribir(est3);
            curso.inscribir(est1);
            curso.inscribir(est3);
            System.out.println("Inscripciones iniciales realizadas con éxito.");


            for (Actividad act : eventoUTN.getActividades()) {
                for (Inscripcion ins : act.getInscripciones()) {
                    ins.emitirTicket();
                }
            }
            System.out.println("Tickets generados para los alumnos inscriptos.");

            System.out.println("\n2. Intentando persistir evento en archivo...");
            if (eventoUTN.persistirEvento()) {
                System.out.println("Evento serializado y guardado correctamente.");
            }

            System.out.println("\n3. Intentando recuperar evento desde archivo...");
            EventoUniversitario eventoRecuperado = EventoUniversitario.recuperarEvento("E001");
            if (eventoRecuperado != null) {
                System.out.println("Evento recuperado con éxito: " + eventoRecuperado.getTitulo());
            }

            System.out.println("\n4. Intentando inscripción que excede el cupo...");
            charla.inscribir(est3);

            System.out.println("Esto no debería imprimirse.");

        } catch (CupoExcedidoException e) {
            System.err.println("EXCEPCIÓN ATRAPADA: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error general inesperado: " + e.getMessage());
        } finally {
            System.out.println("\n--- Ejecutando bloque FINALLY ---");
            System.out.println("Operación de prueba de inscripciones, guardado y lectura finalizada.");
        }


        System.out.println("\n--- Iniciando envío de tickets en segundo plano ---");
        EnvioTicketsThread hiloTickets = new EnvioTicketsThread(eventoUTN);
        hiloTickets.start(); // Ejecuta el proceso en paralelo

        System.out.println("\n--- Resumen de Datos ---");
        eventoUTN.mostrarDatos();

        System.out.println("\n--- Detalle de Inscriptos ---");
        charla.mostrarInscripciones();
        taller.mostrarInscripciones();
        curso.mostrarInscripciones();

        System.out.println("\n--- Emisión de Certificados ---");
        for (Actividad actividad : eventoUTN.getActividades()) {

            if (actividad instanceof Certificable) {
                Certificable actCertificable = (Certificable) actividad;
                System.out.println("\n* Certificados para " + actividad.getTipo() + ": " + actividad.getTitulo() + " *");

                if (actividad.getInscripciones().isEmpty()) {
                    System.out.println("- No hay alumnos para certificar.");
                } else {
                    for (Inscripcion ins : actividad.getInscripciones()) {
                        String textoCertificado = actCertificable.generarCertificado(ins.getEstudiante());
                        System.out.println(textoCertificado);
                    }
                }
            } else {
                System.out.println("\n* La actividad '" + actividad.getTitulo() + "' es de tipo " + actividad.getTipo() + " y NO emite certificados. *");
            }
        }

        System.out.println("\n--- Total de eventos creados ---");
        System.out.println("El sistema tiene registrados " + EventoUniversitario.getCantidadEventos() + " evento(s).");

        System.out.println("\n--- Ejercicio 3: Filtrado con Genéricos y Wildcards ---");

        List<Charla> listaCharlas = eventoUTN.filtrarActividadesPorTipo(Charla.class);
        List<Taller> listaTalleres = eventoUTN.filtrarActividadesPorTipo(Taller.class);
        List<Curso> listaCursos = eventoUTN.filtrarActividadesPorTipo(Curso.class);

        System.out.println("Cantidad de Charlas: " + listaCharlas.size());
        System.out.println("Cantidad de Talleres: " + listaTalleres.size());
        System.out.println("Cantidad de Cursos: " + listaCursos.size());

        System.out.println("\nCostos de materiales por tipo de actividad:");
        System.out.println("- Charlas: $" + eventoUTN.calcularCostoMateriales(listaCharlas));
        System.out.println("- Talleres: $" + eventoUTN.calcularCostoMateriales(listaTalleres));
        System.out.println("- Cursos: $" + eventoUTN.calcularCostoMateriales(listaCursos));

        System.out.println("\n[HILO PRINCIPAL] Fin del método main. La consola seguirá activa hasta que el hilo secundario termine de enviar los correos.");
    }
}