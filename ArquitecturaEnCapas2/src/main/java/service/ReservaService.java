
package service;

import dao.ReservaDAO;
import java.time.LocalDateTime;
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
                reservas.add(new Reserva(codigo, pasajero, vehiculo,fechaCreacion, fechaViaje, estado));
            }
        }
    }

    public String crearReserva(String cedulaPasajero, String placaVehiculo, String fechaViaje) {
        Pasajero pasajero = personaService.buscarPasajeroPorCedula(cedulaPasajero);
        if (pasajero == null)
            return "ERROR: No existe pasajero con cedula " + cedulaPasajero;

        Vehiculo vehiculo = vehiculoService.buscarPorPlaca(placaVehiculo);
        if (vehiculo == null)
            return "ERROR: No existe vehiculo con placa " + placaVehiculo;
   
        for (Reserva r : reservas) {
            if (r.isActiva()
                    && r.getPasajero().getCedula().equals(cedulaPasajero)
                    && r.getVehiculo().getPlaca().equals(placaVehiculo)
                    && r.getFechaViaje().equals(fechaViaje)) {
                return "ERROR: El pasajero ya tiene una reserva activa en ese vehiculo para la fecha " + fechaViaje;
            }
        }

        int ocupados = vehiculo.getPasajerosActuales() + contarReservasActivas(placaVehiculo);
        if (ocupados >= vehiculo.getCapacidadMaxima()) {
            return "ERROR: El vehiculo " + placaVehiculo + " no tiene cupos disponibles (capacidad llena con tickets + reservas).";
        }

        String fechaCreacion = LocalDateTime.now().format(FMT);
        String codigo = "RS" + (reservas.size() + 1);

        Reserva reserva = new Reserva(codigo, pasajero, vehiculo, fechaCreacion, fechaViaje);
        reservas.add(reserva);
        reservaDAO.guardarReserva(reserva);

        return "Reserva creada exitosamente.\n"
                + "Codigo: " + codigo + "\n"
             + "Recuerde: tiene 24 horas para convertirla en ticket antes de que expire.";
    }

    public String cancelarReserva(String codigo) {
        Reserva r = buscarPorCodigo(codigo);
        if (r == null)
            return "ERROR: No existe reserva con codigo " + codigo;
        if (!r.isActiva())
            return "ERROR: La reserva " + codigo + " no esta activa (estado: " + r.getEstado() + ")";

        r.cancelar();
        reservaDAO.reescribirReservas(reservas);
        return "Reserva " + codigo + " cancelada. El cupo queda disponible.";
    }
    
    public String convertirEnTicket(String codigo, String origen, String destino) {
        Reserva r = buscarPorCodigo(codigo);
        
        if (r == null)
            return "ERROR: No existe reserva con codigo " + codigo;
        if (!r.isActiva())
            return "ERROR: La reserva " + codigo + " no esta activa (estado: " + r.getEstado() + ")";
        if (r.estaVencida())
            return "ERROR: La reserva " + codigo + " esta vencida (mas de 24h). No se puede convertir.";

        String resultado = ticketService.venderTicket(
                r.getPasajero().getCedula(),
                r.getVehiculo().getPlaca(),
                origen, destino);

        if (resultado.startsWith("ERROR")) return resultado;
        r.convertir();
        reservaDAO.reescribirReservas(reservas);

        return "Reserva " + codigo + " convertida en ticket exitosamente.\n" + resultado;
    }

    public int verificarVencidas() {
        int canceladas = 0;
        for (Reserva r : reservas) {
            if (r.isActiva() && r.estaVencida()) {
                r.cancelar();
                canceladas++;
            }
        }
        if (canceladas > 0) {
            reservaDAO.reescribirReservas(reservas);
        }
        
        return canceladas;
    }

}
