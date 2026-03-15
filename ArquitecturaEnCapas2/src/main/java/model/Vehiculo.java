package model;

/**
 *
 * @author Jose
 */
public abstract class Vehiculo implements Imprimible
{

    protected String placa;
    protected String ruta;
    protected int capacidadMaxima;
    protected int pasajerosActuales;
    protected boolean disponible;

    public Vehiculo(String placa, String ruta)
    {
        this.placa = placa;
        this.ruta = ruta;
        this.pasajerosActuales = 0;
        this.disponible = true;
    }

    public abstract double getTarifaBase();

    public String getPlaca()
    {
        return placa;
    }

    public String getRuta()
    {
        return ruta;
    }

    public int getCapacidadMaxima()
    {
        return capacidadMaxima;
    }

    public int getPasajerosActuales()
    {
        return pasajerosActuales;
    }

    public boolean isDisponible()
    {
        return disponible;
    }

    public void setPlaca(String placa)
    {
        this.placa = placa;
    }

    public void setRuta(String ruta)
    {
        this.ruta = ruta;
    }

    public void setDisponible(boolean disponible)
    {
        this.disponible = disponible;
    }
    
    public boolean tieneCupos()
    {
        return pasajerosActuales < capacidadMaxima;
    }

    public void sumarPasajero()
    {
        pasajerosActuales++;
    }

    public int getCuposDisponibles()
    {
        return capacidadMaxima - pasajerosActuales;
    }

    public abstract String getTipo();

    public String toTexto()
    {
        return getTipo() + ";" + placa + ";" + ruta + ";" + capacidadMaxima + ";" 
                         + pasajerosActuales + ";" + disponible;
    }

}
