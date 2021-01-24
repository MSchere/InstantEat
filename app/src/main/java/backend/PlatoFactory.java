package backend;

import java.util.ArrayList;

public class PlatoFactory {
    
    public Plato crearPlato(String nombre,String restaurante,ArrayList<String> ingredientes,double precio,boolean glutenFree, boolean esVegano){
        return new Plato(nombre,restaurante,ingredientes,precio,glutenFree,esVegano);
    }
    
}
