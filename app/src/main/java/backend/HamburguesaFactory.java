package backend;

import java.util.ArrayList;

/**
 * Clase que fabrica hamburguesas
 */
public class HamburguesaFactory {
    
    String[] pan = {"Pan normal","Pan de masa madre","Pan sin gluten"}; // Tipos de panes de la hamburguesa
    String lechuga = "Lechuga"; // Lechuga
    String tomate = "Tomate"; // Tomate
    String[] cebolla = {"Cebolla cruda","Cebolla caramelizada"}; // Tipos de cebollas
    String bacon = "Bacon"; // Bacon
    String[] tipoCarne = {"Vaca","Pollo","Vegana"}; // Tipos de carne
    String[] estadoCarne = {"Poco hecha","Al punto","Muy hecha"}; // Estado de la carne
    
    /**
     * Constructor
     */
    public HamburguesaFactory(){}
    
    /**
     * Crea un objeto de tipo Hamburguesa.
     *
     * @param nombre Nombre de la hamburguesa
     * @return Instancia de la clase Plato.
     */
    public Plato crearHamburguesa(String nombre,int[] opciones){
        ArrayList<String> ingredientes = new ArrayList();
        boolean esVegano = false;
        boolean glutenFree = false;
        
        if(opciones[0] != 0){
            ingredientes.add(pan[opciones[0]-1]);
        }
        if(opciones[1] != 0){
            ingredientes.add(lechuga);
        }
        if(opciones[2] != 0){
            ingredientes.add(tomate);
        }
        if(opciones[3] != 0){
            ingredientes.add(cebolla[opciones[3]-1]);
        }
        if(opciones[4] != 0){
            ingredientes.add(bacon);
        }
        if(opciones[5] != 0){
            ingredientes.add(tipoCarne[opciones[5]-1]);
        }
        if(opciones[6] != 0){
            ingredientes.add(estadoCarne[opciones[6]-1]);
        }
        if(opciones[0]==3) glutenFree = true;
        if(opciones[4]==0 && (opciones[5]==0 || opciones[5]==3)) esVegano = true;
        
        return new Plato(nombre,"Tu eliges",ingredientes,5.0,glutenFree,esVegano);
    }
}
