package model;

/**
 *
 * @author alex
 */

public class Conductor extends Persona {
    private String numeroLicencia;
    private String categoriaLicencia; // B1, B2, C1, C2

    public Conductor(String cedula, String nombre, String numeroLicencia, String categoriaLicencia) {
        super(cedula, nombre);
        this.numeroLicencia = numeroLicencia;
        this.categoriaLicencia = categoriaLicencia;
    }

    public String getNumeroLicencia() {
        return numeroLicencia; 
    }
    
    public String getCategoriaLicencia() {
        return categoriaLicencia; 
    }
    
    public void setNumeroLicencia(String numeroLicencia) {
        this.numeroLicencia = numeroLicencia; 
    }
    
    public void setCategoriaLicencia(String categoriaLicencia) {
        this.categoriaLicencia = categoriaLicencia; 
    }

    public boolean tieneLicencia() {
        return numeroLicencia != null && !numeroLicencia.isEmpty();
    }

    @Override
    public void imprimirDetalle() {
        System.out.println("=== CONDUCTOR ===");
        System.out.println("Cedula: " + cedula);
        System.out.println("Nombre: " + nombre);
        System.out.println("Licencia: " + numeroLicencia);
        System.out.println("Categoria: " + categoriaLicencia);
    }

    
    public String toTexto() {
        return cedula + ";" + nombre + ";" + numeroLicencia + ";" + categoriaLicencia;
    }
    
}
