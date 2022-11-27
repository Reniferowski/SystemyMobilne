package com.example.libraryapp;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Book.class}, version = 1, exportSchema = false)
public abstract class BookDatabase extends RoomDatabase {
    private static BookDatabase databaseInstance;
    static final ExecutorService databaseWriteExecutor = Executors.newSingleThreadExecutor();

    public abstract BookDao bookDao();

    static BookDatabase getDatabase(final Context context) {
        if(databaseInstance == null) {
            databaseInstance = Room.databaseBuilder(context.getApplicationContext(),
                    BookDatabase.class, "book_database").addCallback(roomDatabaseCallback).build();
        }
        return databaseInstance;
    }

    private static final RoomDatabase.Callback roomDatabaseCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(() -> {
                BookDao dao = databaseInstance.bookDao();
                Book book = new Book("Clean Code", "Robert C. Martin");
                Book book1 = new Book("Droga Szamana: Początek", "Wasilij Machanienko");
                Book book2 = new Book("Droga Szamana: Gambit Kartosa", "Wasilij Machanienko");
                Book book3 = new Book("Droga Szamana: Tajemnica mrocznego lasu", "Wasilij Machanienko");
                Book book4 = new Book("Droga Szamana: Zamek widmo", "Wasilij Machanienko");
                Book book5 = new Book("Droga Szamana: Szachy Karmadonta", "Wasilij Machanienko");
                Book book6 = new Book("Droga Szamana: Nowy początek", "Wasilij Machanienko");
                Book book7 = new Book("Droga Szamana: Na troipie stwórcy", "Wasilij Machanienko");
                dao.insert(book);
                dao.insert(book1);
                dao.insert(book2);
                dao.insert(book3);
                dao.insert(book4);
                dao.insert(book5);
                dao.insert(book6);
                dao.insert(book7);
            });
        }
    };
}
