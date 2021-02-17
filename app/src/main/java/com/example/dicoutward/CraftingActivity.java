package com.example.dicoutward;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.dicoutward.ManagerDB.AppDatabase;
import com.example.dicoutward.model.crafting.ItemCraftable;
import com.example.dicoutward.model.crafting.ItemCraftableAdapter;
import com.example.dicoutward.model.crafting.ItemCraftableDao;
import com.example.dicoutward.model.crafting.ItemsCraftable;
import com.example.dicoutward.model.crafting.Recipes;
import com.google.android.material.tabs.TabLayout;
/*
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

 */
import java.util.ArrayList;
import java.util.List;


public class CraftingActivity extends AppCompatActivity {

    private Recipes recipe;

    private ItemsCraftable data_survival;
    private ItemsCraftable data_alchemy;
    private ItemsCraftable data_cooking;

    private RecyclerView recyclerView;
    private ItemCraftableAdapter adapter;
    private ArrayList<ItemCraftable> itemCraftablesAdapter;
    private Context context = this;

    private AppDatabase db;
    private ItemCraftableDao itemCraftableDao;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.acticity_crafting);


        //INITIALISATION
        data_survival = new ItemsCraftable();
        data_alchemy = new ItemsCraftable();
        data_cooking = new ItemsCraftable();

        //DEPART PAR SUVIVAL
        recipe = Recipes.SURVIVAL;


        //PREPARER RECYCLERVIEW
        recyclerView = findViewById(R.id.listViewItemsCraftable);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);

        itemCraftablesAdapter = new ArrayList<>(data_survival.getItemsCraftable());
        adapter = new ItemCraftableAdapter(itemCraftablesAdapter, context);
        recyclerView.setAdapter(adapter);

        //EVENEMENTS
        final EditText editTextObjet = findViewById(R.id.editTextObjet);
        editTextObjet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s != null){
                    ArrayList<ItemCraftable> filter = null;
                    String str = s.toString();
                    switch(recipe){
                        case SURVIVAL:
                            filter = new ArrayList<>(data_survival.getItemsCraftable(str));
                            break;
                        case ALCHEMY:
                            filter = new ArrayList<>(data_alchemy.getItemsCraftable(str));
                            break;
                        case COOKING:
                            filter = new ArrayList<>(data_cooking.getItemsCraftable(str));
                            break;
                    }

                    updateAdapter(filter);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        final TabLayout tabCategoorie = findViewById(R.id.tab_recips);
        tabCategoorie.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                ArrayList<ItemCraftable> filter = null;
                String str_filter = editTextObjet.getText().toString();
                switch (tab.getPosition()){
                    case 0:
                        filter = new ArrayList<>(data_survival.getItemsCraftable(str_filter));
                        recipe = Recipes.SURVIVAL;
                        break;
                    case 1:
                        filter = new ArrayList<>(data_alchemy.getItemsCraftable(str_filter));
                        recipe = Recipes.ALCHEMY;
                        break;
                    case 2:
                        filter = new ArrayList<>(data_cooking.getItemsCraftable(str_filter));
                        recipe = Recipes.COOKING;
                        break;
                }
                updateAdapter(filter);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //DATABASE
        db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class, "database_outward.db").createFromAsset("database/database_outward.db").build();
        itemCraftableDao = db.itemCraftableDao();
        new AsyncGetItemsCraftables().execute();

    }

    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    private void prepareItemsCraftable(List<ItemCraftable> items){
        for(ItemCraftable i : items){
            if(i != null){
                switch (i.catogerie){
                    case "Crafting/Survival":
                        data_survival.addItemCraftable(i);
                        break;
                    case "Alchemy":
                        data_alchemy.addItemCraftable(i);
                        break;
                    case "Cooking":
                        data_cooking.addItemCraftable(i);
                        break;

                }
            }
        }
    }

    /*
    private void stockerItemsCraftable(){
        SharedPreferences myPrefs = getSharedPreferences(DATA_ITEMCRAFABLES, MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = myPrefs.edit();

        Gson gson = new Gson();

        String survival_json = gson.toJson(data_survival);
        prefsEditor.putString(Recipes.SURVIVAL.lastUrl(), survival_json);

        String alchemy_json = gson.toJson(data_alchemy);
        prefsEditor.putString(Recipes.ALCHEMY.lastUrl(), alchemy_json);

        String cooking_json = gson.toJson(data_cooking);
        prefsEditor.putString(Recipes.COOKING.lastUrl(), cooking_json);

        prefsEditor.apply();
    }

    private boolean recupererItemsCraftables(){
        SharedPreferences myPrefs = getSharedPreferences(DATA_ITEMCRAFABLES, MODE_PRIVATE);
        Gson gson = new Gson();


        SharedPreferences.Editor editor = myPrefs.edit();
        editor.remove(recipe.SURVIVAL.lastUrl());
        editor.remove(recipe.ALCHEMY.lastUrl());
        editor.remove(recipe.COOKING.lastUrl());
        editor.commit();


        String jsonSurvival = myPrefs.getString(recipe.SURVIVAL.lastUrl(), null);
        String jsonAlchemy = myPrefs.getString(recipe.ALCHEMY.lastUrl(), null);
        String jsonCooking = myPrefs.getString(recipe.COOKING.lastUrl(), null);

        if(jsonSurvival != null && jsonAlchemy != null && jsonCooking != null){
            data_survival = gson.fromJson(jsonSurvival, ItemsCraftable.class);
            data_alchemy = gson.fromJson(jsonAlchemy, ItemsCraftable.class);
            data_cooking = gson.fromJson(jsonCooking, ItemsCraftable.class);
            return false;
        } else {
            return true;
        }
    }

     */

    private void updateAdapter(ArrayList<ItemCraftable> filter){
        adapter.setFilter(filter);
        adapter.notifyDataSetChanged();
    }

    /*
    private Elements getTrFromTables(Element table){
        return table.select("tr");
    }

    private String getNomFromTr(Element td){
        return td.text();
    }

    private ArrayList<String> getNomsIngredients(Element td){
        ArrayList<String> str_ingredients = new ArrayList<>();
        Elements lis =  td.select("li");
        for(Element li : lis){
            str_ingredients.add(li.text());
        }
        return str_ingredients;
    }

    private String getUrlImageFromTr(Element td){
        return td.getElementsByTag("img").first().attr("src");
    }


    private String getStation(Element td) {
        return td.text();
    }

    private void ScrapingDocument(Elements tables, Recipes recipe){
        //LECTURE
        for(Element table : tables){

            Elements trs = getTrFromTables(table);
            trs.remove(0);

            for (Element tr : trs){

                Elements tds = tr.select("td");
                String imageUrl = getUrlImageFromTr(tds.first());
                String[] urlcutted = imageUrl.split("/");
                imageUrl = "";
                for(String str : urlcutted){
                    if(str.equals("revision")) break;
                    if(str != null) imageUrl = imageUrl + str + "/";
                }
                String nom = getNomFromTr(tds.first());
                ArrayList<String> ingredients = getNomsIngredients(tds.get(1));
                String station = getStation(tds.last());
                Bitmap image = null;
                try {
                    image = Picasso.get().load(imageUrl).get();
                } catch (IOException e){
                    e.printStackTrace();
                }

                ItemCraftable itemCraftable = new ItemCraftable(image, nom, ingredients, station, recipe.lastUrl());

                if (recipe.equals(Recipes.SURVIVAL)) {
                    data_survival.addItemCraftable(itemCraftable);
                    adapter.setFilter(new ArrayList<>(data_survival.getItemsCraftable("")));
                } else if (recipe.equals(Recipes.ALCHEMY)) {
                    data_alchemy.addItemCraftable(itemCraftable);
                } else if (recipe.equals(Recipes.COOKING)) {
                    data_cooking.addItemCraftable(itemCraftable);
                }
            }

        }
    }

    private class ScrapingAsync extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            for(Recipes r : Recipes.values()){
                try {
                    recipe = r;
                    Elements trs = Jsoup.connect(url+r.lastUrl()).get().select("table.wikitable");
                    ScrapingDocument(trs, r);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(mustStoreData) stockerItemsCraftable();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pd.dismiss();
            adapter.notifyDataSetChanged();
        }
    }

     */

    private class AsyncGetItemsCraftables extends AsyncTask<Void, Void, List<ItemCraftable>>{
        @Override
        protected List<ItemCraftable> doInBackground(Void... voids) {
            return itemCraftableDao.getAll();
        }

        @Override
        protected void onPostExecute(List<ItemCraftable> list) {
            super.onPostExecute(list);
            prepareItemsCraftable(list);
            updateAdapter(data_survival.getItemsCraftable());
            adapter.notifyDataSetChanged();
        }
    }

}
