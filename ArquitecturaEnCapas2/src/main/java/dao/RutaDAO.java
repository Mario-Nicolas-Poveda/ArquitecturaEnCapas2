package dao;

import model.Ruta;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jose
 */
public class RutaDAO
{

    private static final String ARCHIVO = "rutas.txt";

    public void guardarRuta(Ruta r)
    {
        try
        {
            FileWriter fw = new FileWriter(ARCHIVO, true);
            fw.write(r.toTexto() + "\n");
            fw.close();
        } catch (IOException e)
        {
            System.out.println("Error al guardar ruta: " + e.getMessage());
        }
    }

    public List<Ruta> cargarRutas()
    {
        List<Ruta> lista = new ArrayList<>();
        File f = new File(ARCHIVO);
        if (!f.exists())
        {
            return lista;
        }
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(ARCHIVO));
            String linea;
            while ((linea = br.readLine()) != null)
            {
                if (!linea.trim().isEmpty())
                {
                    String[] p = linea.split(";");
                    if (p.length >= 5)
                    {
                        lista.add(new Ruta(p[0], p[1], p[2],
                                Double.parseDouble(p[3]),
                                Integer.parseInt(p[4])));
                    }
                }
            }
            br.close();
        } catch (IOException e)
        {
            System.out.println("Error al leer rutas: " + e.getMessage());
        }
        return lista;
    }

}
