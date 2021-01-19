package backend;

import java.util.ArrayList;

public class Plato {
    private String nombre;
    private String restaurante;
    private double precio;
    private ArrayList<String> ingredientes;
    private boolean gluten;
    private boolean vegano;
    
    public Plato(String nombre,String restaurante,ArrayList<String> ingredientes,double precio,boolean tieneGluten, boolean esVegano){
        this.nombre = nombre;
        this.restaurante = restaurante;
        this.precio = precio;
        this.ingredientes = ingredientes;
        this.vegano = esVegano;
        this.gluten = tieneGluten;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(String restaurante) {
        this.restaurante = restaurante;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public ArrayList<String> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(ArrayList<String> ingredientes) {
        this.ingredientes = ingredientes;
    }

    public boolean isGluten() {
        return gluten;
    }

    public void setGluten(boolean tieneGluten) {
        this.gluten = tieneGluten;
    }

    public boolean isVegano() {
        return vegano;
    }

    public void setVegano(boolean esVegano) {
        this.vegano = esVegano;
    }
    
    @Override
    public String toString(){
        String cadVegano="No";
        String cadGluten="No";
        if(vegano) cadVegano="Si";
        if(gluten) cadGluten="Si";
        return("Nombre: "+nombre+", Restaurante: "+restaurante+", Ingredientes: "+ingredientes.toString()+
                ", Precio: "+precio+", Gluten: "+cadGluten+", Vegano: "+cadVegano);
    }
}
