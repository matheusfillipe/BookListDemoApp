package com.example.matheus.myreader.feature.bookList;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.example.matheus.myreader.feature.R;
import com.example.matheus.myreader.feature.bookList.data.AppDatabase;
import com.example.matheus.myreader.feature.bookList.data.Book;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class BookListView extends ListView {
    ArrayList<String> items;
    ArrayAdapter<String> adapter;
    int counter = 0;
    private List<Book> booksList = new ArrayList<Book>();
    private InsertBookAsyncTask insertBookAsyncTask = null;
    AppDatabase db;

    private void initVars(Context context) {
        items = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(context, R.layout.book_list_item, items);
        this.setAdapter((ListAdapter) adapter);
        db = AppDatabase.getDatabase(context);
        getAllBooks();
    }

    public BookListView(Context context) {
        super(context);
        initVars(context);
    }

    public BookListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initVars(context);
    }

    public BookListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initVars(context);
    }

    public BookListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initVars(context);
    }

    public void addItem(Uri file, String title) {
        counter++;
        items.add(counter + " - Title: " + title);
        adapter.notifyDataSetChanged();
    }

    public void saveItem(Uri file, String title) {
        Book b = new Book();
        b.setFilePath(file.getPath());
        b.setTitle(title);
        insertBook(b);
        addItem(file, title);
    }

    public int insertBook(Book b) {
        insertBookAsyncTask = new InsertBookAsyncTask(b);
        insertBookAsyncTask.execute((Void) null);
        return 0;
    }

    public void getAllBooks() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... url) {
                booksList = db.bookDao().getAll();
                return  null;
            }

            @Override
            protected void onPostExecute(Void voider) {

                for (final Book book : booksList) {
                    addItem(Uri.fromFile(new File(book.getFilePath())), book.getTitle());
                }
            }


        }.execute();
    }

    private class InsertBookAsyncTask extends AsyncTask<Void, Void, Void> {

        private Book book;
        public InsertBookAsyncTask(Book b) {
            this.book = b;
        }

        @Override
        protected Void doInBackground(Void... url) {
            db.bookDao().insert(book);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            getAllBooks();
        }
    }
}
