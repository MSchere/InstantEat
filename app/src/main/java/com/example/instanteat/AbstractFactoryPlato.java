package com.example.instanteat;

import java.util.ArrayList;

public class AbstractFactoryPlato implements IAbstractFactoryPlato{
    
    private PlatoFactory factoriaPlato = new PlatoFactory();
    private HamburguesaFactory factoriaHamburguesa = new HamburguesaFactory();
    private PizzaFactory factoriaPizza = new PizzaFactory();
    private EnsaladaFactory factoriaEnsalada = new EnsaladaFactory();
    
    /**
     * Crea un objeto de tipo Plato.
     *
     * @return Objeto de tipo Plato.
     */
    @Override
    public Plato creaPlato(String nombre,String restaurante,ArrayList<String> ingredientes,double precio,boolean tieneGluten, boolean esVegano){
        return factoriaPlato.crearPlato(nombre,restaurante,ingredientes,precio,tieneGluten,esVegano);
    }

    /**
     * Crea un objeto de tipo Hamburguesa.
     *
     * @return Objeto de tipo Hamburguesa.
     */
    @Override
    public Plato creaHamburguesa(String nombre,int[] opciones){
        return factoriaHamburguesa.crearHamburguesa(nombre, opciones);
    }
    
    /**
     * Crea un objeto de tipo Pizza.
     *
     * @return Objeto de tipo Pizza.
     */
    @Override
    public Plato creaPizza(String nombre, int[] opciones){
        return factoriaPizza.crearPizza(nombre, opciones);
    }
    
    /**
     * Crea un objeto de tipo Ensalada.
     *
     * @return Objeto de tipo Ensalada.
     */
    @Override
    public Plato creaEnsalada(String nombre, int[] opciones){
        return factoriaEnsalada.crearEnsalada(nombre, opciones);
    }
    
    
}
