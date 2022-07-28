package com.example.apipractice;

import androidx.loader.content.AsyncTaskLoader;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class NewsLoader extends AsyncTaskLoader<String> {
    private String queryString;
    /**
     * @param context
     * @deprecated
     */
    public NewsLoader(@NonNull Context context, String queryString) {
        super(context);
        this.queryString = queryString;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Nullable
    @Override
    public String loadInBackground() {
        return Utility.getInfo(queryString);
    }
}
