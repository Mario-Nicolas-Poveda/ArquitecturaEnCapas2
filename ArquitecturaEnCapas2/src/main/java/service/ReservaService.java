
package service;

import dao.ReservaDAO;
import java.time.format.DateTimeFormatter;
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

    
}
