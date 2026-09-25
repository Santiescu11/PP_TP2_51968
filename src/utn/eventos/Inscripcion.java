package utn.eventos;

import java.time.LocalDate;
import java.io.Serializable;

public class Inscripcion implements Serializable {
    private LocalDate fecha;
    private String estado;
    private Estudiante estudiante;
    private TicketDeAcceso ticket;

    public Inscripcion(Estudiante estudiante) {
        this.estudiante = estudiante;
        this.fecha = LocalDate.now();
        this.estado = "Confirmada";
    }

    public void emitirTicket() {
        if (this.estado.equalsIgnoreCase("Confirmada")) {
            this.ticket = new TicketDeAcceso();
        } else {
            System.out.println("No se puede emitir ticket. La inscripción de " + estudiante.getNombre() + " no está confirmada.");
        }
    }


    public Estudiante getEstudiante() {
        return estudiante;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public TicketDeAcceso getTicket() {
        return ticket;
    }

    public class TicketDeAcceso implements Serializable {
        private String nroTicket;

        public TicketDeAcceso() {
            // Generamos un número aleatorio para simular el código del ticket
            this.nroTicket = "TKT-" + (int)(Math.random() * 10000);
        }

        public void enviarTicket() {
            System.out.println("[HILO SECUNDARIO] -> Enviando " + this.nroTicket + " al correo de " + estudiante.getNombre() + "...");
            try {
                // Simulamos una demora de red de 2 segundos para evidenciar el paralelismo
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                System.err.println("Error en el envío del ticket.");
            }
            System.out.println("[HILO SECUNDARIO] -> " + this.nroTicket + " entregado a " + estudiante.getNombre() + " con éxito.");
        }
    }
}






