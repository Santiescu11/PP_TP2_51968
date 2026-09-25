package utn.eventos.certificacion;

import utn.eventos.Estudiante;

public interface Certificable {
    String ENTIDAD_EMISORA = "UTN - FRM";

    // Los métodos en una interfaz son automáticamente public y abstract
    String generarCertificado(Estudiante estudiante);
}
