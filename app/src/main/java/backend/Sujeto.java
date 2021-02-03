package backend;


public interface Sujeto {

    /**
     * Establece el pedido al que monitorizar su estado
     */
    public void setPedido(Pedido p);

    /**
     * Devuelve el pedido al que monitorizar su estado.
     */
    public Pedido getPedido();

    /**
     * Añade un observador a la lista.
     *
     * @param o Observador.
     */
    public void añadirObservador(Observador o);

    /**
     * Elimina un observador de la lista
     *
     * @param o Observador.
     */
    public void eliminarObservador(Observador o);

    /**
     * Notifica a todos los observadores de que se ha producido un cambio
     */
    public void notificarObservadores();
}
