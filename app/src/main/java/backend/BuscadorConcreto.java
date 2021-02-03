package backend;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Clase que implementa los métodos del buscador
 */
public class BuscadorConcreto implements Buscador{
    
    private ArrayList<Plato> lista,descartes,listaOriginal;
    private ComandoDeshacer deshacer;

    /**
     * Constructor
     * @param lista Lista con los platos a la que hay que aplicar los filtros
     */
    public BuscadorConcreto(ArrayList<Plato> lista) {
        this.lista = new ArrayList();
        this.descartes = new ArrayList();
        this.listaOriginal = new ArrayList();
        for(int i=0;i<lista.size();i++){
            this.lista.add(lista.get(i));
            this.listaOriginal.add(lista.get(i));
        }
        Deshacer deshacer = new Deshacer();
        this.deshacer = deshacer;
    }
    
    @Override
    public void resetLista(){
        lista.clear();
        for(int i=0;i<listaOriginal.size();i++){
            lista.add(listaOriginal.get(i));
        }
        descartes.clear();
    }
    
    @Override
    public ArrayList<Plato> getLista(){
        return lista;
    }
    
    @Override
    public void deshacerNombre(){
        ArrayList<ArrayList<Plato>> listas = new ArrayList();
        listas.add(lista);
        listas.add(descartes);
        listas = deshacer.deshacerNombre(listas);
        lista = listas.get(0);
        descartes = listas.get(1);
    }
    
    @Override
    public void deshacerRestaurante(){
        ArrayList<ArrayList<Plato>> listas = new ArrayList();
        listas.add(this.lista);
        listas.add(this.descartes);
        listas = deshacer.deshacerRestaurante(listas);
        lista = listas.get(0);
        descartes = listas.get(1);
    }
    
    @Override
    public void deshacerGlutenFree(){
        ArrayList<ArrayList<Plato>> listas = new ArrayList();
        listas.add(this.lista);
        listas.add(this.descartes);
        listas = deshacer.deshacerGlutenFree(listas);
        lista = listas.get(0);
        descartes = listas.get(1);
    }

    @Override
    public void deshacerVegano(){
        ArrayList<ArrayList<Plato>> listas = new ArrayList();
        listas.add(this.lista);
        listas.add(this.descartes);
        listas = deshacer.deshacerVegano(listas);
        lista = listas.get(0);
        descartes = listas.get(1);
    }
    
    @Override
    public ArrayList<Plato> buscarNombrePlato(String nombre){
        if(!nombre.equals("")){
            ArrayList<Plato> listaTemp = new ArrayList();
            Plato temp;
            deshacer.setFiltroNombre(nombre);
            Iterator<Plato> it = lista.iterator();
            while(it.hasNext()){
                temp = it.next();
                if(temp.getNombre().toUpperCase().contains(nombre.toUpperCase())){
                    listaTemp.add(temp);
                }
                else{
                    descartes.add(temp);
                }
            }
            lista = listaTemp;
        }
        return lista;
    }
    
    @Override
    public ArrayList<Plato> buscarNombreRestaurante(String nombre){
        if(!nombre.equals("")){
            ArrayList<Plato> listaTemp = new ArrayList();
            Plato temp;
            deshacer.setFiltroRestaurante(nombre);
            Iterator<Plato> it = lista.iterator();
            while(it.hasNext()){
                temp = it.next();
                if(temp.getRestaurante().toUpperCase().contains(nombre.toUpperCase())){
                    listaTemp.add(temp);
                }
                else{
                    descartes.add(temp);
                }
            }
            lista = listaTemp;
        }
        return lista;
    }

    @Override
    public ArrayList<Plato> ordenarPrecioMayMen(){
        if(lista.size()>0){
            ArrayList<Plato> listaTemp = new ArrayList();
            Plato temp,platoMayor;
            while(lista.size()>0){
                Iterator<Plato> it = lista.iterator();
                platoMayor = it.next();
                while(it.hasNext()){
                    temp = it.next();
                    if(temp.getPrecio()>platoMayor.getPrecio()){
                        platoMayor = temp;
                    }
                }
                listaTemp.add(platoMayor);
                lista.remove(platoMayor);
            }
            lista = listaTemp;
        }
        return lista;
    }

    @Override
    public ArrayList<Plato> ordenarPrecioMenMay(){
        if(lista.size()>0){
            ArrayList<Plato> listaTemp = new ArrayList();
            Plato temp,platoMayor;
            while(lista.size()>0){
                Iterator<Plato> it = lista.iterator();
                platoMayor = it.next();
                while(it.hasNext()){
                    temp = it.next();
                    if(temp.getPrecio()<platoMayor.getPrecio()){
                        platoMayor = temp;
                    }
                }
                listaTemp.add(platoMayor);
                lista.remove(platoMayor);
            }
            lista = listaTemp;
        }
        return lista;
    }

    @Override
    public ArrayList<Plato> mostrarVeganos(){
        ArrayList<Plato> listaTemp = new ArrayList();
        Plato temp;
        deshacer.setFiltroVegano(true);
        Iterator<Plato> it = lista.iterator();
        while(it.hasNext()){
            temp = it.next();
            if(temp.isVegano()){
                listaTemp.add(temp);
            }
            else{
                descartes.add(temp);
            }
        }
        lista = listaTemp;
        
        return lista;
    }

    @Override
    public ArrayList<Plato> mostrarGlutenFree(){
        ArrayList<Plato> listaTemp = new ArrayList();
        Plato temp;
        deshacer.setFiltroGlutenFree(false);
        Iterator<Plato> it = lista.iterator();
        while(it.hasNext()){
            temp = it.next();
            if(temp.isGlutenFree()){
                listaTemp.add(temp);
            }
            else{
                descartes.add(temp);
            }
        }
        lista = listaTemp;
        
        return lista;
    }
    
}
