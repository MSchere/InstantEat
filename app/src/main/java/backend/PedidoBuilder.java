package backend;


import java.util.ArrayList;

/**
 * Constructor: Clase abstracta que especifica los métodos para crear las partes
 * del producto concreto.
 */
public abstract class PedidoBuilder {

    /**
     * Producto complejo a construir: Pedido.
     */
    protected Pedido pedido;

    /**
     * Devuelve el producto complejo que se construye.
     *
     * @return Producto Pedido.
     */
    public Pedido getPedido() {
        return pedido;
    }

    public void crearNuevoPedido(int id, String email, int telefono, String direccionCliente, String restaurante,
                                 String direccionRestaurante, ArrayList<String> platos, double precioTotal, String metodoPago,
                                 String estado){
        pedido = new PedidoPrincipal(id, email, telefono, direccionCliente, restaurante, direccionRestaurante, platos, precioTotal, metodoPago, estado);
    }
    
    /**
     * Define el ID del pedido.
     */
    public abstract void TomarID();
    
    /**
     * Añade los platos elegidos.
     */
    public abstract void AddPlatos();

    /**
     * Añade el Restaurante elegido.
     */
    public abstract void AddRestaurante();
    
    /**
     * Añade el Precio de la oferta.
     */
    public abstract void AddPrecio();
    
    
    
    

    
  
}
