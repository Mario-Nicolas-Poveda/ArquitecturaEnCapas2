/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author alex
 */

public abstract class Persona implements Imprimible {
    protected String cedula;
    protected String nombre;

    public Persona(String cedula, String nombre) {
        this.cedula = cedula;
        this.nombre = nombre;
    }

    
    public String getCedula() { 
        return cedula; 
    }
    
    public String getNombre() { 
        return nombre; 
    }
    
    public void setCedula(String cedula) {
        this.cedula = cedula; 
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre; 
    }
    
}