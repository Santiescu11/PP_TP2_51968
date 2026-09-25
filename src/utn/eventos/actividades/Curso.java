package utn.eventos.actividades;

import utn.eventos.Estudiante;
import utn.eventos.certificacion.Certificable;

public class Curso extends Actividad implements Certificable {
    private int nivel;

    public Curso(int id, String titulo, int cupoMaximo, int nivel) {
        super(id, titulo, cupoMaximo);
        this.nivel = nivel;
    }

    @Override
    public double calcularCostoMateriales() {
        return 500.0 * this.nivel; // Costo simulado basado en el nivel
    }

    @Override
    public String getTipo() {
        return "Curso";
    }

    // Implementación obligatoria del método de la interfaz Certificable
    @Override
    public String generarCertificado(Estudiante estudiante) {
        return "CERTIFICADO DE APROBACIÓN: Se certifica que " + estudiante.getNombre() +
                " completó el Curso '" + this.getTitulo() + "' (Nivel " + this.nivel +
                "). Emitido por: " + ENTIDAD_EMISORA;
    }
}