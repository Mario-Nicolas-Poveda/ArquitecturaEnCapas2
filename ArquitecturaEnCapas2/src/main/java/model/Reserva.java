package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Jose
 */
public class Reserva implements Imprimible
{

    public static final String ACTIVA = "Activa";
    public static final String CONVERTIDA = "Convertida";
    public static final String CANCELADA = "Cancelada";

    private String codigo;
    private Pasajero pasajero;
    private Vehiculo vehiculo;
    private String fechaCreacion;
    private String fechaViaje;
    private String estado;
    private static final DateTimeFormatter FMT
            = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public Reserva(String codigo, Pasajero pasajero, Vehiculo vehiculo,
            String fechaCreacion, String fechaViaje)
    {
        this.codigo = codigo;
        this.pasajero = pasajero;
        this.vehiculo = vehiculo;
        this.fechaCreacion = fechaCreacion;
        this.fechaViaje = fechaViaje;
        this.estado = ACTIVA;
    }

    public Reserva(String codigo, Pasajero pasajero, Vehiculo vehiculo,
            String fechaCreacion, String fechaViaje, String estado)
    {
        this(codigo, pasajero, vehiculo, fechaCreacion, fechaViaje);
        this.estado = estado;
    }

    public boolean estaVencida()
    {
        if (!estado.equals(ACTIVA))
        {
            return false;
        }
        try
        {
            LocalDateTime creacion = LocalDateTime.parse(fechaCreacion, FMT);
            LocalDateTime ahora = LocalDateTime.now();
            return java.time.Duration.between(creacion, ahora).toHours() >= 24;
        } catch (Exception e)
        {
            return false;
        }
    }

    public void cancelar()
    {
        this.estado = CANCELADA;
    }

    public void convertir()
    {
        this.estado = CONVERTIDA;
    }

    public boolean isActiva()
    {
        return estado.equals(ACTIVA);
    }

    public boolean isConvertida()
    {
        return estado.equals(CONVERTIDA);
    }

    public boolean isCancelada()
    {
        return estado.equals(CANCELADA);
    }

    public String getCodigo()
    {
        return codigo;
    }

    public Pasajero getPasajero()
    {
        return pasajero;
    }

    public Vehiculo getVehiculo()
    {
        return vehiculo;
    }

    public String getFechaCreacion()
    {
        return fechaCreacion;
    }

    public String getFechaViaje()
    {
        return fechaViaje;
    }

    public String getEstado()
    {
        return estado;
    }

}
