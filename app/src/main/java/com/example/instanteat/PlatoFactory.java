package com.example.instanteat;
import java.util.ArrayList;

public class PlatoFactory {
    
    public Plato crearPlato(String nombre, ArrayList<String> ingredientes, boolean esVegano, boolean tieneGluten){
        return new Plato(nombre,ingredientes,esVegano,tieneGluten);
    }
    
}
