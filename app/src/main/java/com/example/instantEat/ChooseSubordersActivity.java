package com.example.instantEat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.text.DecimalFormat;
import java.util.ArrayList;

import backend.ObservadorConcretoEstado;
import backend.Pedido;
import backend.PedidoPrincipal;
import backend.Subpedido;
import backend.Sujeto;
import backend.SujetoConcreto;

public class ChooseSubordersActivity extends AppCompatActivity {
    TextView totalPriceText;
    ListView orderList;
    Button finishOrderEditorButton;
    AdapterOrder adapterOrder;
    PedidoPrincipal mainOrder;
    Pedido order;
    Sujeto subject;
    ObservadorConcretoEstado o;
    DecimalFormat df = new DecimalFormat("#.00");
    SharedPreferences prefs;
    ArrayList<String> selectedOrdersPrices, selectedOrdersIds;
    ArrayList<String> IDs, restaurantNames, dishes, prices, states;
    ArrayList<Integer> selectedPositions;
    String email;
    int orderId;
    Double mainOrderPrice, totalPrice;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.suborder_editor);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        bundle = getIntent().getExtras();
        order = (Pedido) bundle.getSerializable("order");
        orderId = (bundle.getInt("orderId"));
        mainOrderPrice = (bundle.getDouble("totalPrice"));
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        email = prefs.getString("email", "NULL");

        totalPriceText = findViewById(R.id.totalPriceText);
        finishOrderEditorButton = findViewById(R.id.finishOrderEditorButton);
        selectedOrdersPrices = new ArrayList<String>();
        selectedOrdersIds = new ArrayList<String>();
        selectedPositions = new ArrayList<Integer>();

        totalPriceText.setText(df.format(mainOrderPrice) + " €");
        fillLists(Utilities.getOrders(this, new String[] {email}, false, true));

        adapterOrder = new AdapterOrder(this, IDs, restaurantNames, dishes, prices, states);
        orderList = findViewById(R.id.orderList);
        orderList.setItemsCanFocus(false);
        orderList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        orderList.setAdapter(adapterOrder);
        orderList.setAdapter(adapterOrder);

        //Contiene una lista auxiliar para los elementos seleccionados
        orderList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    String selectedOrderId = IDs.get(i);
                    String selectedPrice = prices.get(i);
                    Integer pos = i;
                    if (selectedOrdersIds.contains(selectedOrderId)) { //deselecciona el objeto
                        view.setBackgroundColor(getResources().getColor(R.color.white));
                        selectedOrdersIds.remove(selectedOrderId);
                        selectedOrdersPrices.remove(selectedPrice);
                        selectedPositions.remove(pos);
                    } else {
                        view.setBackgroundColor(getResources().getColor(R.color.purple_200));
                        selectedOrdersIds.add(selectedOrderId);
                        selectedOrdersPrices.add(selectedPrice);
                        selectedPositions.add(pos);
                    }
                    //Utilities.showToast(getApplicationContext(), selectedPositions.get(i) + "");
                    totalPrice = calculatePrice();
                }
                catch (Exception e) {
                    e.printStackTrace();
                    Utilities.showToast(getApplicationContext(), "Error en posición: " + i);
                }
            }
        });
        orderList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                for(int pos:selectedPositions){
                    getViewByPosition(pos, orderList).setBackgroundColor(getResources().getColor(R.color.purple_200));
                }
            }
        });

        finishOrderEditorButton.setOnClickListener(v -> {
                    if (selectedOrdersIds.size()!=0) {
                        mainOrder = new PedidoPrincipal(order.getId(), order.getEmail(), order.getTelefono(), order.getDireccionCliente(), order.getRestaurante(),
                                order.getDireccionRestaurante(), order.getPlatos(), 0, order.getMetodoPago(), "Preparando con subpedidos");
                        observeState("Preparando con subpedidos");
                        Pedido tempOrder;
                        Subpedido subOrder;
                        for (String id : selectedOrdersIds) {
                            tempOrder = Utilities.getOrder(getApplicationContext(), id);
                            subOrder = new Subpedido(tempOrder.getId(), tempOrder.getEmail(), 0, "", tempOrder.getRestaurante(),
                                    tempOrder.getDireccionRestaurante(), tempOrder.getPlatos(), tempOrder.getPrecioTotal(), "", "");
                            mainOrder.añadirSubpedido(subOrder);
                        }
                        mainOrder.setPrecioTotal(mainOrder.getPrecioPedido());
                        Utilities.insertMainOrder(getApplicationContext(), mainOrder);

                        startActivity(new Intent(getApplicationContext(), ClientMenuActivity.class).addFlags(
                                Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                        Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                        Intent.FLAG_ACTIVITY_NEW_TASK));
                    }
                    else {
                        Utilities.showToast(getApplicationContext(), "Ningún subpedido seleccionado");
                    }
        });
    }
    //Encontrado en stack overflow, créditos al usuario VVB
    //Devuelve la view deseada dada la listView y una posición
    public View getViewByPosition(int pos, ListView listView) {
        int firstPos = listView.getFirstVisiblePosition();
        int lastPos = firstPos + listView.getChildCount() - 1;
        if (pos < firstPos || pos > lastPos ) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            int childIndex = pos - firstPos;
            return listView.getChildAt(childIndex);
        }
    }

    //Calcula el precio extrayendo los numeros de la string.
    private double calculatePrice() {
        double price = mainOrderPrice;
        String doubleValue;
        for(String item:selectedOrdersPrices){
            doubleValue = item.replaceAll("[^\\d.]", "");
            price = price + Double.parseDouble(doubleValue);
        }
        totalPriceText.setText(df.format(price) + " €");
        return price;
    }

    private void fillLists(ArrayList<Pedido> orderList){
        IDs = new ArrayList<String>();
        restaurantNames = new ArrayList<String>();
        dishes = new ArrayList<String>();
        prices = new ArrayList<String>();
        states = new ArrayList<String>();
        for (Pedido order:orderList){
            IDs.add(String.valueOf(order.getId()));
            restaurantNames.add(order.getRestaurante());
            prices.add(String.valueOf(order.getPrecioTotal()));
            dishes.add(Utilities.arrayListToString(order.getPlatos()));
            states.add(order.getEstado());
        }
    }

    private void observeState(String state){
        subject = new SujetoConcreto();
        subject.setPedido(mainOrder);
        o = new ObservadorConcretoEstado("Observador", "Preparando con subpedidos", subject);
        subject.añadirObservador(o);
        order.setEstado(state);
        o.actualizar();
    }

}