package reserva.modelo;

import reserva.exception.InvalidReservationException;
import reserva.exception.InvalidTransitionException;

import java.time.LocalDate;
import java.util.UUID;

public class Reserva {
    private UUID id;
    private String emailContacto;
    private LocalDate fecha;
    private int cantidadPersonas;
    private EstadoReserva estado;

    private Reserva(UUID id, String emailContacto, LocalDate fecha, int cantidadPersonas) {
        this.id = id;
        this.emailContacto = emailContacto;
        this.fecha = fecha;
        this.cantidadPersonas = cantidadPersonas;
        this.estado = EstadoReserva.ACTIVA;
    }

    public static Reserva instance(UUID id, String emailContacto, LocalDate fecha, int cantidadPersonas) {
        if (id == null){
            throw new InvalidReservationException("El ID es obligatorio");
        }
        if (fecha == null) {
            throw new InvalidReservationException("La fecha es obligatoria");
        }
        if (emailContacto == null || emailContacto.trim().isEmpty()) {
            throw new InvalidReservationException("El correo de contacto es obligatorio");
        }
        if (cantidadPersonas <= 0) {
            throw new InvalidReservationException("La cantidad de personas debe ser mayor que cero");
        }
        return new Reserva(id, emailContacto, fecha, cantidadPersonas);
    }

    public void cancelar() {
        if (this.estado == EstadoReserva.CANCELADA) {
            throw new InvalidTransitionException("La reserva ya se encuentra cancelada");
        }
        this.estado = EstadoReserva.CANCELADA;
    }

    public UUID getId() {
        return id;
    }
    public String getEmailContacto() {
        return emailContacto;
    }
    public LocalDate getFecha() {
        return fecha;
    }
    public int getCantidadPersonas() {
        return cantidadPersonas;
    }
    public EstadoReserva getEstado() {
        return estado;
    }
}