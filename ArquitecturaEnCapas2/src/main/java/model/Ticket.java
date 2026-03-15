
package model;

/**
 *
 * @author alex
 */

public class Ticket implements Imprimible, Calculable {
    private String id;
    private Pasajero pasajero;
    private Vehiculo vehiculo;
    private String fechaCompra;
    private String origen;
    private String destino;
    private double valorFinal;

    public Ticket(String id, Pasajero pasajero, Vehiculo vehiculo, String fechaCompra, String origen, String destino) {
        this.id = id;
        this.pasajero = pasajero;
        this.vehiculo = vehiculo;
        this.fechaCompra = fechaCompra;
        this.origen = origen;
        this.destino = destino;
        this.valorFinal = calcularTotal();
    }

    @Override
    public double calcularTotal() {
        double tarifa = vehiculo.getTarifaBase();
        double descuento = pasajero.calcularDescuento();
        return tarifa - (tarifa * descuento);
    }

    @Override
    public void imprimirDetalle() {
        System.out.println("=== TICKET ===");
        System.out.println("ID: " + id);
        System.out.println("Pasajero: " + pasajero.getNombre() + " (" + pasajero.getCedula() + ")");
        System.out.println("Tipo pasajero: " + pasajero.getTipo());
        System.out.println("Vehiculo: " + vehiculo.getTipo() + " - Placa: " + vehiculo.getPlaca());
        System.out.println("Ruta: " + origen + " -> " + destino);
        System.out.println("Fecha: " + fechaCompra);
        System.out.println("Valor final: $" + valorFinal);
    }

    public String getId() {
        return id; 
    }
    
    public Pasajero getPasajero() {
        return pasajero; 
    }
    
    public Vehiculo getVehiculo() {
        return vehiculo; 
    }
    
    public String getFechaCompra() {
        return fechaCompra; 
    }
    
    public String getOrigen() {
        return origen; 
    }
    
    public String getDestino() {
        return destino; 
    }
    
    public double getValorFinal() {
        return valorFinal; 
    }

    public String toTexto() {
        return id + ";" + pasajero.getCedula() + ";" + vehiculo.getPlaca() + ";" + fechaCompra + ";" + origen + ";" + destino + ";" + valorFinal;
    }
    
}
