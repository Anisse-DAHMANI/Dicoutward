package com.example.dicoutward.ManagerDB;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.dicoutward.model.crafting.ItemCraftable;
import com.example.dicoutward.model.crafting.ItemCraftableDao;

@Database(entities = {ItemCraftable.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ItemCraftableDao itemCraftableDao();
    //test commit
}
