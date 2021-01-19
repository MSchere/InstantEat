package backend;

import java.util.ArrayList;

public interface Buscador {
    
    public void resetLista();
    
    public ArrayList<Plato> buscarNombrePlato(String nombre);
    
    public ArrayList<Plato> buscarNombreRestaurante(String nombre);

    public ArrayList<Plato> ordenarPrecioMayMen();

    public ArrayList<Plato> ordenarPrecioMenMay();

    public ArrayList<Plato> mostrarVeganos();

    public ArrayList<Plato> mostrarNoVeganos();

    public ArrayList<Plato> mostrarGluten();

    public ArrayList<Plato> mostrarNoGluten();
}
