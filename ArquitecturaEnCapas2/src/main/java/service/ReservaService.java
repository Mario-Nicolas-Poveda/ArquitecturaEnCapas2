
package service;

import dao.ReservaDAO;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import model.*;
/**
 *
 * @author alex
 */
public class ReservaService {
    private ReservaDAO      reservaDAO;
    private VehiculoService vehiculoService;
    private PersonaService  personaService;
    private TicketService   ticketService;
    private List<Reserva>   reservas;

    private static final DateTimeFormatter FMT = 
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public ReservaService(VehiculoService vehiculoService,PersonaService personaService,TicketService ticketService) {
        this.reservaDAO      = new ReservaDAO();
        this.vehiculoService = vehiculoService;
        this.personaService  = personaService;
        this.ticketService   = ticketService;
        this.reservas        = new ArrayList<>();
        cargarReservas();
        verificarVencidas();
    }

    private void cargarReservas() {
        List<String[]> lineas = reservaDAO.cargarLineas();
        for (String[] p : lineas) {
            String codigo        = p[0];
            String cedula        = p[1];
            String placa         = p[2];
            String fechaCreacion = p[3];
            String fechaViaje    = p[4];
            String estado        = p[5];
            
            Pasajero pasajero = personaService.buscarPasajeroPorCedula(cedula);
            Vehiculo vehiculo = vehiculoService.buscarPorPlaca(placa);

            if (pasajero != null && vehiculo != null) {
                reservas.add(new Reserva(codigo, pasajero, vehiculo,
                        fechaCreacion, fechaViaje, estado));
            }
        }
    }

}
