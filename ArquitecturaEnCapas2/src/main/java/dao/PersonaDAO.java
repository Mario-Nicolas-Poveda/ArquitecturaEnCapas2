package dao;

/**
 *
 * @author alex and jose 
 */
import model.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PersonaDAO
{

    private static final String ARCHIVO_CONDUCTORES = "conductores.txt";
    private static final String ARCHIVO_PASAJEROS = "pasajeros.txt";

    public void guardarConductor(Conductor c)
    {
        try
        {
            FileWriter fw = new FileWriter(ARCHIVO_CONDUCTORES, true);
            fw.write(c.toTexto() + "\n");
            fw.close();
        } catch (IOException e)
        {
            System.out.println("Error al guardar conductor: " + e.getMessage());
        }
    }

    public void guardarPasajero(Pasajero p)
    {
        try
        {
            FileWriter fw = new FileWriter(ARCHIVO_PASAJEROS, true);
            fw.write(p.toTexto() + "\n");
            fw.close();
        } catch (IOException e)
        {
            System.out.println("Error al guardar pasajero: " + e.getMessage());
        }
    }

    public List<Conductor> cargarConductores()
    {
        List<Conductor> lista = new ArrayList<>();
        File f = new File(ARCHIVO_CONDUCTORES);
        if (!f.exists())
        {
            return lista;
        }
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(ARCHIVO_CONDUCTORES));
            String linea;
            while ((linea = br.readLine()) != null)
            {
                if (!linea.trim().isEmpty())
                {
                    String[] p = linea.split(";");
                    if (p.length >= 4)
                    {
                        lista.add(new Conductor(p[0], p[1], p[2], p[3]));
                    }
                }
            }
            br.close();
        } catch (IOException e)
        {
            System.out.println("Error al leer conductores: " + e.getMessage());
        }
        return lista;
    }

    public List<Pasajero> cargarPasajeros()
    {
        List<Pasajero> lista = new ArrayList<>();
        File f = new File(ARCHIVO_PASAJEROS);
        if (!f.exists())
        {
            return lista;
        }
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(ARCHIVO_PASAJEROS));
            String linea;
            while ((linea = br.readLine()) != null)
            {
                if (!linea.trim().isEmpty())
                {
                    String[] p = linea.split(";");
                    if (p.length >= 4)
                    {
                        String tipo = p[0];
                        String cedula = p[1];
                        String nombre = p[2];
                        String fechaNac = p[3];
                        Pasajero pasajero = null;
                        if (tipo.equals("Regular"))
                        {
                            pasajero = new PasajeroRegular(cedula, nombre, fechaNac);
                        } else if (tipo.equals("Estudiante"))
                        {
                            pasajero = new PasajeroEstudiante(cedula, nombre, fechaNac);
                        } else if (tipo.equals("AdultoMayor"))
                        {
                            pasajero = new PasajeroAdultoMayor(cedula, nombre, fechaNac);
                        }
                        if (pasajero != null)
                        {
                            lista.add(pasajero);
                        }
                    }
                }
            }
            br.close();
        } catch (IOException e)
        {
            System.out.println("Error al leer pasajeros: " + e.getMessage());
        }
        return lista;
    }

}
