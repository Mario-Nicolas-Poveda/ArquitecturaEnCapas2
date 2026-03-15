
package model;

/**
 *
 * @author alex
 */

public class PasajeroAdultoMayor extends Pasajero {

    public PasajeroAdultoMayor(String cedula, String nombre) {
        super(cedula, nombre);
    }

    @Override
    public double calcularDescuento() {
        return 0.30;
    }

    @Override
    public String getTipo() {
        return "AdultoMayor";
    }

    @Override
    public void imprimirDetalle() {
        System.out.println("=== PASAJERO ADULTO MAYOR ===");
        System.out.println("Cedula: " + cedula);
        System.out.println("Nombre: " + nombre);
        System.out.println("Descuento: 30%");
    }
    
}
