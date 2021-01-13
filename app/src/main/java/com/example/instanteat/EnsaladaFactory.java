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
public class EnsaladaFactory {
    
    String[] base = {"Lechuga","Canonigos","Rucula"};
    String[] carne = {"Pollo","Pavo"};
    String[] queso = {"Chedar","Azul"};
    String maiz = "Maiz";
    String[] tomate = {"Normal","Cherry"};
    String[] aceitunas = {"Aceitunas","Olivas"};
    String[] salsa = {"Mayonesa","Alioli","Cesar"};
    
    public EnsaladaFactory(){}
    
    public Plato crearEnsalada(String nombre,int[] opciones){
        ArrayList<String> ingredientes = new ArrayList();
        boolean esVegano = false;
        boolean tieneGluten = true;
        
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
        
        return new Plato(nombre,5.0,ingredientes,esVegano,tieneGluten);
    }
}
