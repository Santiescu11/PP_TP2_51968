package utn.eventos.actividades;

import utn.eventos.Estudiante;
import utn.eventos.Inscripcion;
import utn.eventos.excepciones.CupoExcedidoException;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

public abstract class Actividad implements Serializable {
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

    // Método para inscribir a los estudiantes modificado con throw
    public Inscripcion inscribir(Estudiante estudiante) throws CupoExcedidoException {
        // Invertimos la lógica: primero controlamos el error y lanzamos la excepción
        if (this.inscripciones.size() >= this.cupoMaximo) {
            throw new CupoExcedidoException("No se pudo inscribir a " + estudiante.getNombre() + ". Cupo lleno en la actividad: " + this.titulo);
        }

        // Si no hay error, el código continúa normalmente
        Inscripcion nuevaInscripcion = new Inscripcion(estudiante);
        this.inscripciones.add(nuevaInscripcion);

        return nuevaInscripcion;
    }

    // Método para mostrar quiénes están inscriptos
    public void mostrarInscripciones() {
        System.out.println("\n--- Inscriptos en " + this.titulo + " ---");
        // Usamos un bucle for each para recorrer la lista
        for (Inscripcion inscripcion : this.inscripciones) {
            System.out.println("- Alumno: " + inscripcion.getEstudiante().getNombre() + " (Fecha: " + inscripcion.getFecha() + ")");
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

    public List<Inscripcion> getInscripciones() {
        return this.inscripciones;
    }
}
