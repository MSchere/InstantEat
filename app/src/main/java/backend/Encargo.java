package backend;

import java.util.ArrayList;

/**
 * Director: Construye un objeto complejo Pedido usando la clase abstracta
 * PedidoBuilder.
 */
public class Encargo {

    /**
     * Clase abstracta para construir el producto.
     */
    private PedidoBuilder pedidoBuilder = new PedidoBuilder() {
        @Override
        public void TomarID() {

        }

        @Override
        public void AddPlatos() {

        }

        @Override
        public void AddRestaurante() {

        }

        @Override
        public void AddPrecio() {

        }
    };

    /**
     * Establece el constructor concreto.
     *
     * @param pb Constructor concreto.
     */
    public void setPedidoBuilder(PedidoBuilder pb) {
        pedidoBuilder = pb;
    }

    /**
     * Devuelve el producto complejo creado.
     *
     * @return producto Pizza
     */
    public Pedido getPedido() {
        return pedidoBuilder.getPedido();
    }

    /**
     * Construye el producto Pizza paso a paso.
     */
    public void crearPedido(int id, String email, int telefono, String direccionCliente, String restaurante,
                            String direccionRestaurante, ArrayList<String> platos, double precioTotal, String metodoPago,
                            String estado) {
        pedidoBuilder.crearNuevoPedido(id, email, telefono, direccionCliente, restaurante, direccionRestaurante, platos, precioTotal, metodoPago, estado);
        pedidoBuilder.TomarID();
        pedidoBuilder.AddRestaurante();
        pedidoBuilder.AddPlatos();
        pedidoBuilder.AddPrecio();
        
    }
}
