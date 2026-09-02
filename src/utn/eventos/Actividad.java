package utn.eventos;

import java.util.List;
import java.util.ArrayList;

public abstract class Actividad {
    private int id;
    private String titulo;
    private int cupoMaximo;

    public final int CUPO_MINIMO = 10; // Le asignamos un valor arbitrario de 10

    // Colección para guardar las inscripciones
    private List<Inscripcion> inscripciones;

    public Actividad(int id, String titulo, int cupoMaximo) {
        this.id = id;
        this.titulo = titulo;
        this.cupoMaximo = cupoMaximo;

        this.inscripciones = new ArrayList<>();
    }
    // Método para inscribir a los estudiantes
    public Inscripcion inscribir(Estudiante estudiante) {
        // Controlamos que no se supere el cupo máximo
        if (this.inscripciones.size() < this.cupoMaximo) {
            // Creamos el objeto Inscripcion que relaciona a la actividad con el alumno
            Inscripcion nuevaInscripcion = new Inscripcion(estudiante);
            // Agregamos la inscripción a nuestra lista
            this.inscripciones.add(nuevaInscripcion);

            return nuevaInscripcion;
        } else {
            System.out.print("No se pudo inscribir a " + estudiante.getNombre() + ". Cupo lleno en la actividad: " + this.titulo);
            return null;
        }
    }

    // Método para mostrar quiénes están inscriptos
    public void mostrarInscripciones() {
        System.out.println("--- Inscriptos en " + this.titulo + "---");
        // Usamos un bucle for each para recorrer la lista
        for (Inscripcion inscripcion : this.inscripciones) {
            System.out.print("- Alumno: " + inscripcion.getEstudiante().getNombre() + " (Fecha: " + inscripcion.getFecha() + ")");
        }
    }
    public String getTitulo() {
        return titulo;
    }
    // Método FINAL: no puede ser sobrescrito (redefinido) por las subclases
    public final void mostrarIdentificacion() {
        System.out.println("ID: " + this.id + " | Título: " + this.titulo + " | Tipo: " + this.getTipo());
    }

    // Métodos ABSTRACTOS: declaran un comportamiento pero delegan la lógica a las clases hijas
    public abstract double calcularCostoMateriales();
    public abstract String getTipo();
}

