/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend;

import java.util.ArrayList;

/**
 *
 * @author ajpaz
 */
public class PizzaFactory {
    
    String[] masa = {"Masa normal","Masa fina","Masa sin gluten"};
    String rellenoBorde = "Queso";
    String[] base1 = {"Tomate","Salsa barbacoa"};
    String base2 = "Queso";
    String[] ingrediente1 = {"Bacon","Pollo","Espinacas"};
    String[] ingrediente2 = {"Anchoas","Maiz","Salami"};
    String[] ingrediente3 = {"Setas","Pimiento","Aceitunas"};
    
    public PizzaFactory(){}
    
    public Plato crearPizza(String nombre,int[] opciones){
        ArrayList<String> ingredientes = new ArrayList();
        boolean esVegano = false;
        boolean glutenFree = false;
        
        if(opciones[0] != 0){
            ingredientes.add(masa[opciones[0]-1]);
        }
        if(opciones[1] != 0){
            ingredientes.add(rellenoBorde);
        }
        if(opciones[2] != 0){
            ingredientes.add(base1[opciones[0]-1]);
        }
        if(opciones[3] != 0){
            ingredientes.add(base2);
        }
        if(opciones[4] != 0){
            ingredientes.add(ingrediente1[opciones[0]-1]);
        }
        if(opciones[5] != 0){
            ingredientes.add(ingrediente2[opciones[0]-1]);
        }
        if(opciones[6] != 0){
            ingredientes.add(ingrediente3[opciones[0]-1]);
        }
        if(opciones[0]==3) glutenFree = true;
        if(opciones[1]==0 && (opciones[2]==0 || opciones[2]==1) && opciones[3]==0 &&
                (opciones[4]==0 || opciones[4]==3) && (opciones[5]==0 || opciones[5]==2)) esVegano = true;
        
        return new Plato(nombre,"Tu eliges",ingredientes,5.0,glutenFree,esVegano);
    }
}
