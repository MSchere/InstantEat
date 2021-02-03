package backend;


public class ObservadorConcretoPrecio implements Observador { //Clase que aplica un descuento del 5% si se gasta mas de la cantidad indicada
	private String nombre;	//Nombre del observador.
    private double precio;			
    private Sujeto sujeto;			
    

    /**
     * Constructor.
     *
     * @param sujeto Sujeto (pedido) al que observamos.
     */
    public ObservadorConcretoPrecio(String nombre, double precio, Sujeto sujeto) {
        this.precio = precio;
        this.nombre = nombre;
        this.sujeto = sujeto;
       
        sujeto.añadirObservador(this);
    }

    @Override
    public String actualizar() {
        double pre = sujeto.getPedido().getPrecioTotal();
       

        if (pre > precio) {
           sujeto.getPedido().setPrecioTotal((sujeto.getPedido().getPrecioTotal()-(sujeto.getPedido().getPrecioTotal()/100)*5));
        	// Aplica un 5% de descuento al pedido 
            
        }
        return "actualizado";
    }
}
    
	

