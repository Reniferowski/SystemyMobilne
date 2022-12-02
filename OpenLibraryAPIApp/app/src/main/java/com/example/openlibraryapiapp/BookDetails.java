package com.example.openlibraryapiapp;

import static com.example.openlibraryapiapp.MainActivity.IMAGE_URL_BASE;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class BookDetails extends AppCompatActivity {

    public final static String BOOK_EXTRA = "BOOK_EXTRA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        TextView bookTitleTextView = findViewById(R.id.book_title);
        TextView bookAuthorTextView = findViewById(R.id.book_author);
        TextView numberOfPagesTextView = findViewById(R.id.number_of_pages);

        ImageView bookCover = findViewById(R.id.img_cover);

        TextView hasEbookTextView = findViewById(R.id.has_ebook);

        Book book = (Book) getIntent().getSerializableExtra(BOOK_EXTRA);

        bookTitleTextView.setText(getString(R.string.title) + book.getTitle());
        bookAuthorTextView.setText(getString(R.string.author) + TextUtils.join(", ", book.getAuthors()));
        numberOfPagesTextView.setText(getString(R.string.pages) + book.getNumberOfPages());
        hasEbookTextView.setText(getString(R.string.ebook) + book.getHasEbook());

        if (book.getCover() != null) {
            Picasso.with(getApplicationContext())
                    .load(IMAGE_URL_BASE + book.getCover() + "-L.jpg")
                    .placeholder(R.drawable.ic_book).into(bookCover);
        } else {
            bookCover.setImageResource(R.drawable.ic_book);
        }
    }
}