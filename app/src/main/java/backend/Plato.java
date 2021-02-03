package backend;

import java.util.ArrayList;

/**
 * Clase que reprenta al plato de un restaurante
 */
public class Plato {
    private String nombre; // Nombre del plato
    private String restaurante; // Nombre del restaurante del plato
    private double precio; // Precio del plato
    private ArrayList<String> ingredientes; // Cadena con los ingredientes del plato
    private boolean glutenFree; // Vale true si el plato no tiene gluten, false si tiene
    private boolean vegano; // Vale true si el plato es vegano, false si no lo es
    
    /**
     * Constructor
     * 
     * @param nombre Nombre del plato
     * @param restaurante Nombre del restaurante del plato
     * @param ingredientes Cadena con los ingredientes del plato
     * @param precio Precio del plato
     * @param tieneGluten Vale true si el plato no tiene gluten, false si tiene
     * @param esVegano Vale true si el plato es vegano, false si no lo es
     */
    public Plato(String nombre,String restaurante,ArrayList<String> ingredientes,double precio,boolean tieneGluten, boolean esVegano){
        this.nombre = nombre;
        this.restaurante = restaurante;
        this.precio = precio;
        this.ingredientes = ingredientes;
        this.vegano = esVegano;
        this.glutenFree = tieneGluten;
    }

    /**
     * Devuelve el nombre del plato
     * 
     * @return Nombre del plato
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del plato
     * 
     * @param nombre Nombre del plato
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Devuelve el nombre del restaurante del plato
     * 
     * @return Nombre del restaurante del plato
     */
    public String getRestaurante() {
        return restaurante;
    }

    /**
     * Establece el nombre del restaurante del plato
     * 
     * @param restaurante Nombre del restaurante del plato
     */
    public void setRestaurante(String restaurante) {
        this.restaurante = restaurante;
    }

    /**
     * Devuelve el precio del plato
     * 
     * @return Precio del plato
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * Establece el precio del plato
     * 
     * @param precio Precio del plato
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    /**
     * Devuelve la cadena con los ingredientes del plato
     * 
     * @return Cadena con los ingredientes del plato
     */
    public ArrayList<String> getIngredientes() {
        return ingredientes;
    }

    /**
     * Establece la cadena con los ingredientes del plato
     * 
     * @param ingredientes Cadena con los ingredientes del plato
     */
    public void setIngredientes(ArrayList<String> ingredientes) {
        this.ingredientes = ingredientes;
    }

    /**
     * Devuelve true si el plato no tiene gluten, false si tiene
     * 
     * @return true si el plato no tiene gluten, false si tiene
     */
    public boolean isGlutenFree() {
        return glutenFree;
    }

    /**
     * Establece true si el plato no tiene gluten, false si tiene
     * 
     * @param tieneGluten true si el plato no tiene gluten, false si tiene
     */
    public void setGlutenFree(boolean tieneGluten) {
        this.glutenFree = tieneGluten;
    }

    /**
     * Devuelve true si el plato es vegano, false si no lo es
     * 
     * @return true si el plato es vegano, false si no lo es
     */
    public boolean isVegano() {
        return vegano;
    }

    /**
     * Establece true si el plato es vegano, false si no lo es
     * 
     * @param esVegano true si el plato es vegano, false si no lo es
     */
    public void setVegano(boolean esVegano) {
        this.vegano = esVegano;
    }
    
    @Override
    public String toString(){
        String cadVegano="No";
        String cadGluten="No";
        if(vegano) cadVegano="Si";
        if(glutenFree) cadGluten="Si";
        return("Nombre: "+nombre+", Restaurante: "+restaurante+", Ingredientes: "+ingredientes.toString()+
                ", Precio: "+precio+", GlutenFree: "+cadGluten+", Vegano: "+cadVegano);
    }
}
