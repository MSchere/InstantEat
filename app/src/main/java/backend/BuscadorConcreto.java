package backend;

import java.util.ArrayList;
import java.util.Iterator;

public class BuscadorConcreto implements Buscador{
    
    private ArrayList<Plato> lista,listaOriginal;

    public BuscadorConcreto(ArrayList<Plato> lista) {
        this.lista = new ArrayList();
        this.listaOriginal = new ArrayList();
        for(int i=0;i<lista.size();i++){
            this.lista.add(lista.get(i));
            this.listaOriginal.add(lista.get(i));
        }
    }
    
    @Override
    public void resetLista(){
        lista.clear();
        for(int i=0;i<listaOriginal.size();i++){
            lista.add(listaOriginal.get(i));
        }
    }
    
    @Override
    public ArrayList<Plato> buscarNombrePlato(String nombre){
        ArrayList<Plato> listaTemp = new ArrayList();
        Plato temp;
        Iterator<Plato> it = lista.iterator();
        while(it.hasNext()){
            temp = it.next();
            if(temp.getNombre().toUpperCase().contains(nombre.toUpperCase())){
                listaTemp.add(temp);
            }
        }
        lista = listaTemp;
        
        return lista;
    }
    
    @Override
    public ArrayList<Plato> buscarNombreRestaurante(String nombre){
        ArrayList<Plato> listaTemp = new ArrayList();
        Plato temp;
        Iterator<Plato> it = lista.iterator();
        while(it.hasNext()){
            temp = it.next();
            if(temp.getRestaurante().toUpperCase().contains(nombre.toUpperCase())){
                listaTemp.add(temp);
            }
        }
        lista = listaTemp;
        
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
        Iterator<Plato> it = lista.iterator();
        while(it.hasNext()){
            temp = it.next();
            if(temp.isVegano()){
                listaTemp.add(temp);
            }
        }
        lista = listaTemp;
        
        return lista;
    }

    @Override
    public ArrayList<Plato> mostrarNoVeganos(){
        ArrayList<Plato> listaTemp = new ArrayList();
        Plato temp;
        Iterator<Plato> it = lista.iterator();
        while(it.hasNext()){
            temp = it.next();
            if(!temp.isVegano()){
                listaTemp.add(temp);
            }
        }
        lista = listaTemp;
        
        return lista;
    }

    @Override
    public ArrayList<Plato> mostrarGluten(){
        ArrayList<Plato> listaTemp = new ArrayList();
        Plato temp;
        Iterator<Plato> it = lista.iterator();
        while(it.hasNext()){
            temp = it.next();
            if(temp.isGluten()){
                listaTemp.add(temp);
            }
        }
        lista = listaTemp;
        
        return lista;
    }

    @Override
    public ArrayList<Plato> mostrarNoGluten(){
        ArrayList<Plato> listaTemp = new ArrayList();
        Plato temp;
        Iterator<Plato> it = lista.iterator();
        while(it.hasNext()){
            temp = it.next();
            if(!temp.isGluten()){
                listaTemp.add(temp);
            }
        }
        lista = listaTemp;
        
        return lista;
    }
    
}
