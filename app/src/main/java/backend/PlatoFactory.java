package backend;

import java.util.ArrayList;

import backend.Plato;

public class PlatoFactory {
    
    public Plato crearPlato(String nombre, String restaurante, ArrayList<String> ingredientes, double precio, boolean tieneGluten, boolean esVegano){
        return new Plato(nombre,restaurante,ingredientes,precio,tieneGluten,esVegano);
    }
    
}
