
package service;

/**
 *
 * @author alex
 */

import dao.RutaDAO;
import model.Ruta;
import java.util.List;

public class RutaService {

    private RutaDAO rutaDAO;
    private List<Ruta> rutas;

    public RutaService() {
        this.rutaDAO = new RutaDAO();
        this.rutas = rutaDAO.cargarRutas();
    }

    public String registrarRuta(Ruta r) {
        for (Ruta existente : rutas) {
            if (existente.getCodigo().equalsIgnoreCase(r.getCodigo())) {
                return "ERROR: Ya existe una ruta con el codigo " + r.getCodigo();
            }
        }
        rutas.add(r);
        rutaDAO.guardarRuta(r);
        return "Ruta registrada correctamente.";
    }

    public Ruta buscarPorCodigo(String codigo) {
        for (Ruta r : rutas) {
            if (r.getCodigo().equalsIgnoreCase(codigo)) return r;
        }
        return null;
    }

    public List<Ruta> listarRutas() {
        return rutas;
    }
    
}


