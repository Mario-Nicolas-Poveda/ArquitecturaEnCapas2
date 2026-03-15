
package model;

/**
 *
 * @author Jose
 */
public class Bus extends Vehiculo
{
    
    public Bus(String placa, String ruta) { 

        super(placa, ruta); 

        this.capacidadMaxima = 45; 

    } 

    @Override 
    public double getTarifaBase() { 
        return 15000; 
    } 

    @Override 
    public String getTipo() { 
        return "Bus"; 
    } 

    @Override 
    public void imprimirDetalle() { 

        System.out.println("=== BUS ==="); 

        System.out.println("Placa: " + placa); 

        System.out.println("Ruta: " + ruta); 

        System.out.println("Capacidad: " + capacidadMaxima); 

        System.out.println("Cupos disponibles: " + getCuposDisponibles()); 

        System.out.println("Tarifa base: $" + getTarifaBase()); 

        System.out.println("Disponible: " + (disponible ? "Si" : "No")); 

    } 

}
