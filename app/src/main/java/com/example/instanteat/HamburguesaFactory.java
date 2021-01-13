/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.instanteat;
import java.util.ArrayList;

/**
 *
 * @author ajpaz
 */
public class HamburguesaFactory {
    
    String[] pan = {"Pan normal","Pan de masa madre","Pan sin gluten"};
    String lechuga = "Lechuga";
    String tomate = "Tomate";
    String[] cebolla = {"Cebolla cruda","Cebolla caramelizada"};
    String bacon = "Bacon";
    String[] tipoCarne = {"Vaca","Pollo","Vegana"};
    String[] estadoCarne = {"Poco hecha","Al punto","Muy hecha"};
    
    public HamburguesaFactory(){}
    
    public Plato crearHamburguesa(String nombre,int[] opciones){
        ArrayList<String> ingredientes = new ArrayList();
        boolean esVegano = false;
        boolean tieneGluten = true;
        
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
        if(opciones[0]==3) tieneGluten = false;
        if(opciones[4]==0 && (opciones[5]==0 || opciones[5]==3)) esVegano = true;
        
        return new Plato(nombre,ingredientes,esVegano,tieneGluten);
    }
}
