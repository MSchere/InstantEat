package backend;

/**
 * Interfaz del patrón decorator
 */
public interface Decorable {

    /**
     * Añade o quita elementos de la interfaz gráfica dependiendo de que instancia lo llame
     */
    void decorate();
}
