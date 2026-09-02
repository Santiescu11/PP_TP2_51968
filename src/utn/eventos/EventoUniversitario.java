package utn.eventos;

import java.util.ArrayList;
import java.util.List;

public class EventoUniversitario {
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
            // Recorremos las actividades, delegando polimórficamente el cálculo de materiales
            for (Actividad act : this.actividades) {
                costoTotalActividades += act.calcularCostoMateriales();
            }
            // Retornamos el total más el 21% de impuestos
            return (this.costoBase + costoTotalActividades) * 1.21;
        }
    }

    // === (Creación de Subclases) ===
    public void crearActividad(int id, String titulo, int cupo, String tipo) {
        if (tipo.equalsIgnoreCase("Charla")) {
            // Instanciamos la subclase Charla (ponemos un disertante genérico por ahora)
            Actividad nuevaCharla = new Charla(id, titulo, cupo, "A designar");
            this.actividades.add(nuevaCharla);
        } else if (tipo.equalsIgnoreCase("Taller")) {
            // Instanciamos la subclase Taller (suponemos que requiere notebook por defecto)
            Actividad nuevoTaller = new Taller(id, titulo, cupo, true);
            this.actividades.add(nuevoTaller);
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
}
