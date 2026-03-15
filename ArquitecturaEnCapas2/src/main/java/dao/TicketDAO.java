/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author alex
 */
import model.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class TicketDAO {

    private static final String ARCHIVO_TICKETS = "tickets.txt";

    public void guardarTicket(Ticket t) {
        try {
            FileWriter fw = new FileWriter(ARCHIVO_TICKETS, true);
            fw.write(t.toTexto() + "\n");
            fw.close();
        } catch (IOException e) {
            System.out.println("Error al guardar ticket: " + e.getMessage());
        }
    }

    public List<String[]> cargarLineas() {
        List<String[]> lista = new ArrayList<>();
        File f = new File(ARCHIVO_TICKETS);
        if (!f.exists()) return lista;
        try {
            BufferedReader br = new BufferedReader(new FileReader(ARCHIVO_TICKETS));
            String linea;
            while ((linea = br.readLine()) != null) {
                if (!linea.trim().isEmpty()) {
                    String[] partes = linea.split(";");
                    if (partes.length >= 7) lista.add(partes);
                }
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Error al leer tickets: " + e.getMessage());
        }
        return lista;
    }
    
}
