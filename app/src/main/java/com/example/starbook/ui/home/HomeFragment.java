package com.example.starbook.ui.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.starbook.R;
import com.example.starbook.model.Book;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

import cz.msebera.android.httpclient.Header;

public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";
    private ProgressBar progressBar;
    private RecyclerView listBooks;
    ArrayList<Book> listBook = new ArrayList<>();

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        progressBar = view.findViewById(R.id.progressBar);

        // Add the following lines to create RecyclerView
        listBooks = view.findViewById(R.id.rv_category);
        listBooks.setHasFixedSize(true);
        listBooks.setLayoutManager(new LinearLayoutManager(getContext()));
        updateListBooks();
        return view;
    }

    private void addListBooks(Book book){
        listBook.add(book);
    }

    private ArrayList<Book> getListBooks(){
        return listBook;
    }

    private void updateListBooks(){
        progressBar.setVisibility(View.VISIBLE);
        AsyncHttpClient client1 = new AsyncHttpClient();
        String apiKey = "RXNDVO5a1EQwRxAJ3cjtW0mCwkIf8Drr";
        String url1 = "https://api.nytimes.com/svc/books/v3/lists/current/combined-print-and-e-book-nonfiction.json?api-key="+apiKey;
        client1.get(url1, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                progressBar.setVisibility(View.INVISIBLE);
                String result = new String(responseBody);
                Log.d(TAG, result);
                try {
                    JSONObject results = new JSONObject(result).getJSONObject("results");
                    JSONArray items = results.getJSONArray("books");
                    for (int i = 0; i < results.length(); i++) {
                        JSONObject book = items.getJSONObject(i);
                        addListBooks(new Book(book));
                        BooksAdapter adapter = new BooksAdapter(getListBooks(), getActivity());
                        listBooks.setAdapter(adapter);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                progressBar.setVisibility(View.INVISIBLE);
                String errorMessage;
                switch (statusCode) {
                    case 401:
                        errorMessage = statusCode + " : Bad Request";
                        break;
                    case 403:
                        errorMessage = statusCode + " : Forbidden";
                        break;
                    case 404:
                        errorMessage = statusCode + " : Not Found";
                        break;
                    default:
                        errorMessage =  statusCode + " : " + error.getMessage();
                        break;
                }
            }
        });
    }
}
