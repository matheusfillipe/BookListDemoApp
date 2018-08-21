package com.example.matheus.myreader.feature.bookList.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Book.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract BookDao bookDao();

    private static AppDatabase INSTANCE=null;

    public static AppDatabase getDatabase(final Context context){

        if(INSTANCE == null){
            synchronized (AppDatabase.class){
                if(INSTANCE==null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "bookListDB").build();
                }
            }
        }

        return INSTANCE;
    }


}
