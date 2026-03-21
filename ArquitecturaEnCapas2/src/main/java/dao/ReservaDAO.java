
package dao;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.*;
import model.Reserva;

/**
 *
 * @author alex
 */

public class ReservaDAO {
    
        private static final String ARCHIVO = "reservas.txt";
        
    public void guardarReserva(Reserva r) {
        try {
            FileWriter fw = new FileWriter(ARCHIVO, true);
            fw.write(r.toTexto() + "\n");
            fw.close();
        } catch (IOException e) {
            System.out.println("Error al guardar reserva: " + e.getMessage());
        }
    }
    
        public List<String[]> cargarLineas() {
        List<String[]> lista = new ArrayList<>();
        File f = new File(ARCHIVO);
        if (!f.exists()) return lista;
        try {
            BufferedReader br = new BufferedReader(new FileReader(ARCHIVO));
            String linea;
            while ((linea = br.readLine()) != null) {
                if (!linea.trim().isEmpty()) {
                    String[] partes = linea.split(";");
                    if (partes.length >= 6) lista.add(partes);
                }
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Error al leer reservas: " + e.getMessage());
        }
        return lista;
    }

        
}
