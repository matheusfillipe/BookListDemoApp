package com.example.matheus.myreader.feature.bookList.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface BookDao {

    @Query("SELECT * FROM book")
    List<Book> getAll();

    @Query("SELECT * FROM book WHERE uid IN (:bookIds)")
    List<Book> loadAllByIds(int[] bookIds);

    @Query("SELECT * FROM book WHERE title LIKE :first AND " + "file_path LIKE :last LIMIT 1")
    Book findByName(String first, String last);



    @Insert
    void insert(Book book);

    @Delete
    void delete(Book book);

}
