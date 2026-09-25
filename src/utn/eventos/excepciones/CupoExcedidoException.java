package utn.eventos.excepciones;

public class CupoExcedidoException extends Exception {
    public CupoExcedidoException(String mensaje) {
        super(mensaje);
    }
}