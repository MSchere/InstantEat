package com.example.instanteat;

public class Ingrediente {
    
    private String nombre;
    private boolean gluten;
    private boolean vegano;
    
    public Ingrediente(String nombre, boolean gluten, boolean vegano){
        this.nombre = nombre;
        this.gluten = gluten;
        this.vegano = vegano;
    }
    
    public boolean tieneGluten(){
        return this.gluten;
    }
    
    public boolean esVegano(){
        return this.vegano;
    }
}
