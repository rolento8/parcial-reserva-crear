package reserva.input;

import reserva.modelo.Reserva;
import java.time.LocalDate;
import java.util.UUID;

public interface CreateReservationInput {
    Reserva execute(UUID id, String emailContacto, LocalDate fecha, int cantidadPersonas);
}