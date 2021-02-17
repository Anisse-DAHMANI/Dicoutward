package com.example.dicoutward.model.crafting;


import java.util.ArrayList;
import java.util.LinkedList;

public class ItemsCraftable {


    private ArrayList<ItemCraftable> itemsCraftable;

    public ItemsCraftable(){
        itemsCraftable = new ArrayList<>();
    }

    public ArrayList<ItemCraftable> getItemsCraftable() {
        return itemsCraftable;
    }

    public void addItemCraftable(ItemCraftable item){
        itemsCraftable.add(item);
    }

    public ArrayList<ItemCraftable> getItemsCraftable(String search) {
        if(search.equals("")) return itemsCraftable;
        ArrayList<ItemCraftable> itemsCraftables_filter = new ArrayList<>();
        for(ItemCraftable item : itemsCraftable) if(item.getNom().toLowerCase().contains(search.toLowerCase()) || checkIngredientExists(item.getIngredients(), search)) itemsCraftables_filter.add(item);
        return itemsCraftables_filter;
    }

    private boolean checkIngredientExists(ArrayList<String> ingredients, String search){
        for(String ingredient : ingredients) if(ingredient.toLowerCase().contains(search.toLowerCase())) return true;
        return false;
    }


    @Override
    public String toString() {
        String str_con = "";
        for (ItemCraftable item : itemsCraftable){
            str_con += item.toString()+"\n";
        }
        return "ItemsCraftable{ itemsCraftable=" + str_con +
                '}';
    }
}
