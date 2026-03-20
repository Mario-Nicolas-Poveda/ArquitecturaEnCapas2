package model;

/**
 *
 * @authors alex and jose
 */
public class PasajeroEstudiante extends Pasajero
{

    public PasajeroEstudiante(String cedula, String nombre, String fechaNacimiento)
    {
        super(cedula, nombre, fechaNacimiento);
    }

    @Override
    public double calcularDescuento()
    {
        return 0.15;
    }

    @Override
    public String getTipo()
    {
        return "Estudiante";
    }

    @Override
    public void imprimirDetalle()
    {
        System.out.println("=== PASAJERO ESTUDIANTE ===");
        System.out.println("Cedula: " + cedula);
        System.out.println("Nombre: " + nombre);
        System.out.println("Fecha nacimiento: " + fechaNacimiento);
        System.out.println("Edad: " + calcularEdad() + " años");
        System.out.println("Descuento: 15%");
    }

}
