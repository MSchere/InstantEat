package com.example.instanteat;

import java.util.ArrayList;

public class Plato {
    private String nombre;
    private double precio;
    private ArrayList<String> ingredientes;
    private boolean tieneGluten;
    private boolean esVegano;
    
    public Plato(String nombre,double precio,ArrayList<String> ingredientes, boolean esVegano, boolean tieneGluten){
        this.nombre = nombre;
        this.precio = precio;
        this.ingredientes = ingredientes;
        this.esVegano = esVegano;
        this.tieneGluten = tieneGluten;
    }
    
    public boolean tieneGluten(){
        return tieneGluten;
    }
    
    public boolean esVegano(){
        return esVegano;
    }
    
    public double precio(){
        return precio;
    }
    
    @Override
    public String toString(){
        String cadVegano="No";
        String cadGluten="No";
        if(esVegano) cadVegano="Si";
        if(tieneGluten) cadGluten="Si";
        return("Nombre: "+nombre+" , ingredientes: "+ingredientes.toString()+" , Gluten: "+cadGluten+" , Vegano: "+cadVegano);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public ArrayList<String> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(ArrayList<String> ingredientes) {
        this.ingredientes = ingredientes;
    }

    public boolean getTieneGluten() {
        return tieneGluten;
    }

    public void setTieneGluten(boolean tieneGluten) {
        this.tieneGluten = tieneGluten;
    }

    public boolean getEsVegano() {
        return esVegano;
    }

    public void setEsVegano(boolean esVegano) {
        this.esVegano = esVegano;
    }
}
