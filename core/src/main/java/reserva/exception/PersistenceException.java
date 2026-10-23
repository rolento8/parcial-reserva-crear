package reserva.exception;

public class PersistenceException extends RuntimeException {

    // Constructor que acepta el mensaje y la causa original (el que requiere la línea 31)
    public PersistenceException(String message, Throwable cause) {
        super(message, cause);
    }

    // Opcional: Podés dejar también el constructor de solo String por si lo necesitás en otro momento
    public PersistenceException(String message) {
        super(message);
    }
}