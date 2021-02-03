package backend;

import java.util.ArrayList;

/**
 * Interfaz en la que se definen los métodos de fabricación de platos
 */
public interface IAbstractFactoryPlato {
    
    /**
     * Devuelve una instancia de la clase Plato con los argumentos como atributos 
     * 
     * @param nombre Nombre del plato
     * @param restaurante Nombre del restaurante del plato
     * @param ingredientes Cadena con los ingredientes del plato
     * @param precio Precio del plato
     * @param glutenFree Vale true si el plato no tiene gluten, false si tiene
     * @param esVegano Vale true si el plato es vegano, false si no lo es
     * @return Nueva instancia de la clase Plato
     */
    public Plato creaPlato(String nombre,String restaurante,ArrayList<String> ingredientes,double precio,boolean glutenFree, boolean esVegano);

    /**
     * Crea un objeto de tipo Hamburguesa.
     *
     * @param nombre Nombre de la hamburguesa
     * @return Instancia de la clase Plato.
     */
    public Plato creaHamburguesa(String nombre, int[] opciones);
    
    /**
     * Crea un objeto de tipo Pizza.
     *
     * @param nombre Nombre de la pizza
     * @param opciones Opciones para seleccionar los ingredientes
     * @return Instancia de la clase Plato.
     */
    public Plato creaPizza(String nombre, int[] opciones);
    
    /**
     * Crea un objeto de tipo Ensalada.
     *
     * @param nombre Nombre de la ensalada
     * @param opciones Opciones para seleccionar los ingredientes
     * @return Instancia de la clase Plato.
     */
    public Plato creaEnsalada(String nombre, int[] opciones);
    
}
