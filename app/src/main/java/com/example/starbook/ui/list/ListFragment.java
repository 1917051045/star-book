package com.example.starbook.ui.list;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.starbook.R;
import com.example.starbook.model.Category;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class ListFragment extends Fragment{
    private static final String TAG = "ListFragment";
    private ProgressBar progressBar;
    private RecyclerView CategoriesView;
    ArrayList<Category> CategoryList = new ArrayList<>();

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.list_fragment, container, false);
        progressBar = view.findViewById(R.id.progressBar);

        // Add the following lines to create RecyclerView
        CategoriesView = view.findViewById(R.id.rv_category);
        CategoriesView.setHasFixedSize(true);
        CategoriesView.setLayoutManager(new LinearLayoutManager(getContext()));
        updateCategoryList();
        return view;
    }

    private void addCategoryList(Category cat){
        CategoryList.add(cat);
    }

    private ArrayList<Category> getCategoryList(){
        return CategoryList;
    }

    private void updateCategoryList(){
        progressBar.setVisibility(View.VISIBLE);
        AsyncHttpClient client1 = new AsyncHttpClient();
        String apiKey = "RXNDVO5a1EQwRxAJ3cjtW0mCwkIf8Drr";
        String url1 = "https://api.nytimes.com/svc/books/v3/lists/names.json?api-key="+apiKey;
        client1.get(url1, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                progressBar.setVisibility(View.INVISIBLE);
                String result = new String(responseBody);
                Log.d(TAG, result);
                try {
                    JSONArray results = new JSONObject(result).getJSONArray("results");
                    for (int i = 2; i < results.length(); i++) {
                        JSONObject category = results.getJSONObject(i);
                        addCategoryList(new Category(category));
                        System.out.println(getCategoryList().toString());
                        CategoryAdapter adapter = new CategoryAdapter(getCategoryList(), getActivity());
                        CategoriesView.setAdapter(adapter);
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
