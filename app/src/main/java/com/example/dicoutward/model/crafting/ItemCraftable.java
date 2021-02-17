package com.example.dicoutward.model.crafting;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedList;


@Entity
public class ItemCraftable {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "catogerie")
    public String catogerie;

    @ColumnInfo(name = "imageBytes", typeAffinity = ColumnInfo.BLOB)
    public byte[] imageBytes;

    @ColumnInfo(name = "nom")
    public String nom;

    @ColumnInfo(name = "ingredientsJson")
    public String ingredientsJson;

    @ColumnInfo(name = "station")
    public String station;

    public ItemCraftable(String catogerie, byte[] imageBytes, String nom, String ingredientsJson, String station){
        this.catogerie = catogerie;
        this.imageBytes = imageBytes;
        this.nom = nom;
        this.ingredientsJson = ingredientsJson;
        this.station = station;
    }

    @Ignore
    public ItemCraftable(Bitmap bitmap, String nom, ArrayList<String> ingredients, String station, String catogerie){
        /*
        this.imageUrl = "";
        String[] urlcutted = imageUrl.split("/");
        for(String str : urlcutted){
            if(str.equals("revision")) break;
            if(str != null) this.imageUrl = this.imageUrl + str + "/";
        }

         */
        Gson gson = new Gson();

        this.ingredientsJson = gson.toJson(ingredients);
        this.imageBytes = toBytes(bitmap);
        this.nom = nom;
        this.station = station;
        this.catogerie = catogerie;
    }


    private byte[] toBytes(Bitmap bitmap){
        ByteArrayOutputStream blob = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0 /* Ignored for PNGs */, blob);
        byte[] bitmapdata = blob.toByteArray();
        return bitmapdata;
    }

    public Bitmap getImage(){
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
    }


    public String getNom() {
        return nom;
    }

    public ArrayList<String> getIngredients() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<String>>(){}.getType();
        return gson.fromJson(ingredientsJson, type);
    }

    public String getStation() {
        return station;
    }

    @Override
    public String toString() {

        String str_con = "";
        for (String str : getIngredients()){
            str_con += str+"\n";
        }

        return "ItemCraftable{" +
                ", nom='" + nom + '\'' +
                ", ingredients=" + str_con +
                ", station='" + station + '\'' +
                '}';
    }


}
