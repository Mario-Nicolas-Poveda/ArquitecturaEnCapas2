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

}
