package backend;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Hoja. Empleado sin subordinados. Deja vacíos los métodos de añadir y eliminar
 * subordinado.
 */
public class Subpedido extends Pedido implements Serializable {

   

    public Subpedido(int id, String email, int telefono, String direccionCliente, String restaurante, String direccionRestaurante, ArrayList<String> platos, double precioTotal, String metodoPago, String estado) {
		super(id, email,telefono, direccionCliente, restaurante, direccionRestaurante, platos, precioTotal,  metodoPago, estado);
	}
    

	
	@Override
    public void añadirSubpedido(Pedido p) {
    }

    @Override
    public void eliminarSubpedido(Pedido p) {
    }

  
    @Override
	public double getPrecioPedido() {
    	return super.getPrecioTotal();
	}



	@Override
    public String getDescripcion() {
        return "### " + super.toString();
    }
}
