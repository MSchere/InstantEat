package backend;

/**
 * Clase que implementa el observador de estados
 */
public class ObservadorConcretoEstado implements Observador {

    private String nombre; // Nombre del observador
    private String estado; // Estado actual
    private Sujeto sujeto; // Sujeto al que observamos

    /**
     * Constructor
     *
     * @param nombre Nombre del observador
     * @param estado Estado actual
     * @param sujeto Sujeto (pedido) al que observamos
     */
    public ObservadorConcretoEstado(String nombre, String estado, Sujeto sujeto) {
        this.nombre = nombre;
        this.estado = estado;
        this.sujeto = sujeto;

        sujeto.añadirObservador(this);
    }

    @Override
    public String actualizar() {
        String est = sujeto.getPedido().getEstado();

        if (est.equals("Cancelado") & estado.equals("Completado")) {
            notificarfinal(est);
            sujeto.eliminarObservador(this); // Si el estado esta completado o cancelado eliminamos el observador
        } else {
            if (estado.equals(est)) {
                notificarbase(est);
            }
        }
        return est;

    }

    /**
     * Notifica a la base de datos si el pedido esta en espera o en proceso
     *
     * @param estado Estado a notificar
     */
    public void notificarbase(String estado) {
        System.out.println("///" + nombre + " ha observado que el estado ha cambiado a " + estado + "///");
    }

    /**
     * Notifica a la base de datos si el pedido esta cancelado o completado
     *
     * @param estado Estado a notificar
     */
    public void notificarfinal(String estado) {
        System.out.println("///" + nombre + " ha observado que el estado ha cambiado a " + estado + "///");
    }
}
