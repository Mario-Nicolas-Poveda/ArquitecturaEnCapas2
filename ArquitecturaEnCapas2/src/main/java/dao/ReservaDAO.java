
package dao;

import java.io.FileWriter;
import java.io.IOException;
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
}
