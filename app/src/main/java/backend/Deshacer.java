package backend;

import java.util.ArrayList;

/**
 * Clase que implementa los métodos para deshacer los filtros del buscador
 */
public class Deshacer implements ComandoDeshacer{

    private String filtroNombre,filtroRestaurante; // Contenido del filtro de nombre de plato y nombre de restaurante
    private boolean filtroGlutenFree,filtroVegano; // Estado de los filtros de GlutenFree y vegano
    
    /**
     * Constructor
     */
    public Deshacer() {
        filtroNombre = "";
        filtroRestaurante = "";
        filtroGlutenFree = false;
        filtroVegano = false;
    }

    @Override
    public void setFiltroNombre(String filtroNombre) {
        this.filtroNombre = filtroNombre;
    }

    @Override
    public void setFiltroRestaurante(String filtroRestaurante) {
        this.filtroRestaurante = filtroRestaurante;
    }

    @Override
    public void setFiltroGlutenFree(boolean filtroGlutenFree) {
        this.filtroGlutenFree = filtroGlutenFree;
    }

    @Override
    public void setFiltroVegano(boolean filtroVegano) {
        this.filtroVegano = filtroVegano;
    }
    
    @Override
    public ArrayList<ArrayList<Plato>> deshacerNombre(ArrayList<ArrayList<Plato>> listas){
        ArrayList<Plato> lista = listas.get(0);
        ArrayList<Plato> descartes = listas.get(1);
        ArrayList<Plato> temp = new ArrayList();
        Plato plato;
        boolean validoR,validoG,validoV;
        for(int i=0;i< descartes.size();i++){
            plato = descartes.get(i);
            validoR = false;
            validoG = false;
            validoV = false;
            if(filtroRestaurante.equals("")){
                validoR = true;
            }
            else if(!filtroRestaurante.equals("") && plato.getRestaurante().toUpperCase().contains(filtroRestaurante.toUpperCase())){
                validoR = true;
            }
            if(!filtroGlutenFree){
                validoG = true;
            }
            else if(plato.isGlutenFree()){
                validoG = true;
            }
            if(!filtroVegano){
                validoV = true;
            }
            else if(plato.isVegano()){
                validoV = true;
            }        
            if(validoR && validoG && validoV){
                lista.add(plato);
            }
            else{
                temp.add(plato);
            }
        }
        listas.set(0, lista);
        listas.set(1, temp);
        filtroNombre = "";
        return listas;
    }
    
    @Override
    public ArrayList<ArrayList<Plato>> deshacerRestaurante(ArrayList<ArrayList<Plato>> listas){
        ArrayList<Plato> lista = listas.get(0);
        ArrayList<Plato> descartes = listas.get(1);
        ArrayList<Plato> temp = new ArrayList();
        Plato plato;
        boolean validoN,validoG,validoV;
        for(int i=0;i< descartes.size();i++){
            plato = descartes.get(i);
            validoN = false;
            validoG = false;
            validoV = false;
            if(filtroNombre.equals("")){
                validoN = true;
            }
            else if(!filtroNombre.equals("") && plato.getNombre().toUpperCase().contains(filtroNombre.toUpperCase())){
                validoN = true;
            }
            if(!filtroGlutenFree){
                validoG = true;
            }
            else if(plato.isGlutenFree()){
                validoG = true;
            }
            if(!filtroVegano){
                validoV = true;
            }
            else if(plato.isVegano()){
                validoV = true;
            }        
            if(validoN && validoG && validoV){
                lista.add(plato);
            }
            else{
                temp.add(plato);
            }
        }
        listas.set(0, lista);
        listas.set(1, temp);
        filtroRestaurante = "";
        return listas;
    }
    
    @Override
    public ArrayList<ArrayList<Plato>> deshacerGlutenFree(ArrayList<ArrayList<Plato>> listas){
        ArrayList<Plato> lista = listas.get(0);
        ArrayList<Plato> descartes = listas.get(1);
        ArrayList<Plato> temp = new ArrayList();
        Plato plato;
        boolean validoN,validoR,validoV;
        for(int i=0;i< descartes.size();i++){
            plato = descartes.get(i);
            validoN = false;
            validoR = false;
            validoV = false;
            if(filtroNombre.equals("")){
                validoN = true;
            }
            else if(!filtroNombre.equals("") && plato.getNombre().toUpperCase().contains(filtroNombre.toUpperCase())){
                validoN = true;
            }
            if(filtroRestaurante.equals("")){
                validoR = true;
            }
            else if(!filtroRestaurante.equals("") && plato.getRestaurante().toUpperCase().contains(filtroRestaurante.toUpperCase())){
                validoR = true;
            }
            if(!filtroVegano){
                validoV = true;
            }
            else if(plato.isVegano()){
                validoV = true;
            }        
            if(validoN && validoR && validoV){
                lista.add(plato);
            }
            else{
                temp.add(plato);
            }
        }
        listas.set(0, lista);
        listas.set(1, temp);
        filtroGlutenFree = false;
        return listas;
    }
    
    @Override
    public ArrayList<ArrayList<Plato>> deshacerVegano(ArrayList<ArrayList<Plato>> listas){
        ArrayList<Plato> lista = listas.get(0);
        ArrayList<Plato> descartes = listas.get(1);
        ArrayList<Plato> temp = new ArrayList();
        Plato plato;
        boolean validoN,validoR,validoG;
        for(int i=0;i< descartes.size();i++){
            plato = descartes.get(i);
            validoN = false;
            validoR = false;
            validoG = false;
            if(filtroNombre.equals("")){
                validoN = true;
            }
            else if(!filtroNombre.equals("") && plato.getNombre().toUpperCase().contains(filtroNombre.toUpperCase())){
                validoN = true;
            }
            if(filtroRestaurante.equals("")){
                validoR = true;
            }
            else if(!filtroRestaurante.equals("") && plato.getRestaurante().toUpperCase().contains(filtroRestaurante.toUpperCase())){
                validoR = true;
            }
            if(!filtroGlutenFree){
                validoG = true;
            }
            else if(plato.isGlutenFree()){
                validoG = true;
            }        
            if(validoN && validoR && validoG){
                lista.add(plato);
            }
            else{
                temp.add(plato);
            }
        }
        listas.set(0, lista);
        listas.set(1, temp);
        filtroVegano = false;
        return listas;
    }
}
