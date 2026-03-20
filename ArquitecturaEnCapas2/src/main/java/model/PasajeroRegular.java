package model;

/**
 *
 * @authors alex and jose
 */
public class PasajeroRegular extends Pasajero
{

    public PasajeroRegular(String cedula, String nombre, String fechaNacimiento)
    {
        super(cedula, nombre, fechaNacimiento);
    }

    @Override
    public double calcularDescuento()
    {
        return 0.0;
    }

    @Override
    public String getTipo()
    {
        return "Regular";
    }

    @Override
    public void imprimirDetalle()
    {
        System.out.println("=== PASAJERO REGULAR ===");
        System.out.println("Cedula: " + cedula);
        System.out.println("Nombre: " + nombre);
        System.out.println("Fecha nacimiento: " + fechaNacimiento);
        System.out.println("Edad: " + calcularEdad() + " anos");
        System.out.println("Descuento: 0%");
    }

}
