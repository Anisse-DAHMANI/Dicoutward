package com.example.dicoutward.model.crafting;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface ItemCraftableDao {

    @Query("SELECT * FROM ItemCraftable")
    List<ItemCraftable> getAll();

    @Query("SELECT * FROM ItemCraftable WHERE catogerie = :categorie")
    List<ItemCraftable> getAllByStation(String categorie);

    @Insert
    void insert(ItemCraftable itemCraftables);



}
