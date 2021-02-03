package backend;

public class ObservadorConcretoEstado implements Observador {
	private String nombre;
	private String estado; // Nombre del observador.
	private Sujeto sujeto;

	/**
	 * Constructor.
	 *
	 * @param sujeto Sujeto (pedido) al que observamos.
	 */
	public ObservadorConcretoEstado(String nombre, String estado, Sujeto sujeto) {
//		this.nombre = nombre;
		this.estado = estado;
		this.sujeto = sujeto;

		sujeto.añadirObservador(this);
	}

	@Override
	public String actualizar() {
		String est = sujeto.getPedido().getEstado();

		if (est.equals("cancelado") & estado.equals("completado")) {
			notificarfinal(est);
			sujeto.eliminarObservador(this); // Si el estado esta completado o cancelado eliminamos el observador
		} else {
			if (estado.equals(est))
				notificarbase(est);
		}
		return est;

	}

	public void notificarbase(String estado) {
		// Metodo para notificar a la base de datos si el pedido esta en espera o en
		// proceso
	}

	public void notificarfinal(String estado) {
		// Método para notificar a la base de datos si el pedido esta cancelado o
		// completado
	}
}
