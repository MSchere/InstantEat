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


public class MyAdapter extends ArrayAdapter {
    Context context;
    ArrayList<String> rName, rIngredients, rPrice;
    int[] image;

    public MyAdapter(Context context, ArrayList<String> rName, ArrayList<String> rIngredients, ArrayList<String> rPrice) {
        super(context, R.layout.row, R.id.textView, rName);
        this.context = context;
        this.rName = rName;
        this.rIngredients = rIngredients;
        this.rPrice = rPrice;
        this.image = image;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = layoutInflater.inflate(R.layout.row, parent, false);
        TextView name = row.findViewById(R.id.dishNameListField);
        TextView ingredients = row.findViewById(R.id.ingredientsListField);
        TextView price = row.findViewById(R.id.dishPriceListField);

        name.setText(rName.get(position));
        ingredients.setText(rIngredients.get(position));
        price.setText(rPrice.get(position) + "€");
        return row;
    }
}
