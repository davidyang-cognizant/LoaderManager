package com.example.apipractice;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        // Helps perform async loading of data
        if (getSupportLoaderManager().getLoader(0) != null) {
            getSupportLoaderManager().initLoader(0, null, this);
        }

        getNews();
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        String queryString = "";
        if (args != null) {
            queryString = args.getString("queryString");
        }
        return new NewsLoader(this, queryString);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONArray articles = jsonObject.getJSONArray("articles");
            List<Article> articleList = new ArrayList<>();
            for (int i = 0; i < articles.length(); i++) {
                JSONObject article = articles.getJSONObject(i);
                String title = "";
                String description = "";

                if (article.has("title")) {
                    title = article.getString("title");
                }
                if (article.has("description")) {
                    description = article.getString("description");
                }
                articleList.add(new Article(title, description));
            }
            for (Article article:
                 articleList) {
                Log.d("Pineapple", article.getTitle());
            }
            NewsAdapter newsAdapter = new NewsAdapter(this, articleList);
            recyclerView.setAdapter(newsAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }

    public void getNews() {
        /*
        Check for a connection to the web
         */
        ConnectivityManager connMng = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connMng != null) {
            networkInfo = connMng.getActiveNetworkInfo();
        }
        if (networkInfo != null && networkInfo.isConnected()) {
            Bundle queryBundle = new Bundle();
            queryBundle.putString("queryString", "");
            getSupportLoaderManager().restartLoader(0, queryBundle, this);
        }
    }
}