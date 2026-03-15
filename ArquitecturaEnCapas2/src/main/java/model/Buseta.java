package model;

/**
 *
 * @author Jose
 */
public class Buseta extends Vehiculo
{

    public Buseta(String placa, String ruta)
    {

        super(placa, ruta);

        this.capacidadMaxima = 19;

    }

    @Override

    public double getTarifaBase()
    {
        return 8000;
    }

    @Override

    public String getTipo()
    {
        return "Buseta";
    }

    @Override

    public void imprimirDetalle()
    {

        System.out.println("=== BUSETA ===");

        System.out.println("Placa: " + placa);

        System.out.println("Ruta: " + ruta);

        System.out.println("Capacidad: " + capacidadMaxima);

        System.out.println("Cupos disponibles: " + getCuposDisponibles());

        System.out.println("Tarifa base: $" + getTarifaBase());

        System.out.println("Disponible: " + (disponible ? "Si" : "No"));

    }

}
