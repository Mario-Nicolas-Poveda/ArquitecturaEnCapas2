
package service;

/**
 *
 * @author alex
 */

import dao.TicketDAO;
import model.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TicketService {
    private TicketDAO ticketDAO;
    private VehiculoService vehiculoService;
    private PersonaService personaService;
    private List<Ticket> tickets;

    public TicketService(VehiculoService vehiculoService, PersonaService personaService) {
        this.ticketDAO = new TicketDAO();
        this.vehiculoService = vehiculoService;
        this.personaService = personaService;
        this.tickets = new ArrayList<>();
        cargarTickets();
    }

    private void cargarTickets() {
        List<String[]> lineas = ticketDAO.cargarLineas();
        for (String[] partes : lineas) {
            String id = partes[0];
            String cedulaPasajero = partes[1];
            String placaVehiculo  = partes[2];
            String fecha   = partes[3];
            String origen  = partes[4];
            String destino = partes[5];
            Pasajero p = personaService.buscarPasajeroPorCedula(cedulaPasajero);
            Vehiculo v = vehiculoService.buscarPorPlaca(placaVehiculo);
            if (p != null && v != null) tickets.add(new Ticket(id, p, v, fecha, origen, destino));
        }
    }

    public String venderTicket(String cedulaPasajero, String placaVehiculo,String origen, String destino) {
        
        Pasajero pasajero = personaService.buscarPasajeroPorCedula(cedulaPasajero);
        
        if (pasajero == null) return "ERROR: No existe un pasajero con cedula " + cedulaPasajero;
        
        Vehiculo vehiculo = vehiculoService.buscarPorPlaca(placaVehiculo);
        
        if (vehiculo == null) return "ERROR: No existe un vehiculo con placa " + placaVehiculo;
        
        if (!vehiculo.tieneCupos()) return "ERROR: El vehiculo no tiene cupos disponibles.";
        
        String fecha = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        String id = "TK" + (tickets.size() + 1);
        
        Ticket ticket = new Ticket(id, pasajero, vehiculo, fecha, origen, destino);
        tickets.add(ticket);
        ticketDAO.guardarTicket(ticket);
        vehiculo.sumarPasajero();
        vehiculoService.actualizarArchivos();
        return "Ticket vendido exitosamente. Valor: $" + ticket.getValorFinal();
    }

    public List<Ticket> listarTickets() {
        return tickets; 
    }

    public double calcularTotalRecaudado() {
        double total = 0;
        for (Ticket t : tickets) total += t.getValorFinal();
        return total;
    }

    public void mostrarPasajerosPorTipo() {
        int regular = 0, estudiante = 0, adultoMayor = 0;
        for (Ticket t : tickets) {
            String tipo = t.getPasajero().getTipo();
            if (tipo.equals("Regular")) regular++;
            else if (tipo.equals("Estudiante")) estudiante++;
            else if (tipo.equals("AdultoMayor")) adultoMayor++;
        }
        
        System.out.println("Pasajeros Regular: " + regular);
        System.out.println("Pasajeros Estudiante: " + estudiante);
        System.out.println("Pasajeros Adulto Mayor: " + adultoMayor);
    }

    public void mostrarVehiculoConMasTickets() {
        if (tickets.isEmpty()) { System.out.println("No hay tickets registrados."); return; }
        List<String> placas = new ArrayList<>();
        List<Integer> conteos = new ArrayList<>();
        
        for (Ticket t : tickets) {
            String placa = t.getVehiculo().getPlaca();
            int idx = placas.indexOf(placa);
            if (idx == -1) { placas.add(placa); conteos.add(1); }
            else conteos.set(idx, conteos.get(idx) + 1);
        }
        
        int maxIdx = 0;
        for (int i = 1; i < conteos.size(); i++)
            if (conteos.get(i) > conteos.get(maxIdx)) maxIdx = i;
        System.out.println("Vehiculo con mas tickets vendidos:");
        System.out.println("Placa: " + placas.get(maxIdx) + " - Tickets: " + conteos.get(maxIdx));
    }
    
}
