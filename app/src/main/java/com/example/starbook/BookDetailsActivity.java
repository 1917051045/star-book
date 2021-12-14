package com.example.starbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.starbook.model.Book;
import com.example.starbook.ui.home.BooksAdapter;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

import cz.msebera.android.httpclient.Header;

public class BookDetailsActivity extends AppCompatActivity {
    Book book;
    private TextView tvTitle, tvAuthor, tvDescription, tvPubl, tvISBN;
    private ImageView ivCover;
    private ProgressBar progressBar;
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        Intent intent = getIntent();
        this.book = intent.getParcelableExtra("book");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(this.book.getTitle());
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ivCover = findViewById(R.id.ivCover);
        tvTitle = findViewById(R.id.tvTitle);
        tvAuthor = findViewById(R.id.tvAuthor);
        tvDescription = findViewById(R.id.tvDescription);
        tvPubl = findViewById(R.id.tvPubl);
        tvISBN = findViewById(R.id.tvISBN);

        progressBar = findViewById(R.id.progressBar);
        tvTitle.setVisibility(View.INVISIBLE);
        tvAuthor.setVisibility(View.INVISIBLE);
        tvDescription.setVisibility(View.INVISIBLE);
        tvPubl.setVisibility(View.INVISIBLE);
        tvISBN.setVisibility(View.INVISIBLE);
        getBookDetails();
    }

    private void getBookDetails() {

        tvTitle.setText(this.book.getTitle());
        tvAuthor.setText(this.book.getAuthor());
        tvDescription.setText(Html.fromHtml(this.book.getDescription()));
        tvPubl.setText(this.book.getPublisher());
        tvISBN.setText(this.book.getIsbn());


        progressBar.setVisibility(View.VISIBLE);
        String imgUrl = this.book.getCover();
        Glide.with(BookDetailsActivity.this).load(imgUrl)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        tvTitle.setVisibility(View.VISIBLE);
                        tvAuthor.setVisibility(View.VISIBLE);
                        tvDescription.setVisibility(View.VISIBLE);
                        tvPubl.setVisibility(View.VISIBLE);
                        tvISBN.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                }).into(ivCover);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}