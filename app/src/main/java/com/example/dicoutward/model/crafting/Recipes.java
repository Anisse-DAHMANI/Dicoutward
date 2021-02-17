package com.example.dicoutward.model.crafting;

public enum Recipes {
    SURVIVAL("Crafting/Survival"),
    ALCHEMY("Alchemy"),
    COOKING("Cooking");

    private String url;


    private Recipes(String url){
        this.url = url;
    }

    public String lastUrl(){ return this.url; }
}
