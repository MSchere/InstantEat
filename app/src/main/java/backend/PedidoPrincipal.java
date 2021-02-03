package backend;

import java.io.Serializable;
import java.util.ArrayList;


public class PedidoPrincipal extends Pedido implements Serializable {

    /**
     * Lista de subpedidos.
     */
    private ArrayList<Pedido> subpedidos = new ArrayList<Pedido> ();


    public PedidoPrincipal(int id, String email, int telefono, String direccionCliente, String restaurante, String direccionRestaurante, ArrayList<String> platos, double precioTotal, String metodoPago, String estado) {
		super(id, email, telefono, direccionCliente, restaurante, direccionRestaurante, platos, precioTotal, metodoPago, estado);
		
	}


	@Override
    public void añadirSubpedido(Pedido p) {
        subpedidos.add(p); // Añade elemento a la lista.
    }

    @Override
    public void eliminarSubpedido(Pedido p) {
        subpedidos.remove(p);// Elimina elemento de la lista.
    }

    public ArrayList<Pedido> getSubpedidos() {
        return subpedidos;
    }

   public double getPrecioPedido() {
        double sum = getPrecioTotal(); // Suma el salario de este empleado.
        for (Pedido subpedido : subpedidos) {
            sum += subpedido.getPrecioPedido();// Suma el salario de su subordinado.
        }
        return sum;
    }

    @Override
    public String getDescripcion() {
        String desc = this.toString(); //Añade a la cadena el pedido
        for (Pedido subpedido : subpedidos) {
            //Añade a la cadena el puesto de su subordinado.
            desc += "\n\t### " + subpedido.getDescripcion();
        }
        return desc;
    }
}
