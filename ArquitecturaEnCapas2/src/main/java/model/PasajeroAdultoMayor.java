package model;

/**
 *
 * @author alex
 */
public class PasajeroAdultoMayor extends Pasajero
{

    public PasajeroAdultoMayor(String cedula, String nombre, String fechaNacimiento)
    {
        super(cedula, nombre, fechaNacimiento);
    }

    @Override
    public double calcularDescuento()
    {
        return 0.30;
    }

    @Override
    public String getTipo()
    {
        return "AdultoMayor";
    }

    @Override
    public void imprimirDetalle()
    {
        System.out.println("=== PASAJERO ADULTO MAYOR ===");
        System.out.println("Cedula: " + cedula);
        System.out.println("Nombre: " + nombre);
        System.out.println("Fecha nacimiento: " + fechaNacimiento);
        System.out.println("Edad: " + calcularEdad() + " anos");
        System.out.println("Descuento: 30%");
    }

}
