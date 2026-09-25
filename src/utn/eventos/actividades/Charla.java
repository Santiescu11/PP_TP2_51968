package utn.eventos.actividades;

public class Charla extends Actividad {
    private String disertante;

    public Charla(int id, String titulo, int cupoMaximo, String disertante) {
        // Llamada al constructor de la superclase (Actividad)
        super(id, titulo, cupoMaximo);
        this.disertante = disertante;
    }

    // Sobrescribimos el método abstracto (la charla es gratis)
    @Override
    public double calcularCostoMateriales() {
        return 0.0;
    }

    @Override
    public String getTipo() {
        return "Charla";
    }
}