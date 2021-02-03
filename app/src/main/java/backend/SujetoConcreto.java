package backend;


import java.util.ArrayList;

public class SujetoConcreto implements Sujeto {

    // Lista de observadores de un valor.
    private ArrayList<Observador> observadores = new ArrayList<>();
    //Valor monitorizado.
    private Pedido pedido;

    @Override
    public void setPedido(Pedido p) {
        this.pedido = p;
    }

    @Override
    public Pedido getPedido() {
        return this.pedido;
    }

    @Override
    public void añadirObservador(Observador o) {
        if (observadores.contains(o) == false) {
            observadores.add(o);
        }
    }

    @Override
    public void eliminarObservador(Observador o) {
        observadores.remove(o);
    }

    @Override
    public void notificarObservadores() {
        //Pasamos el vector a un array para evitar la eliminación
        //de los observadores en tiempo de ejecución
        Object[] obArray = observadores.toArray();

        for (int i = 0; i < obArray.length; ++i) {
            Observador o = (Observador) obArray[i];
            o.actualizar();
        }
    }
}
