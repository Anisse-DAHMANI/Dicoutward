package com.example.dicoutward.model.crafting;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dicoutward.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ItemCraftableAdapter extends RecyclerView.Adapter<ItemCraftableAdapter.ViewHolder> {


    private ArrayList<ItemCraftable> itemsCraftables;
    private Context context;
    private LayoutInflater mInflater;

    public ItemCraftableAdapter(ArrayList<ItemCraftable> itemsCraftables, Context context) {
        this.itemsCraftables = itemsCraftables;
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ItemCraftableAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_craftable, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ItemCraftableAdapter.ViewHolder holder, int position) {
        holder.bind(itemsCraftables.get(position));
    }

    @Override
    public int getItemCount() {
        return itemsCraftables.size();
    }

    public void setFilter (ArrayList<ItemCraftable> newList) {
        itemsCraftables = new ArrayList<>();
        itemsCraftables.addAll(newList);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView textViewNom;
        private ImageButton imageButtonCrafting;
        private LinearLayout linearLayoutIngredients;
        private TextView textViewStation;

        public ViewHolder(@NonNull View view) {
            super(view);
            textViewNom = view.findViewById(R.id.timesname);
            imageButtonCrafting = view.findViewById(R.id.imageButtonCrafting);
            linearLayoutIngredients = view.findViewById(R.id.list_ingredients);
            textViewStation = view.findViewById(R.id.station);

        }

        void bind(ItemCraftable itemCraftable){

            textViewNom.setText(itemCraftable.getNom());
            imageButtonCrafting.setImageBitmap(itemCraftable.getImage());
            linearLayoutIngredients.removeAllViews();
            for(String ingredient : itemCraftable.getIngredients()) {
                TextView textViewIngredient = new TextView(context);
                textViewIngredient.setText(" ● "+ingredient);
                textViewIngredient.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                textViewIngredient.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
                textViewIngredient.setTextColor(Color.WHITE);
                linearLayoutIngredients.addView(textViewIngredient);
            }
            textViewStation.setText(itemCraftable.getStation());
        }
    }



}