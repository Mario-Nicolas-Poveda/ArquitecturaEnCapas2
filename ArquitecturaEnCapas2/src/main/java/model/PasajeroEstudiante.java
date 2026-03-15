
package model;

/**
 *
 * @author alex
 */

public class PasajeroEstudiante extends Pasajero {

    public PasajeroEstudiante(String cedula, String nombre) {
        super(cedula, nombre);
    }

    @Override
    public double calcularDescuento() {
        return 0.15;
    }

    @Override
    public String getTipo() {
        return "Estudiante";
    }

    @Override
    public void imprimirDetalle() {
        System.out.println("=== PASAJERO ESTUDIANTE ===");
        System.out.println("Cedula: " + cedula);
        System.out.println("Nombre: " + nombre);
        System.out.println("Descuento: 15%");
    }
    
    
}
