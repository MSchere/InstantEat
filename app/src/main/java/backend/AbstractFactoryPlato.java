package backend;

import java.util.ArrayList;

/**
 * Clase que implementa los métodos de fabricación de platos
 */
public class AbstractFactoryPlato implements IAbstractFactoryPlato{
    
    private PlatoFactory factoriaPlato = new PlatoFactory(); // Instancia de la fábrica de platos
    private HamburguesaFactory factoriaHamburguesa = new HamburguesaFactory(); // Instancia de la fábrica de hamburguesas
    private PizzaFactory factoriaPizza = new PizzaFactory(); // Instancia de la fábrica de pizzas
    private EnsaladaFactory factoriaEnsalada = new EnsaladaFactory(); // Instancia de la fábrica de ensaladas
    
    @Override
    public Plato creaPlato(String nombre,String restaurante,ArrayList<String> ingredientes,double precio,boolean tieneGluten, boolean esVegano){
        return factoriaPlato.crearPlato(nombre,restaurante,ingredientes,precio,tieneGluten,esVegano);
    }

    @Override
    public Plato creaHamburguesa(String nombre,int[] opciones){
        return factoriaHamburguesa.crearHamburguesa(nombre, opciones);
    }
    
    @Override
    public Plato creaPizza(String nombre, int[] opciones){
        return factoriaPizza.crearPizza(nombre, opciones);
    }
    
    @Override
    public Plato creaEnsalada(String nombre, int[] opciones){
        return factoriaEnsalada.crearEnsalada(nombre, opciones);
    }
    
    
}
