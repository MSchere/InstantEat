package com.example.instanteat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.DecimalFormat;
import java.util.ArrayList;


public class AdapterDish extends ArrayAdapter {
    Context context;
    ArrayList<String> rName, rIngredients, rPrice;
    int[] image;

    public AdapterDish(Context context, ArrayList<String> rName, ArrayList<String> rIngredients, ArrayList<String> rPrice) {
        super(context, R.layout.row_dish, R.id.textView, rName);
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
        View row = layoutInflater.inflate(R.layout.row_dish, parent, false);
        TextView name = row.findViewById(R.id.dishNameListField);
        TextView ingredients = row.findViewById(R.id.ingredientsListField);
        TextView price = row.findViewById(R.id.dishPriceListField);

        name.setText(rName.get(position));
        ingredients.setText(rIngredients.get(position));
        DecimalFormat df = new DecimalFormat("#.00");
        price.setText(df.format(Double.valueOf(rPrice.get(position))) + "€");
        return row;
    }
}
