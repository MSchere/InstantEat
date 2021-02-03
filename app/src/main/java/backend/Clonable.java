package backend;

/**
 * Interfaz del patron prototype
 */
public interface Clonable {

    /**
     * Devuelve una copia de la instancia que lo ha llamado
     * 
     * @return copia de la instancia que lo ha llamado
     */
    public Object copy();
}
