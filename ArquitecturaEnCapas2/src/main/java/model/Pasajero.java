
package model;

/**
 *
 * @author alex
 */

public abstract class Pasajero extends Persona {
    
    public Pasajero(String cedula, String nombre) {
        super(cedula, nombre);
    }

    public abstract double calcularDescuento();
    public abstract String getTipo();

    public String toTexto() {
        return getTipo() + ";" + cedula + ";" + nombre;
    }
    
    
    
}
