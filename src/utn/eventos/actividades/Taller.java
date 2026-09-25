package utn.eventos.actividades;

import utn.eventos.Estudiante;
import utn.eventos.certificacion.Certificable;


public class Taller extends Actividad implements Certificable {
    private boolean requiereNotebook;

    public Taller(int id, String titulo, int cupoMaximo, boolean requiereNotebook) {
        super(id, titulo, cupoMaximo);
        this.requiereNotebook = requiereNotebook;
    }

    @Override
    public double calcularCostoMateriales() {
        return 1500.0;
    }

    @Override
    public String getTipo() {
        return "Taller";
    }

    // Implementación del método de la interfaz
    @Override
    public String generarCertificado(Estudiante estudiante) {
        return "CERTIFICADO DE ASISTENCIA: Se certifica que " + estudiante.getNombre() +
                " participó en el Taller '" + this.getTitulo() +
                "'. Emitido por: " + ENTIDAD_EMISORA;
    }
}