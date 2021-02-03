package backend;

import java.util.ArrayList;

/**
 * Interfaz del buscador
 */
public interface Buscador {
    
    /**
     * Devuelve la lista a su estado original sin filtros
     */
    public void resetLista();
    
    /**
     * Devuelve la lista de los platos que cumplen el filtro
     * 
     * @return Lista de los platos que cumplen el filtro
     */
    public ArrayList<Plato> getLista();
    
    /**
     * Llama al método deshacerNombre de la clase Deshacer
     */
    public void deshacerNombre();
    
    /**
     * Llama al método deshacerRestaurante de la clase Deshacer
     */
    public void deshacerRestaurante();
    
    /**
     * Llama al método deshacerGlutenFree de la clase Deshacer
     */
    public void deshacerGlutenFree();
    
    /**
     * Llama al método deshacerVegano de la clase Deshacer
     */
    public void deshacerVegano();
    
    /**
     * Filtra la lista de platos actual con el nombre que entra como argumento
     * 
     * @param nombre Nuevo valor del filtro
     * @return Lista con los platos filtrados
     */
    public ArrayList<Plato> buscarNombrePlato(String nombre);
    
    /**
     * Filtra la lista de platos actual con el restaurante que entra como argumento
     * 
     * @param nombre Nuevo valor del filtro
     * @return Lista con los platos filtrados
     */
    public ArrayList<Plato> buscarNombreRestaurante(String nombre);

    /**
     * Ordena la lista de platos actual de mayor a menor precio
     * 
     * @return Lista con los platos ordenados
     */
    public ArrayList<Plato> ordenarPrecioMayMen();

    /**
     * Ordena la lista de platos actual de menor a mayor precio
     * 
     * @return Lista con los platos ordenados
     */
    public ArrayList<Plato> ordenarPrecioMenMay();

    /**
     * Filtra la lista de platos actual dejando solo los veganos
     * 
     * @return Lista con los platos filtrados
     */
    public ArrayList<Plato> mostrarVeganos();

    /**
     * Filtra la lista de platos actual dejando solo los que no tienen gluten
     * 
     * @return Lista con los platos filtrados
     */
    public ArrayList<Plato> mostrarGlutenFree();
}
