package service;

/**
 *
 * @author Jose
 */
import dao.VehiculoDAO;
import model.*;
import java.util.List;

public class VehiculoService
{

    private VehiculoDAO vehiculoDAO;
    private List<Vehiculo> vehiculos;

    public VehiculoService()
    {

        this.vehiculoDAO = new VehiculoDAO();

        this.vehiculos = vehiculoDAO.cargarTodos();

    }

    public String registrarVehiculo(Vehiculo v)
    {

        for (Vehiculo existente : vehiculos)
        {

            if (existente.getPlaca().equalsIgnoreCase(v.getPlaca()))
            {
                return "ERROR: Ya existe un vehiculo con la placa " + v.getPlaca();
            }

        }

        vehiculos.add(v);

        vehiculoDAO.guardarVehiculo(v);

        return "Vehiculo registrado correctamente.";
    }

    public List<Vehiculo> listarVehiculos()
    {
        return vehiculos;
    }

    public Vehiculo buscarPorPlaca(String placa)
    {

        for (Vehiculo v : vehiculos)
        {

            if (v.getPlaca().equalsIgnoreCase(placa))
            {
                return v;
            }

        }
        return null;
    }

    public void actualizarArchivos()
    {
        vehiculoDAO.reescribirVehiculos(vehiculos);
    }

}
