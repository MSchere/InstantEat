package backend;

import java.util.ArrayList;

public interface ComandoDeshacer {
    
    public void setFiltroNombre(String filtroNombre);

    public void setFiltroRestaurante(String filtroRestaurante);

    public void setFiltroGlutenFree(boolean filtroGlutenFree);

    public void setFiltroVegano(boolean filtroVegano);
    
    public ArrayList<ArrayList<Plato>> deshacerNombre(ArrayList<ArrayList<Plato>> listas);
    
    public ArrayList<ArrayList<Plato>> deshacerRestaurante(ArrayList<ArrayList<Plato>> listas);
    
    public ArrayList<ArrayList<Plato>> deshacerGlutenFree(ArrayList<ArrayList<Plato>> listas);
    
    public ArrayList<ArrayList<Plato>> deshacerVegano(ArrayList<ArrayList<Plato>> listas);
    
}
