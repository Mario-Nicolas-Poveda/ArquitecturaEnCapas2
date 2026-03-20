package service;

/**
 *
 * @author alex
 */

import dao.PersonaDAO;
import model.*;
import java.util.List;

public class PersonaService {

    private PersonaDAO personaDAO;
    private List<Conductor> conductores;
    private List<Pasajero> pasajeros;

    public PersonaService() {
        this.personaDAO = new PersonaDAO();
        this.conductores = personaDAO.cargarConductores();
        this.pasajeros = personaDAO.cargarPasajeros();
    }

    public String registrarConductor(Conductor c) {
        if (!c.tieneLicencia()) return "ERROR: El conductor no tiene licencia registrada.";
        conductores.add(c);
        personaDAO.guardarConductor(c);
        return "Conductor registrado correctamente.";
    }

    public String registrarPasajero(String cedula, String nombre, String fechaNacimiento, String tipoSolicitado) {
        PasajeroRegular temporal = new PasajeroRegular(cedula, nombre, fechaNacimiento);
        Pasajero p;

        if (temporal.esAdultoMayor()) {
            p = new PasajeroAdultoMayor(cedula, nombre, fechaNacimiento);
            pasajeros.add(p);
            personaDAO.guardarPasajero(p);
            return "Pasajero registrado como ADULTO MAYOR automaticamente (edad: "
                    + temporal.calcularEdad() + " anos). Descuento 30% aplicado.";
        }
        
        if (tipoSolicitado.equals("Estudiante")) {
            p = new PasajeroEstudiante(cedula, nombre, fechaNacimiento);
        } else {
            p = new PasajeroRegular(cedula, nombre, fechaNacimiento);
        }
        pasajeros.add(p);
        personaDAO.guardarPasajero(p);
        return "Pasajero registrado como " + p.getTipo() + " correctamente.";
    }
    
    public Conductor buscarConductorPorCedula(String cedula) {
        for (Conductor c : conductores) {
            if (c.getCedula().equals(cedula)) return c;
        }
        return null;
    }

    public Pasajero buscarPasajeroPorCedula(String cedula) {
        for (Pasajero p : pasajeros) {
            if (p.getCedula().equals(cedula)) return p;
        }
        return null;
    }

    public List<Conductor> listarConductores() {
        return conductores; 
    }
    
    public List<Pasajero> listarPasajeros() {
        return pasajeros; 
    }
    
    
}
