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

}
