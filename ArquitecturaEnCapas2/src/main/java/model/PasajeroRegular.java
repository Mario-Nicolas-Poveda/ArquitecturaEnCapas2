
package model;

/**
 *
 * @author alex
 */

public class PasajeroRegular extends Pasajero {

    public PasajeroRegular(String cedula, String nombre) {
        super(cedula, nombre);
    }

    @Override
    public double calcularDescuento() {
        return 0.0; 
    }

    @Override
    public String getTipo() {
        return "Regular"; 
    }

    @Override
    public void imprimirDetalle() {
        System.out.println("=== PASAJERO REGULAR ===");
        System.out.println("Cedula: " + cedula);
        System.out.println("Nombre: " + nombre);
        System.out.println("Descuento: 0%");
    }
    
}
