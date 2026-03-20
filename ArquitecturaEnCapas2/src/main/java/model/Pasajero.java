package model;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

/**
 *
 * @authors alex and jose
 */
public abstract class Pasajero extends Persona
{

    protected String fechaNacimiento;

    public Pasajero(String cedula, String nombre, String fechaNacimiento)
    {
        super(cedula, nombre);
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getFechaNacimiento()
    {
        return fechaNacimiento;
    }

    public int calcularEdad()
    {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate nacimiento = LocalDate.parse(fechaNacimiento, fmt);
        return Period.between(nacimiento, LocalDate.now()).getYears();
    }

    public boolean esAdultoMayor()
    {
        return calcularEdad() >= 60;
    }

    public abstract double calcularDescuento();

    public abstract String getTipo();

    public String toTexto()
    {
        return getTipo() + ";" + cedula + ";" + nombre + ";" + fechaNacimiento;
    }

}
