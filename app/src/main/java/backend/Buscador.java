package backend;

import java.util.ArrayList;

public interface Buscador {
    
    public void resetLista();
    
    public ArrayList<Plato> getLista();
    
    public void deshacerNombre();
    
    public void deshacerRestaurante();
    
    public void deshacerGlutenFree();
    
    public void deshacerVegano();
    
    public ArrayList<Plato> buscarNombrePlato(String nombre);
    
    public ArrayList<Plato> buscarNombreRestaurante(String nombre);

    public ArrayList<Plato> ordenarPrecioMayMen();

    public ArrayList<Plato> ordenarPrecioMenMay();

    public ArrayList<Plato> mostrarVeganos();

    public ArrayList<Plato> mostrarGlutenFree();
}
