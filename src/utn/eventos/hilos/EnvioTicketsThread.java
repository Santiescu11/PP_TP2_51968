package utn.eventos.hilos;

import utn.eventos.modelo.EventoUniversitario;
import utn.eventos.actividades.Actividad;
import utn.eventos.Inscripcion;

public class EnvioTicketsThread extends Thread {

    private EventoUniversitario evento;

    public EnvioTicketsThread(EventoUniversitario evento) {
        this.evento = evento;
    }

    // El método run() es el "main" de este hilo secundario
    @Override
    public void run() {
        System.out.println("\n[HILO SECUNDARIO] Iniciando el servidor de correos. Buscando tickets para enviar...");


        for (Actividad act : evento.getActividades()) {
            for (Inscripcion ins : act.getInscripciones()) {
                if (ins.getTicket() != null) {

                    ins.getTicket().enviarTicket();
                }
            }
        }
        System.out.println("[HILO SECUNDARIO] Todos los tickets fueron enviados. Apagando servidor de correos.");
    }
}