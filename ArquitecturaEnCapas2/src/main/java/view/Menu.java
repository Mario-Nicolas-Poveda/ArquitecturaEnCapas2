/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;
import model.*;
import service.*;
import java.util.Scanner;
import java.util.List;

/**
 *
 * @author Mario
 */
public class Menu {
    private Scanner sc;
    private VehiculoService vehiculoService;
    private PersonaService personaService;
    private TicketService ticketService;

    public Menu() {
        sc = new Scanner(System.in);
        vehiculoService = new VehiculoService();
        personaService = new PersonaService();
        ticketService = new TicketService(vehiculoService, personaService);
    }

    public void mostrarMenuPrincipal() {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("MENU");
            System.out.println("\n\n");
            System.out.println("1. Gestion de vehiculos");
            System.out.println("2. Gestion de personas");
            System.out.println("3. Venta de tickets");
            System.out.println("4. Consultas y estadisticas");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opcion: ");
            opcion = leerEntero();
            switch (opcion) {
                case 1: menuVehiculos(); break;
                case 2: menuPersonas(); break;
                case 3: menuTickets(); break;
                case 4: menuEstadisticas(); break;
                case 0: System.out.println("Saliendo"); break;
                default: System.out.println("Error, opcion no valida");
            }
        }
    }

    private void menuVehiculos() {
        int op = -1;
        while (op != 0) {
            System.out.println("\n VEHICULOS");
            System.out.println("1. Registrar Buseta");
            System.out.println("2. Registrar MicroBus");
            System.out.println("3. Registrar Bus");
            System.out.println("4. Listar todos los vehiculos");
            System.out.println("0. Volver");
            System.out.print("Opcion: ");
            op = leerEntero();
            switch (op) {
                case 1: registrarVehiculo("Buseta"); break;
                case 2: registrarVehiculo("MicroBus"); break;
                case 3: registrarVehiculo("Bus"); break;
                case 4: listarVehiculos(); break;
                case 0: break;
                default: System.out.println("Error, opcion no valida");
            }
        }
    }
    
        private void registrarVehiculo(String tipo) {
            System.out.print("Placa: ");
            String placa = sc.nextLine().trim();
            System.out.print("Ruta: ");
            String ruta = sc.nextLine().trim();

            Vehiculo v = null;
            if (tipo.equals("Buseta")) v = new Buseta(placa, ruta);
            else if (tipo.equals("MicroBus")) v = new MicroBus(placa, ruta);
            else v = new Bus(placa, ruta);

            System.out.println(vehiculoService.registrarVehiculo(v));
        }

        private void listarVehiculos() {
            List<Vehiculo> lista = vehiculoService.listarVehiculos();
            if (lista.isEmpty()) {
                System.out.println("Error no hay vehiculos registrados");
                return;
            }
            for (Vehiculo v : lista) {
                v.imprimirDetalle();
                System.out.println("----");
            }
        }
        
        private void menuPersonas() {
            int op = -1;
            while (op != 0) {
                System.out.println("\nPERSONAS");
                System.out.println("1. Registrar Conductor");
                System.out.println("2. Registrar Pasajero Regular");
                System.out.println("3. Registrar Pasajero Estudiante");
                System.out.println("4. Registrar Pasajero Adulto Mayor");
                System.out.println("5. Listar Conductores");
                System.out.println("6. Listar Pasajeros");
                System.out.println("0. Volver");
                System.out.print("Opcion: ");
                op = leerEntero();
                switch (op) {
                    case 1: registrarConductor(); break;
                    case 2: registrarPasajero("Regular"); break;
                    case 3: registrarPasajero("Estudiante"); break;
                    case 4: registrarPasajero("AdultoMayor"); break;
                    case 5: listarConductores(); break;
                    case 6: listarPasajeros(); break;
                    case 0: break;
                    default: System.out.println("Opcion no valida");
                }
            }
        }

}
