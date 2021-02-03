package backend;

import java.util.ArrayList;

/**
 * Clase que fabrica ensaladas
 */
public class EnsaladaFactory {
    
    String[] base = {"Lechuga","Canonigos","Rucula"}; // Primera base de la ensalada
    String[] carne = {"Pollo","Pavo"}; // Tipos de carne
    String[] queso = {"Chedar","Azul"}; // Tipos de quesos
    String maiz = "Maiz"; // Maíz
    String[] tomate = {"Normal","Cherry"}; // Tipos de tomate
    String[] aceitunas = {"Aceitunas","Olivas"}; // Tipos de aceitunas
    String[] salsa = {"Mayonesa","Alioli","Cesar"}; // Tipos de salsa
    
    /**
     * Constructor
     */
    public EnsaladaFactory(){}
    
    /**
     * Crea un objeto de tipo Ensalada.
     *
     * @param nombre Nombre de la ensalada
     * @param opciones Opciones para seleccionar los ingredientes
     * @return Instancia de la clase Plato.
     */
    public Plato crearEnsalada(String nombre,int[] opciones){
        ArrayList<String> ingredientes = new ArrayList();
        boolean esVegano = false;
        boolean glutenFree = true;
        
        if(opciones[0] != 0){
            ingredientes.add(base[opciones[0]-1]);
        }
        if(opciones[1] != 0){
            ingredientes.add(carne[opciones[0]-1]);
        }
        if(opciones[2] != 0){
            ingredientes.add(queso[opciones[0]-1]);
        }
        if(opciones[3] != 0){
            ingredientes.add(maiz);
        }
        if(opciones[4] != 0){
            ingredientes.add(tomate[opciones[0]-1]);
        }
        if(opciones[5] != 0){
            ingredientes.add(aceitunas[opciones[0]-1]);
        }
        if(opciones[6] != 0){
            ingredientes.add(salsa[opciones[0]-1]);
        }
        if(opciones[1]==0 && (opciones[2]==0 || opciones[2]==1) && opciones[3]==0 &&
                (opciones[4]==0 || opciones[4]==3) && (opciones[5]==0 || opciones[5]==2)) esVegano = true;
        
        return new Plato(nombre,"Tu eliges",ingredientes,5.0,glutenFree,esVegano);
    }
}
