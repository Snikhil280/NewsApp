package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.example.newsapp.model.Article;
import com.example.newsapp.model.ResponseModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements RecyclerAdapter.OnArticleListener{
    private static final String TAG = "MainActivity";

    private SwipeRefreshLayout swipeRefreshLayout;
    private LottieAnimationView loadingAnimation;
    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;
    private LinearLayout errorLayout;
    private ServiceGenerator serviceGenerator = ServiceGenerator.getInstance();
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        loadingAnimation = findViewById(R.id.loadingAnimation);
        recyclerView = findViewById(R.id.recycler_view);
        errorLayout = findViewById(R.id.errorLayout);
        linearLayoutManager = new LinearLayoutManager(this);

        getDataFromApi();
        initRecyclerView();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // This method performs the actual data-refresh operation.
                // The method calls setRefreshing(false) when it's finished.
                getDataFromApi();
            }
        });


    }

    private void getDataFromApi(){
            serviceGenerator.getApi().getPosts("IN", "apikey")
                    .enqueue(new Callback<ResponseModel>() {
                        @Override
                        public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                            Log.d(TAG, "onResponse: " + response.body());
                            if(response.raw().networkResponse() != null){
                                Log.d(TAG, "onResponse: response is from NETWORK...");
                            }
                            else if(response.raw().cacheResponse() != null
                                    && response.raw().networkResponse() == null){
                                Log.d(TAG, "onResponse: response is from CACHE...");
                            }
                            swipeRefreshLayout.setRefreshing(false);
                            if(response.body() == null){
                                adapter.setArticles(new ArrayList<Article>());
                            }
                            else{
                                adapter.setArticles(response.body().getArticles());
                            }
                            loadingAnimation.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                            errorLayout.setVisibility(View.GONE);
                        }

                        @Override
                        public void onFailure(Call<ResponseModel> call, Throwable t) {
                            Log.e(TAG, "onFailure: ", t);
                            adapter.setArticles(new ArrayList<Article>());
                            loadingAnimation.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.GONE);
                            errorLayout.setVisibility(View.VISIBLE);
                        }
                    });

    }


    private void initRecyclerView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(20);
        recyclerView.addItemDecoration(itemDecorator);
        adapter = new RecyclerAdapter(initGlide(),this);
        recyclerView.setAdapter(adapter);
    }

    private RequestManager initGlide(){
    RequestOptions options = new RequestOptions()
            .placeholder(R.drawable.white_background)
            .error(R.drawable.white_background);

    return Glide.with(this)
            .setDefaultRequestOptions(options);
}

    public void retry(View view) {
        loadingAnimation.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        errorLayout.setVisibility(View.GONE);
        getDataFromApi();
    }

    @Override
    public void onArticleClick(int position) {
        Log.d(TAG, "onArticleClick: "+adapter.get(position).getDescription());
        Intent intent = new Intent(this, NewsDetails.class);
        intent.putExtra("NewsUrl", adapter.get(position).getUrl());
        startActivity(intent);
    }
}
