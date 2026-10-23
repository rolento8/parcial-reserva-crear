package model;
import org.junit.jupiter.api.Test;
import reserva.exception.*;
import reserva.modelo.*;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ReservaTest {

    @Test
    void debeCrearReservaActivaExitosamente() {
        UUID id = UUID.randomUUID();
        Reserva reserva = Reserva.instance(id, "mail@test.com", LocalDate.now(), 2);

        assertNotNull(reserva);
        assertEquals(id, reserva.getId());
        assertEquals("mail@test.com", reserva.getEmailContacto());
        assertEquals(EstadoReserva.ACTIVA, reserva.getEstado());
    }

    @Test
    void debeLanzarExcepcionSiIdEsNulo() {
        assertThrows(InvalidReservationException.class,
                () -> Reserva.instance(null, "mail@test.com", LocalDate.now(), 2));
    }

    @Test
    void debeLanzarExcepcionSiFechaEsNula() {
        assertThrows(InvalidReservationException.class,
                () -> Reserva.instance(UUID.randomUUID(), "mail@test.com", null, 2));
    }

    @Test
    void debeLanzarExcepcionSiEmailEsNuloOVacio() {
        assertThrows(InvalidReservationException.class,
                () -> Reserva.instance(UUID.randomUUID(), null, LocalDate.now(), 2));
        assertThrows(InvalidReservationException.class,
                () -> Reserva.instance(UUID.randomUUID(), "   ", LocalDate.now(), 2));
    }

    @Test
    void debeLanzarExcepcionSiCantidadPersonasEsInvalida() {
        assertThrows(InvalidReservationException.class,
                () -> Reserva.instance(UUID.randomUUID(), "mail@test.com", LocalDate.now(), 0));
    }

    @Test
    void debeCancelarReservaActiva() {
        Reserva reserva = Reserva.instance(UUID.randomUUID(), "mail@test.com", LocalDate.now(), 2);
        reserva.cancelar();
        assertEquals(EstadoReserva.CANCELADA, reserva.getEstado());
    }

    @Test
    void debeLanzarExcepcionAlCancelarReservaYaCancelada() {
        Reserva reserva = Reserva.instance(UUID.randomUUID(), "mail@test.com", LocalDate.now(), 2);
        reserva.cancelar();

        assertThrows(InvalidTransitionException.class, reserva::cancelar);
    }
}