package backend;

import java.util.ArrayList;

/**
 * Interfaz de los métodos para deshacer los filtros del buscador
 */
public interface ComandoDeshacer {
    
    /**
     * Establece el valor del filtro del nombre
     * 
     * @param filtroNombre Filtro de nombre
     */
    public void setFiltroNombre(String filtroNombre);

    /**
     * Establece el valor del filtro del restaurante
     * 
     * @param filtroRestaurante Filtro del restaurante
     */
    public void setFiltroRestaurante(String filtroRestaurante);

    /**
     * Establece el estado del filtro del GlutenFree
     * 
     * @param filtroGlutenFree Filtro de GlutenFree
     */
    public void setFiltroGlutenFree(boolean filtroGlutenFree);

    /**
     * Establece el estado del filtro de vegano
     * 
     * @param filtroVegano Filtro de vegano
     */
    public void setFiltroVegano(boolean filtroVegano);
    
    /**
     * Deshace el filtro del nombre del buscador que le llama y vacía el filtro
     * 
     * @param listas Lista con los platos que pasan los filtros y lista con los que no lo cumplen
     * @return Lista con los platos que pasan los filtros y lista con los que no lo cumplen
     */
    public ArrayList<ArrayList<Plato>> deshacerNombre(ArrayList<ArrayList<Plato>> listas);
    
    /**
     * Deshace el filtro del restaurante del buscador que le llama y vacía el filtro
     * 
     * @param listas Lista con los platos que pasan los filtros y lista con los que no lo cumplen
     * @return Lista con los platos que pasan los filtros y lista con los que no lo cumplen
     */
    public ArrayList<ArrayList<Plato>> deshacerRestaurante(ArrayList<ArrayList<Plato>> listas);
    
    /**
     * Deshace el filtro de GlutenFree del buscador que le llama y quita el filtro
     * 
     * @param listas Lista con los platos que pasan los filtros y lista con los que no lo cumplen
     * @return Lista con los platos que pasan los filtros y lista con los que no lo cumplen
     */
    public ArrayList<ArrayList<Plato>> deshacerGlutenFree(ArrayList<ArrayList<Plato>> listas);
    
    /**
     * Deshace el filtro de vegano del buscador que le llama y quita el filtro
     * 
     * @param listas Lista con los platos que pasan los filtros y lista con los que no lo cumplen
     * @return Lista con los platos que pasan los filtros y lista con los que no lo cumplen
     */
    public ArrayList<ArrayList<Plato>> deshacerVegano(ArrayList<ArrayList<Plato>> listas);
    
}
