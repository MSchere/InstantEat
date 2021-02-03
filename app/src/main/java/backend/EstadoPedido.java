package backend;

/**
 * Interfaz que define los métodos que dependen del estado del objeto.
 *
 * @author Salvador Oton
 */
public interface EstadoPedido {

    /**
     * Modifica el estado del objeto
     *
     *
     */
    public void ejecutar(Pedido p);
}
