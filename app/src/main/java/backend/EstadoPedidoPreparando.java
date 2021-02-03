package backend;

import java.io.Serializable;

/**
 * Estado concreto: Implementa la interfaz Estado. Implementa el comportamiento
 * específico de un estado.
 *
 * @author Salvador Oton
 */
public class EstadoPedidoPreparando implements EstadoPedido, Serializable {

    @Override
    public void ejecutar(Pedido p) {
        EstadoPedido preparando = new EstadoPedidoPreparando();
       
        if (!p.getEstado().equals("Completado") & !p.getEstado().equals("Cancelado") ) {
        	p.setEstado("Preparando");
        	p.setEstadoPedido(preparando);
        }
        
       
    }
}
