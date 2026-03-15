
package model;

/**
 *
 * @author Jose
 */
public class MicroBus extends Vehiculo
{
    
    public MicroBus(String placa, String ruta) { 

        super(placa, ruta); 

        this.capacidadMaxima = 25; 

    } 
     
    @Override 
    public double getTarifaBase() { 
        return 10000; 
    } 

    @Override 
    public String getTipo() { 

        return "MicroBus"; 

    } 

    @Override 
    public void imprimirDetalle() { 
        
        System.out.println("=== MICROBUS ==="); 

        System.out.println("Placa: " + placa); 

        System.out.println("Ruta: " + ruta); 

        System.out.println("Capacidad: " + capacidadMaxima); 

        System.out.println("Cupos disponibles: " + getCuposDisponibles()); 

        System.out.println("Tarifa base: $" + getTarifaBase()); 

        System.out.println("Disponible: " + (disponible ? "Si" : "No"));
        
    } 



}
