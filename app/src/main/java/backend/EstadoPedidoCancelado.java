package backend;

import java.io.Serializable;

/**
 * Estado concreto: Implementa la interfaz Estado. Implementa el comportamiento
 * específico de un estado.
 *
 * @author Salvador Oton
 */
public class EstadoPedidoCancelado implements EstadoPedido, Serializable {

	@Override
	public void ejecutar(Pedido p) {
		EstadoPedido cancelado = new EstadoPedidoCancelado();
		if (!p.getEstado().equals("Cancelado") & !p.getEstado().equals("Completado")) {
			p.setEstado("Cancelado");
			p.setEstadoPedido(cancelado);
		}

	}
}
