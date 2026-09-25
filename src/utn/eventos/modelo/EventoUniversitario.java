package utn.eventos.modelo;

import utn.eventos.actividades.Actividad;
import utn.eventos.actividades.Charla;
import utn.eventos.actividades.Curso;
import utn.eventos.actividades.Taller;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

// Agrego "implements Serializable" para poder guardar el objeto en un archivo
public class EventoUniversitario implements Serializable {
    private final String id;
    private String titulo;
    private double costoBase;
    private boolean gratuito;
    private static int cantidadEventos;

    private Sala sala;
    private List<Actividad> actividades;

    public EventoUniversitario(String id, String titulo, double costoBase, boolean gratuito) {
        this.id = id;
        this.titulo = titulo;
        this.costoBase = costoBase;
        this.gratuito = gratuito;
        this.actividades = new ArrayList<>();
        cantidadEventos++;
    }

    public EventoUniversitario(EventoUniversitario otro) {
        this.id = otro.id;
        this.titulo = otro.titulo;
        this.costoBase = otro.costoBase;
        this.gratuito = otro.gratuito;
        this.actividades = new ArrayList<>();
        cantidadEventos++;
    }

    // === (Impuestos y Polimorfismo) ===
    public double calcularCostoEstimado() {
        if (this.gratuito) {
            return 0.0;
        } else {
            double costoTotalActividades = 0.0;

            for (Actividad act : this.actividades) {
                costoTotalActividades += act.calcularCostoMateriales();
            }

            return (this.costoBase + costoTotalActividades) * 1.21;
        }
    }

    // === (Creación de Subclases) ===
    public void crearActividad(int id, String titulo, int cupo, String tipo) {
        if (tipo.equalsIgnoreCase("Charla")) {
            Actividad nuevaCharla = new Charla(id, titulo, cupo, "A designar");
            this.actividades.add(nuevaCharla);
        } else if (tipo.equalsIgnoreCase("Taller")) {
            Actividad nuevoTaller = new Taller(id, titulo, cupo, true);
            this.actividades.add(nuevoTaller);
        } else if (tipo.equalsIgnoreCase("Curso")) {
            // Asignamos un nivel 1 por defecto para simplificar
            Actividad nuevoCurso = new Curso(id, titulo, cupo, 1);
            this.actividades.add(nuevoCurso);
        } else {
            System.out.println("Tipo de actividad no reconocido.");
        }
    }

    public void asignarSala(Sala sala) {
        this.sala = sala;
    }

    public List<Actividad> getActividades() {
        return actividades;
    }

    public void mostrarDatos() {
        System.out.println("=== Datos del Evento ===");
        System.out.println("ID: " + this.id + " | Título: " + this.titulo);

        if (this.sala != null) {
            System.out.println("Sala asignada: " + this.sala.getNombre());
        } else {
            System.out.println("Sala asignada: Aún sin asignar");
        }

        System.out.println("Costo Base sin impuestos: $" + this.costoBase);
        System.out.println("Costo Total Estimado (+21%): $" + this.calcularCostoEstimado());

        System.out.println("Actividades planificadas:");
        if (this.actividades.isEmpty()) {
            System.out.println("- No hay actividades registradas.");
        } else {
            for (Actividad act : this.actividades) {
                act.mostrarIdentificacion();
            }
        }
        System.out.println("========================");
    }

    public static int getCantidadEventos() {
        return cantidadEventos;
    }

    // Método para guardar el evento (serialización)
    public boolean persistirEvento() {
        String nombreArchivo = this.id + ".dat";
        // try-with-resources cierra el flujo automáticamente
        try (FileOutputStream fos = new FileOutputStream(nombreArchivo);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            oos.writeObject(this);
            return true;

        } catch (FileNotFoundException e) {
            System.err.println("Error de archivo (Persistencia): No se pudo crear el archivo. " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error de E/S (Persistencia): Ocurrió un problema al escribir los datos. " + e.getMessage());
        }
        return false;
    }

    // Método para leer el evento (deserialización)
    public static EventoUniversitario recuperarEvento(String id) {
        String nombreArchivo = id + ".dat";
        try (FileInputStream fis = new FileInputStream(nombreArchivo);
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            return (EventoUniversitario) ois.readObject();

        } catch (FileNotFoundException e) {
            System.err.println("Error de archivo (Recuperación): No se encontró el evento con ID '" + id + "'.");
        } catch (ClassNotFoundException e) {
            System.err.println("Error de clase (Recuperación): El archivo no corresponde a un EventoUniversitario. " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error de E/S (Recuperación): Ocurrió un problema al leer los datos. " + e.getMessage());
        }
        return null;
    }

    public String getTitulo() {
        return this.titulo;
    }



    // Método parametrizado acotado para filtrar la lista por tipo (Charla, Taller o Curso)
    public <T extends Actividad> List<T> filtrarActividadesPorTipo(Class<T> tipo) {
        List<T> actividadesFiltradas = new ArrayList<>();
        for (Actividad act : this.actividades) {
            if (tipo.isInstance(act)) {
                actividadesFiltradas.add(tipo.cast(act));
            }
        }
        return actividadesFiltradas;
    }

    // Método con wildcard para calcular el costo de materiales de una lista de actividades
    public double calcularCostoMateriales(List<? extends Actividad> listaActividades) {
        double costoTotal = 0.0;
        for (Actividad act : listaActividades) {
            costoTotal += act.calcularCostoMateriales();
        }
        return costoTotal;
    }
}