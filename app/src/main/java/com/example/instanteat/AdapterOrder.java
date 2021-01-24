package com.example.instanteat;

import android.content.Context;
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
    ArrayList<String> rID, rRestaurantName, rDishes, rTotalPrice;
    int[] image;

    public AdapterOrder(Context context,  ArrayList<String> rID, ArrayList<String> rRestaurantName, ArrayList<String> rDishes, ArrayList<String> rTotalPrice) {
        super(context, R.layout.row_order, R.id.textView, rRestaurantName);
        this.context = context;
        this.rID = rID;
        this.rRestaurantName = rRestaurantName;
        this.rDishes = rDishes;
        this.rTotalPrice = rTotalPrice;
        this.image = image;
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

        id.setText(rID.get(position));
        restaurant.setText(rRestaurantName.get(position));
        total.setText(rTotalPrice.get(position) + "€");
        dishes.setText(rDishes.get(position));
        return row;
    }
}
