/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.instanteat;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author ajpaz
 */
public class PruebaFactoriasPlato {

    public static void main(String[] args){
        ArrayList<ArrayList<String>> lista = new ArrayList();
        ArrayList<String> sublista = new ArrayList();
        
        sublista.add("1");
        sublista.add("2");
        sublista.add("3");
        lista.add(sublista);
        sublista = new ArrayList();
        sublista.add("4");
        sublista.add("5");
        sublista.add("6");
        lista.add(sublista);
        sublista = new ArrayList();
        sublista.add("7");
        sublista.add("8");
        sublista.add("9");
        lista.add(sublista);
        
        Iterator<ArrayList<String>> it = lista.iterator();
    }
    
}
