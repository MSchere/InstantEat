package com.example.instanteat;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;


public class AdapterOrder extends ArrayAdapter {
    Context context;
    ArrayList<String> rID, rRestaurantName, rDishes, rTotalPrices, rStates;

    public AdapterOrder(Context context,  ArrayList<String> rID, ArrayList<String> rRestaurantName, ArrayList<String> rDishes, ArrayList<String> rTotalPrices, ArrayList<String> rStates) {
        super(context, R.layout.row_order, R.id.textView, rRestaurantName);
        this.context = context;
        this.rID = rID;
        this.rRestaurantName = rRestaurantName;
        this.rDishes = rDishes;
        this.rTotalPrices = rTotalPrices;
        this.rStates = rStates;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = layoutInflater.inflate(R.layout.row_order, parent, false);
        TextView id = row.findViewById(R.id.orderIdListField);
        TextView restaurant = row.findViewById(R.id.restaurantNameListField);
        TextView total = row.findViewById(R.id.totalPriceListField);
        TextView dishes = row.findViewById(R.id.dishListField);
        TextView states = row.findViewById(R.id.stateListField);
        String state;

        id.setText(rID.get(position));
        restaurant.setText(rRestaurantName.get(position));
        total.setText(rTotalPrices.get(position) + "€");
        dishes.setText(rDishes.get(position));
        state = rStates.get(position);
        switch (state){
            case "Preparando":
                states.setTextColor(Color.rgb(245, 126, 66));
                break;
            case "Cancelado":
                states.setTextColor(Color.RED);
                break;
            case "Completado":
                states.setTextColor(Color.GREEN);
                break;
        }
        states.setText(rStates.get(position));
        return row;
    }
}
