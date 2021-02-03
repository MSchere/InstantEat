package backend;

import java.util.ArrayList;

/**
 * Clase que fabrica platos
 */
public class PlatoFactory {
    
    /**
     * Devuelve una instancia de la clase Plato con los argumentos como atributos 
     * 
     * @param nombre Nombre del plato
     * @param restaurante Nombre del restaurante del plato
     * @param ingredientes Cadena con los ingredientes del plato
     * @param precio Precio del plato
     * @param glutenFree Vale true si el plato no tiene gluten, false si tiene
     * @param esVegano Vale true si el plato es vegano, false si no lo es
     * @return Nueva instancia de la clase Plato
     */
    public Plato crearPlato(String nombre,String restaurante,ArrayList<String> ingredientes,double precio,boolean glutenFree, boolean esVegano){
        return new Plato(nombre,restaurante,ingredientes,precio,glutenFree,esVegano);
    }
    
}
