package reserva.usecase;

import reserva.exception.PersistenceException;
import reserva.exception.ReservationAlreadyExistsException;
import reserva.input.CreateReservationInput;
import reserva.modelo.Reserva;
import reserva.output.ReservationRepository;

import java.time.LocalDate;
import java.util.UUID;

public class CreateReservationUseCase implements CreateReservationInput {

    private final ReservationRepository repository;

    public CreateReservationUseCase(ReservationRepository repository) {
        this.repository = repository;
    }

    @Override
    public Reserva execute(UUID id, String emailContacto, LocalDate fecha, int cantidadPersonas) {
        Reserva reserva = Reserva.instance(id, emailContacto, fecha, cantidadPersonas);

        if (repository.existsActiveReservation(emailContacto, fecha)) {
            throw new ReservationAlreadyExistsException("Ya existe una reserva activa para el correo " + emailContacto + " en esta fecha");
        }

        try {
            repository.save(reserva);
        } catch (Exception e) {
            throw new PersistenceException("Error de persistencia al intentar guardar la reserva", e);
        }

        return reserva;
    }
}