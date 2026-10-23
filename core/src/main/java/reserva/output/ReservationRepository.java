package reserva.output;

import reserva.modelo.Reserva;
import java.time.LocalDate;

public interface ReservationRepository {
    boolean existsActiveReservation(String emailContacto, LocalDate fecha);
    void save(Reserva reserva);
}