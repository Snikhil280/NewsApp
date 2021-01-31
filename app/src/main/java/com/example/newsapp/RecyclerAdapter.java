package com.example.newsapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.example.newsapp.model.Article;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.NewsViewHolder> {

    private List<Article> articles = new ArrayList<>();
    private RequestManager requestManager;
    private OnArticleListener mOnArticleListener;

    public RecyclerAdapter(RequestManager requestManager, OnArticleListener onArticleListener) {
        this.requestManager = requestManager;
        this.mOnArticleListener = onArticleListener;
//        this.articles = articles;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        // Inflate the layout view you have created for the list rows here
        View view = layoutInflater.inflate(R.layout.post_row, parent, false);
        return new NewsViewHolder(view, requestManager, mOnArticleListener);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, final int position) {
        final Article article = articles.get(position);
        if (article.getAuthor() != null){
            holder.author.setText(article.getAuthor());
        }
        if (article.getTitle() != null){
            holder.title.setText(article.getTitle());
        }
        if (article.getDescription() != null){
            holder.description.setText(article.getDescription());
        }
        if (article.getUrlToImage() != null){
            requestManager.load(article.getUrlToImage())
                    .into(holder.image);
        }
        if (article.getSource() != null){
            holder.sourceName.setText(article.getSource().getName());
        }
        if (article.getPublishedAt() != null){
            holder.publishedAt.setText(getTimeDifference(article.getPublishedAt()));
        }


    }

    @Override
    public int getItemCount() {
        return articles == null? 0: articles.size();
    }
    public void setArticles(List<Article> articles){
        this.articles = articles;
        notifyDataSetChanged();
    }
    public Article get(int position){
        return articles.get(position);
    }


    public class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RequestManager requestManager;
        private ImageView image;
        private TextView sourceName;
        private TextView title;
        private TextView author;
        private TextView publishedAt;
        private TextView description;
        OnArticleListener mOnArticleListener;

        public NewsViewHolder(@NonNull View itemView, RequestManager requestManager, OnArticleListener onArticleListener) {
            super(itemView);
            image = itemView.findViewById(R.id.imageView);
            sourceName = itemView.findViewById(R.id.sourceName);
            title = itemView.findViewById(R.id.titleText);
            author = itemView.findViewById(R.id.author);
            publishedAt = itemView.findViewById(R.id.publishedAt);
            description = itemView.findViewById(R.id.description);
            this.requestManager = requestManager;
            this.mOnArticleListener = onArticleListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mOnArticleListener.onArticleClick(getAdapterPosition());
        }
    }

    public interface OnArticleListener{
        void onArticleClick(int position);
    }

    public static String getTimeDifference(String s){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
        Date date = null;//You will get date object relative to server/client timezone wherever it is parsed
        try {
            date = dateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long diffInTime = new Date().getTime() - date.getTime();
        String timeDiff = "";
        if (diffInTime / (60*60*24*1000) > 0){
            long num = diffInTime / (60*60*24*1000);
            timeDiff = num + " days ago";
        }else if (diffInTime / (60*60*1000) > 0){
            long num = diffInTime / (60*60*1000);
            timeDiff = num + " hours ago";
        }else if (diffInTime / (60*1000) > 0){
            long num = diffInTime / (60*1000);
            timeDiff = num + " minutes ago";
        }else {
            timeDiff = "just now";
        }

        return timeDiff;
    }
}
