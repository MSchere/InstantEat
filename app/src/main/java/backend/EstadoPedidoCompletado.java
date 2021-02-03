package backend;

import java.io.Serializable;

/**
 * Estado concreto: Implementa la interfaz Estado. Implementa el comportamiento
 * específico de un estado.
 *
 * @author Salvador Oton
 */
public class EstadoPedidoCompletado implements EstadoPedido, Serializable {

	@Override
	public void ejecutar(Pedido p) {
		EstadoPedido completado = new EstadoPedidoCompletado();
		if (!p.getEstado().equals("Cancelado")) {
			p.setEstado("Completado");
			p.setEstadoPedido(completado);
		}

	}
}
