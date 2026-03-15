package dao;

/**
 *
 * @author Jose
 */
import model.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class VehiculoDAO
{

    private static final String ARCHIVO_BUSETA = "buseta.txt";
    private static final String ARCHIVO_MICROBUS = "microbus.txt";
    private static final String ARCHIVO_BUS = "bus.txt";

    public void guardarVehiculo(Vehiculo v)
    {

        String archivo = obtenerArchivo(v.getTipo());
        try
        {

            FileWriter fw = new FileWriter(archivo, true);

            fw.write(v.toTexto() + "\n");

            fw.close();

        } catch (IOException e)
        {
            System.out.println("Error al guardar vehiculo: " + e.getMessage());
        }
    }

    public List<Vehiculo> cargarTodos()
    {

        List<Vehiculo> lista = new ArrayList<>();

        lista.addAll(cargarDesdeArchivo(ARCHIVO_BUSETA));

        lista.addAll(cargarDesdeArchivo(ARCHIVO_MICROBUS));

        lista.addAll(cargarDesdeArchivo(ARCHIVO_BUS));

        return lista;

    }

    private List<Vehiculo> cargarDesdeArchivo(String archivo)
    {

        List<Vehiculo> lista = new ArrayList<>();

        File f = new File(archivo);

        if (!f.exists())
        {
            return lista;
        }

        try
        {

            BufferedReader br = new BufferedReader(new FileReader(archivo));

            String linea;

            while ((linea = br.readLine()) != null)
            {

                if (!linea.trim().isEmpty())
                {

                    Vehiculo v = parsearVehiculo(linea);

                    if (v != null)
                    {
                        lista.add(v);
                    }

                }

            }

            br.close();

        } catch (IOException e)
        {

            System.out.println("Error al leer " + archivo + ": " + e.getMessage());

        }

        return lista;

    }

    private Vehiculo parsearVehiculo(String linea)
    {

        String[] partes = linea.split(";");

        if (partes.length < 6)
        {
            return null;
        }

        String tipo = partes[0];

        String placa = partes[1];

        String ruta = partes[2];

        int pasajerosActuales = Integer.parseInt(partes[4]);

        boolean disponible = Boolean.parseBoolean(partes[5]);

        Vehiculo v = null;

        if (tipo.equals("Buseta"))
        {

            v = new Buseta(placa, ruta);

        } else if (tipo.equals("MicroBus"))
        {

            v = new MicroBus(placa, ruta);

        } else if (tipo.equals("Bus"))
        {

            v = new Bus(placa, ruta);

        }

        if (v != null)
        {

            for (int i = 0; i < pasajerosActuales; i++)
            {
                v.sumarPasajero();
            }

            v.setDisponible(disponible);

        }

        return v;

    }

    private String obtenerArchivo(String tipo)
    {

        if (tipo.equals("Buseta"))
        {
            return ARCHIVO_BUSETA;
        }

        if (tipo.equals("MicroBus"))
        {
            return ARCHIVO_MICROBUS;
        }

        return ARCHIVO_BUS;

    }

    public void reescribirVehiculos(List<Vehiculo> lista)
    {

        borrarArchivo(ARCHIVO_BUSETA);

        borrarArchivo(ARCHIVO_MICROBUS);

        borrarArchivo(ARCHIVO_BUS);

        for (Vehiculo v : lista)
        {

            guardarVehiculo(v);

        }

    }

    private void borrarArchivo(String archivo)
    {

        try
        {
            FileWriter fw = new FileWriter(archivo, false);

            fw.write("");

            fw.close();

        } catch (IOException e)
        {
            System.out.println("Error al limpiar archivo: " + e.getMessage());
        }

    }

}
