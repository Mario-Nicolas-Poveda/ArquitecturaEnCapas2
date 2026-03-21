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
    private RutaService rutaService;
    private ReservaService reservaService;

    public Menu() {
        sc = new Scanner(System.in);
        vehiculoService = new VehiculoService();
        personaService = new PersonaService();
        ticketService = new TicketService(vehiculoService, personaService);
        rutaService = new RutaService();
        reservaService = new ReservaService(vehiculoService, personaService, ticketService);
    }

    public void mostrarMenuPrincipal() {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("MENU");
            System.out.println("\n\n");
            System.out.println("1. Gestion de Rutas");
            System.out.println("2. Gestion de vehiculos");
            System.out.println("3. Gestion de personas");
            System.out.println("4. Venta de tickets");
            System.out.println("5. Consultas y estadisticas");
            System.out.println("6. Reportes con Filtros");
            System.out.println("7. Reservas");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opcion: ");
            opcion = leerEntero();
            switch (opcion) {
                case 1:
                    menuRutas();
                    break;
                case 2:
                    menuVehiculos();
                    break;
                case 3:
                    menuPersonas();
                    break;
                case 4:
                    menuTickets();
                    break;
                case 5:
                    menuEstadisticas();
                    break;
                case 6:
                    menuReportes();
                    break;
                case 7:
                    menuReservas();
                    break;
                case 0:
                    System.out.println("Saliendo");
                    break;
                default:
                    System.out.println("Error, opcion no valida");
            }
        }
    }

    private void menuReservas() {
        int op = -1;
        while (op != 0) {
            System.out.println("\nRESERVAS");
            System.out.println("1. Crear reserva");
            System.out.println("2. Cancelar reserva");
            System.out.println("3. Listar reservas activas");
            System.out.println("4. Historial de reservas de un pasajero");
            System.out.println("5. Convertir reserva en ticket");
            System.out.println("6. Verificar reservas vencidas");
            System.out.println("0. Volver");
            System.out.print("Opcion: ");
            op = leerEntero();
            switch (op) {
                case 1:
                    crearReserva();
                    break;
                case 2:
                    cancelarReserva();
                    break;
                case 3:
                    listarReservasActivas();
                    break;
                case 4:
                    historialPasajero();
                    break;
                case 5:
                    convertirReserva();
                    break;
                case 6:
                    verificarVencidas();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Error, opcion no valida");
            }
        }
    }

    private void crearReserva() {
        System.out.print("Cedula del pasajero: ");
        String cedula = sc.nextLine().trim();
        System.out.print("Placa del vehiculo: ");
        String placa = sc.nextLine().trim();
        System.out.print("Fecha del viaje (dd/MM/yyyy): ");
        String fechaViaje = sc.nextLine().trim();
        System.out.println(reservaService.crearReserva(cedula, placa, fechaViaje));
    }

    private void cancelarReserva() {
        System.out.print("Codigo de la reserva: ");
        String codigo = sc.nextLine().trim();
        System.out.println(reservaService.cancelarReserva(codigo));
    }

    private void listarReservasActivas() {
        List<Reserva> lista = reservaService.listarActivas();
        if (lista.isEmpty()) {
            System.out.println("No hay reservas activas");
            return;
        }
        System.out.println("Total reservas activas: " + lista.size());
        for (Reserva r : lista) {
            r.imprimirDetalle();
            System.out.println("--------------");
        }
    }

    private void historialPasajero() {
        System.out.print("Cedula del pasajero: ");
        String cedula = sc.nextLine().trim();
        List<Reserva> lista = reservaService.historialPorPasajero(cedula);
        if (lista.isEmpty()) {
            System.out.println("No hay reservas para ese pasajero");
            return;
        }
        System.out.println("Historial de reservas (" + lista.size() + "):");
        for (Reserva r : lista) {
            r.imprimirDetalle();
            System.out.println("-------------");
        }
    }

    private void convertirReserva() {
        System.out.print("Codigo de la reserva: ");
        String codigo = sc.nextLine().trim();
        System.out.print("Origen: ");
        String origen = sc.nextLine().trim();
        System.out.print("Destino: ");
        String destino = sc.nextLine().trim();
        System.out.println(reservaService.convertirEnTicket(codigo, origen, destino));
    }

    private void verificarVencidas() {
        int canceladas = reservaService.verificarVencidas();
        if (canceladas == 0) {
            System.out.println("No hay reservas vencidas todo esta al dia");
        } else {
            System.out.println("Se cancelaron " + canceladas
                    + " reserva(s) vencida(s) Los cupos quedaron liberados");
        }
    }

    private void menuReportes() {
        int op = -1;
        while (op != 0) {
            System.out.println("\nREPORTES CON FILTROS");
            System.out.println("1. Tickets por fecha especifica");
            System.out.println("2. Tickets por tipo de vehiculo");
            System.out.println("3. Tickets por tipo de pasajero");
            System.out.println("4. Resumen del dia actual");
            System.out.println("0. Volver");
            System.out.print("Opcion: ");
            op = leerEntero();
            switch (op) {
                case 1:
                    reportePorFecha();
                    break;
                case 2:
                    reportePorTipoVehiculo();
                    break;
                case 3:
                    reportePorTipoPasajero();
                    break;
                case 4:
                    ticketService.mostrarResumenDia();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Error opcion no valida");
            }
        }
    }

    private void reportePorTipoVehiculo() {
        System.out.println("Tipos: Buseta | MicroBus | Bus");
        System.out.print("Ingresa el tipo: ");
        String tipo = sc.nextLine().trim();
        List<Ticket> lista = ticketService.filtrarPorTipoVehiculo(tipo);
        System.out.println("Tickets en " + tipo + ": " + lista.size());
        for (Ticket t : lista) {
            t.imprimirDetalle();
            System.out.println("----------");
        }
    }

    private void reportePorTipoPasajero() {
        System.out.println("Tipos: Regular | Estudiante | AdultoMayor");
        System.out.print("Ingresa el tipo: ");
        String tipo = sc.nextLine().trim();
        List<Ticket> lista = ticketService.filtrarPorTipoPasajero(tipo);
        System.out.println("Tickets de pasajeros " + tipo + ": " + lista.size());
        for (Ticket t : lista) {
            t.imprimirDetalle();
            System.out.println("-----------");
        }
    }

    private void reportePorFecha() {
        System.out.print("Ingresa la fecha (dd/MM/yyyy): ");
        String fecha = sc.nextLine().trim();
        List<Ticket> lista = ticketService.filtrarPorFecha(fecha);
        System.out.println("Tickets del " + fecha + ": " + lista.size());
        for (Ticket t : lista) {
            t.imprimirDetalle();
            System.out.println("----------");
        }
    }

    private void menuRutas() {
        int op = -1;
        while (op != 0) {
            System.out.println("\nRUTAS");
            System.out.println("1. Registrar Ruta");
            System.out.println("2. Listar Rutas");
            System.out.println("0. Volver");
            System.out.print("Opcion: ");
            op = leerEntero();
            switch (op) {
                case 1:
                    registrarRuta();
                    break;
                case 2:
                    listarRutas();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opcion no valida");
            }
        }
    }

    private void registrarRuta() {
        System.out.print("Codigo de ruta: ");
        String codigo = sc.nextLine().trim();
        System.out.print("Ciudad origen: ");
        String origen = sc.nextLine().trim();
        System.out.print("Ciudad destino: ");
        String destino = sc.nextLine().trim();
        System.out.print("Distancia (km): ");
        double distancia = leerDouble();
        System.out.print("Tiempo estimado (minutos): ");
        int tiempo = leerEntero();
        Ruta r = new Ruta(codigo, origen, destino, distancia, tiempo);
        System.out.println(rutaService.registrarRuta(r));
    }

    private void listarRutas() {
        List<Ruta> lista = rutaService.listarRutas();
        if (lista.isEmpty()) {
            System.out.println("No hay rutas registradas");
            return;
        }
        for (Ruta r : lista) {
            r.imprimirDetalle();
            System.out.println("-------------");
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
                case 1:
                    registrarVehiculo("Buseta");
                    break;
                case 2:
                    registrarVehiculo("MicroBus");
                    break;
                case 3:
                    registrarVehiculo("Bus");
                    break;
                case 4:
                    listarVehiculos();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Error, opcion no valida");
            }
        }
    }

    private void registrarVehiculo(String tipo) {
        System.out.print("Placa: ");
        String placa = sc.nextLine().trim();

        List<Ruta> rutas = rutaService.listarRutas();
        if (rutas.isEmpty()) {
            System.out.println("Error, no hay rutas registradas registra una ruta primero");
            System.out.print("Ingresa el nombre de ruta manualmente: ");
            String rutaManual = sc.nextLine().trim();
            Vehiculo v = crearVehiculo(tipo, placa, rutaManual);
            System.out.println(vehiculoService.registrarVehiculo(v));
            return;
        }

        System.out.println("Rutas disponibles:");
        for (int i = 0; i < rutas.size(); i++) {
            System.out.println((i + 1) + ". " + rutas.get(i));
        }
        System.out.print("Selecciona el numero de ruta: ");
        int seleccion = leerEntero();
        if (seleccion < 1 || seleccion > rutas.size()) {
            System.out.println("Seleccion invalida");
            return;
        }
        Ruta rutaSeleccionada = rutas.get(seleccion - 1);
        Vehiculo v = crearVehiculo(tipo, placa, rutaSeleccionada.toString());
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
            System.out.println("---------------");
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
                case 1:
                    registrarConductor();
                    break;
                case 2:
                    registrarPasajero("Regular");
                    break;
                case 3:
                    registrarPasajero("Estudiante");
                    break;
                case 4:
                    registrarPasajero("AdultoMayor");
                    break;
                case 5:
                    listarConductores();
                    break;
                case 6:
                    listarPasajeros();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Error opcion no valida");
            }
        }
    }

    private void registrarConductor() {
        System.out.print("Cedula: ");
        String cedula = sc.nextLine().trim();
        System.out.print("Nombre: ");
        String nombre = sc.nextLine().trim();
        System.out.print("Numero de licencia: ");
        String licencia = sc.nextLine().trim();
        System.out.print("Categoria de licencia (B1, B2, C1, C2): ");
        String categoria = sc.nextLine().trim();
        Conductor c = new Conductor(cedula, nombre, licencia, categoria);
        System.out.println(personaService.registrarConductor(c));
    }

    private void registrarPasajero(String tipo) {
        System.out.print("Cedula: ");
        String cedula = sc.nextLine().trim();
        System.out.print("Nombre: ");
        String nombre = sc.nextLine().trim();
        System.out.print("Fecha de nacimiento (dd/MM/yyyy): ");
        String fechaNac = sc.nextLine().trim();
        System.out.println(personaService.registrarPasajero(cedula, nombre, fechaNac, tipo));
    }

    private void listarConductores() {
        List<Conductor> lista = personaService.listarConductores();
        if (lista.isEmpty()) {
            System.out.println("Error no hay conductores registrados");
            return;
        }
        for (Conductor c : lista) {
            c.imprimirDetalle();
            System.out.println("----------");
        }
    }

    private void listarPasajeros() {
        List<Pasajero> lista = personaService.listarPasajeros();
        if (lista.isEmpty()) {
            System.out.println("Error hay pasajeros registrados");
            return;
        }
        for (Pasajero p : lista) {
            p.imprimirDetalle();
            System.out.println("----------");
        }
    }

    private void menuTickets() {
        int op = -1;
        while (op != 0) {
            System.out.println("\nTICKETS");
            System.out.println("1. Vender Ticket");
            System.out.println("2. Listar todos los Tickets");
            System.out.println("0. Volver");
            System.out.print("Opcion: ");
            op = leerEntero();
            switch (op) {
                case 1:
                    venderTicket();
                    break;
                case 2:
                    listarTickets();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Error opcion no valida");
            }
        }
    }

    private void venderTicket() {
        System.out.print("Cedula del pasajero: ");
        String cedula = sc.nextLine().trim();
        System.out.print("Placa del vehiculo: ");
        String placa = sc.nextLine().trim();
        System.out.print("Origen: ");
        String origen = sc.nextLine().trim();
        System.out.print("Destino: ");
        String destino = sc.nextLine().trim();
        System.out.println(ticketService.venderTicket(cedula, placa, origen, destino));
    }

    private void listarTickets() {
        List<Ticket> lista = ticketService.listarTickets();
        if (lista.isEmpty()) {
            System.out.println("Error, no hay tickets vendidos");
            return;
        }
        for (Ticket t : lista) {
            t.imprimirDetalle();
            System.out.println("---------------");
        }
    }

    private void menuEstadisticas() {
        int op = -1;
        while (op != 0) {
            System.out.println("\nESTADISTICAS");
            System.out.println("1. Total recaudado");
            System.out.println("2. Pasajeros por tipo");
            System.out.println("3. Vehiculo con mas tickets");
            System.out.println("0. Volver");
            System.out.print("Opcion: ");
            op = leerEntero();
            switch (op) {
                case 1:
                    System.out.println("Total recaudado: $" + ticketService.calcularTotalRecaudado());
                    break;
                case 2:
                    ticketService.mostrarPasajerosPorTipo();
                    break;
                case 3:
                    ticketService.mostrarVehiculoConMasTickets();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Error, opcion no valida");
            }
        }
    }

    private int leerEntero() {
        try {
            String linea = sc.nextLine();
            return Integer.parseInt(linea.trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private double leerDouble() {
        while (true) {
            try {
                double valor = Double.parseDouble(sc.nextLine().trim());
              
                if (valor <= 0) {
                    System.out.println("ERROR: El valor debe ser mayor a 0. Ejemplo: 850.5");
                } else {
                    return valor;
                }
            } catch (NumberFormatException e) {
                
                System.out.println("ERROR: Ingrese un numero valido Ejemplo: 850.5");
                System.out.print("Distancia (km): ");
            }
        }
    }

    private Vehiculo crearVehiculo(String tipo, String placa, String ruta) {
        if (tipo.equals("Buseta"))
            return new Buseta(placa, ruta);
        else if (tipo.equals("MicroBus"))
            return new MicroBus(placa, ruta);
        else
            return new Bus(placa, ruta);
    }

    // validaciones

    // Solo letras y espacios (para nombres)
    private String leerNombre(String campo) {
        String valor;
        while (true) {
            System.out.print(campo + ": ");
            valor = sc.nextLine().trim();
            if (valor.isEmpty()) {
                System.out.println("ERROR: El campo no puede estar vacio");
            } else if (!valor.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
                System.out.println("ERROR: " + campo + " solo puede contener letras");
            } else {
                return valor;
            }
        }
    }

    private String leerNumerico(String campo) {
        while (true) {
            System.out.print(campo + ": ");
            String valor = sc.nextLine().trim();
            if (valor.isEmpty()) {
                System.out.println("ERROR: El campo '" + campo + "' no puede estar vacio.");
            } else if (!valor.matches("[0-9]+")) {
                System.out.println("ERROR: '" + campo + "' solo puede contener numeros.");
            } else {
                return valor;
            }
        }
    }
    
    private String leerAlfanumerico(String campo) {
        while (true) {
            System.out.print(campo + ": ");
            String valor = sc.nextLine().trim();
            if (valor.isEmpty()) {
                System.out.println("ERROR: El campo '" + campo + "' no puede estar vacio.");
            } else if (!valor.matches("[a-zA-Z0-9]+")) {
                System.out.println("ERROR: '" + campo + "' solo puede contener letras y numeros sin espacios.");
            } else {
                return valor;
            }
        }
    }
    
    private String leerPlaca() {
        while (true) {
            System.out.print("Placa (formato ABC123): ");
            String valor = sc.nextLine().trim().toUpperCase();
            if (!valor.matches("[A-Z]{3}[0-9]{3}")) {
                System.out.println("ERROR: La placa debe tener exactamente 3 letras y 3 numeros. Ejemplo: ABC123");
            } else {
                return valor;
            }
        }
    }
    
    private String leerFecha(String campo) {
        while (true) {
            System.out.print(campo + " (dd/MM/yyyy): ");
            String valor = sc.nextLine().trim();
            if (!valor.matches("\\d{2}/\\d{2}/\\d{4}")) {
                System.out.println("ERROR: Formato invalido. Use dd/MM/yyyy. Ejemplo: 25/12/2026");
                continue;
            }
            try {
                java.time.format.DateTimeFormatter fmt =
                        java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
                java.time.LocalDate.parse(valor, fmt);
                return valor;
            } catch (Exception e) {
                System.out.println("ERROR: Fecha inexistente. Verifique el dia, mes y anio.");
            }
        }
    }
    
    private String leerFechaViaje() {
        while (true) {
            String valor = leerFecha("Fecha del viaje");
            java.time.format.DateTimeFormatter fmt =
                    java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
            java.time.LocalDate fecha = java.time.LocalDate.parse(valor, fmt);
            if (fecha.isBefore(java.time.LocalDate.now())) {
                System.out.println("ERROR: La fecha del viaje no puede ser en el pasado.");
            } else {
                return valor;
            }
        }
    }
    
    private String leerFechaNacimiento() {
        while (true) {
            String valor = leerFecha("Fecha de nacimiento");
            java.time.format.DateTimeFormatter fmt =
                    java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
            java.time.LocalDate fecha = java.time.LocalDate.parse(valor, fmt);
            if (!fecha.isBefore(java.time.LocalDate.now())) {
                System.out.println("ERROR: La fecha de nacimiento no puede ser hoy ni en el futuro.");
            } else if (java.time.Period.between(fecha, java.time.LocalDate.now()).getYears() < 5) {
                System.out.println("ERROR: La edad minima para registrarse es 5 anios.");
            } else {
                return valor;
            }
        }
    }
    
    private String leerCategoria() {
        while (true) {
            System.out.print("Categoria de licencia (B1, B2, C1, C2): ");
            String valor = sc.nextLine().trim().toUpperCase();
            if (valor.equals("B1") || valor.equals("B2")
                    || valor.equals("C1") || valor.equals("C2")) {
                return valor;
            }
            System.out.println("ERROR: Categoria invalida. Solo se acepta B1, B2, C1 o C2.");
        }
    }
    
    private String leerTipoVehiculo() {
        while (true) {
            System.out.print("Tipo de vehiculo: ");
            String valor = sc.nextLine().trim();
            if (valor.equals("Buseta") || valor.equals("MicroBus") || valor.equals("Bus")) {
                return valor;
            }
            System.out.println("ERROR: Tipo invalido. Escriba exactamente: Buseta, MicroBus o Bus");
        }
    }
    
    private String leerTipoPasajero() {
        while (true) {
            System.out.print("Tipo de pasajero: ");
            String valor = sc.nextLine().trim();
            if (valor.equals("Regular") || valor.equals("Estudiante") || valor.equals("AdultoMayor")) {
                return valor;
            }
            System.out.println("ERROR: Tipo invalido. Escriba exactamente: Regular, Estudiante o AdultoMayor");
        }
    }
    
    private int leerEnteroPositivo(String campo) {
        while (true) {
            System.out.print(campo + ": ");
            try {
                int valor = Integer.parseInt(sc.nextLine().trim());
                if (valor <= 0) {
                    System.out.println("ERROR: El valor debe ser mayor a 0.");
                } else {
                    return valor;
                }
            } catch (NumberFormatException e) {
                System.out.println("ERROR: Ingrese un numero entero valido.");
            }
        }
    }
    
    private int leerEnteroRango(int min, int max) {
        while (true) {
            System.out.print("Selecciona el numero de ruta: ");
            try {
                int valor = Integer.parseInt(sc.nextLine().trim());
                if (valor < min || valor > max) {
                    System.out.println("ERROR: Ingrese un numero entre " + min + " y " + max + ".");
                } else {
                    return valor;
                }
            } catch (NumberFormatException e) {
                System.out.println("ERROR: Ingrese un numero valido.");
            }
        }
    }
    
    private void limpiarPantalla() {
        try {
            String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("win")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            for (int i = 0; i < 50; i++) System.out.println();
        }
    }
    // Solo digitos, entre 6 y 10 caracteres (para cedulas)
    private String leerCedula(String campo) {
        String valor;
        while (true) {
            System.out.print(campo + ": ");
            valor = sc.nextLine().trim();
            if (valor.isEmpty()) {
                System.out.println("ERROR: El campo no puede estar vacio");
            } else if (!valor.matches("\\d{6,10}")) {
                System.out.println("ERROR: " + campo + " debe tener entre 6 y 10 digitos numericos");
            } else {
                return valor;
            }
        }
    }

}
