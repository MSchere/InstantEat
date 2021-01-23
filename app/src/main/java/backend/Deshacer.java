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
public class Deshacer implements ComandoDeshacer{

    private String filtroNombre,filtroRestaurante;
    private boolean filtroGluten,filtroVegano;
    
    public Deshacer() {
        filtroNombre = "";
        filtroRestaurante = "";
        filtroGluten = false;
        filtroVegano = false;
    }

    public void setFiltroNombre(String filtroNombre) {
        this.filtroNombre = filtroNombre;
    }

    public void setFiltroRestaurante(String filtroRestaurante) {
        this.filtroRestaurante = filtroRestaurante;
    }

    public void setFiltroGluten(boolean filtroGluten) {
        this.filtroGluten = filtroGluten;
    }

    public void setFiltroVegano(boolean filtroVegano) {
        this.filtroVegano = filtroVegano;
    }
    
    @Override
    public ArrayList<ArrayList<Plato>> deshacerNombre(ArrayList<ArrayList<Plato>> listas){
        ArrayList<Plato> lista = listas.get(0);
        ArrayList<Plato> descartes = listas.get(1);
        Plato plato;
        for(int i=0;i<descartes.size();i++){
            plato = descartes.get(i);
            if(plato.getRestaurante().toUpperCase().contains(filtroRestaurante.toUpperCase()) 
                    && plato.isGlutenFree()==filtroGluten && plato.isVegano()==filtroVegano){
                lista.add(plato);
                descartes.remove(plato);
            }
        }
        listas.set(0, lista);
        listas.set(1, descartes);
        filtroNombre = "";
        return listas;
    }
    
    @Override
    public ArrayList<ArrayList<Plato>> deshacerRestaurante(ArrayList<ArrayList<Plato>> listas){
        ArrayList<Plato> descartes = listas.get(1);
        Plato plato;
        for(int i=0;i<descartes.size();i++){
            plato = descartes.get(i);
            if(plato.getNombre().toUpperCase().contains(filtroNombre.toUpperCase()) 
                    && plato.isGlutenFree()==filtroGluten && plato.isVegano()==filtroVegano){
                listas.get(0).add(plato);
                descartes.remove(plato);
            }
        }
        filtroRestaurante = "";
        return listas;
    }
    
    @Override
    public ArrayList<ArrayList<Plato>> deshacerGluten(ArrayList<ArrayList<Plato>> listas){
        ArrayList<Plato> descartes = listas.get(1);
        Plato plato;
        for(int i=0;i<descartes.size();i++){
            plato = descartes.get(i);
            if(plato.getNombre().toUpperCase().contains(filtroNombre.toUpperCase()) 
                    && plato.getRestaurante().toUpperCase().contains(filtroRestaurante.toUpperCase()) 
                    && plato.isVegano()==filtroVegano){
                listas.get(0).add(plato);
                descartes.remove(plato);
            }
        }
        filtroGluten = true;
        return listas;
    }
    
    @Override
    public ArrayList<ArrayList<Plato>> deshacerVegano(ArrayList<ArrayList<Plato>> listas){
        ArrayList<Plato> descartes = listas.get(1);
        Plato plato;
        for(int i=0;i<descartes.size();i++){
            plato = descartes.get(i);
            if(plato.getNombre().toUpperCase().contains(filtroNombre.toUpperCase()) 
                    && plato.getRestaurante().toUpperCase().contains(filtroRestaurante.toUpperCase()) 
                    && plato.isGlutenFree()==filtroGluten){
                listas.get(0).add(plato);
                descartes.remove(plato);
            }
        }
        filtroVegano = false;
        return listas;
    }
}
