package usecase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reserva.exception.PersistenceException;
import reserva.exception.ReservationAlreadyExistsException;
import reserva.modelo.Reserva;
import reserva.output.ReservationRepository;
import reserva.usecase.CreateReservationUseCase;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreateReservationUseCaseTest {

    private ReservationRepository repository;
    private CreateReservationUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = mock(ReservationRepository.class);
        useCase = new CreateReservationUseCase(repository);
    }

    @Test
    void debeCrearYGuardarReservaExitosamente() {
        UUID id = UUID.randomUUID();
        LocalDate fecha = LocalDate.now();
        String email = "juan@test.com";

        when(repository.existsActiveReservation(email, fecha)).thenReturn(false);

        Reserva creada = useCase.execute(id, email, fecha, 2);

        assertNotNull(creada);
        assertEquals(id, creada.getId());
        verify(repository, times(1)).save(any(Reserva.class));
    }

    @Test
    void debeLanzarExcepcionSiYaExisteReservaActiva() {
        UUID id = UUID.randomUUID();
        LocalDate fecha = LocalDate.now();
        String email = "juan@test.com";

        when(repository.existsActiveReservation(email, fecha)).thenReturn(true);

        assertThrows(ReservationAlreadyExistsException.class,
                () -> useCase.execute(id, email, fecha, 2));

        verify(repository, never()).save(any(Reserva.class));
    }

    @Test
    void debeLanzarExcepcionSiFallaPersistencia() {
        UUID id = UUID.randomUUID();
        LocalDate fecha = LocalDate.now();
        String email = "juan@test.com";

        when(repository.existsActiveReservation(email, fecha)).thenReturn(false);
        doThrow(new RuntimeException("DB down")).when(repository).save(any(Reserva.class));

        assertThrows(PersistenceException.class,
                () -> useCase.execute(id, email, fecha, 2));
    }
}