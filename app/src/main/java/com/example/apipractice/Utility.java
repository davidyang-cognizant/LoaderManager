package com.example.apipractice;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;



public class Utility {
    private static final String API_KEY = "abfcbea2369c407b979303f7171b58dd";
    private static final String API_KEY_TEXT = "apiKey";
    private static final String NEWS_URL = "https://newsapi.org/v2/top-headlines?";
    private static final String SOURCES = "sources";

    static String getInfo(String queryString) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String newsJSONString = null;
        try {
            Uri uri = Uri.parse(NEWS_URL).buildUpon()
                    .appendQueryParameter(SOURCES, "bbc-news")
                    .appendQueryParameter(API_KEY_TEXT, API_KEY)
                    .build();

            // Build the URL
            URL requestURL = new URL(uri.toString());
            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
            urlConnection.connect();


            // Get the input stream

            InputStream inputStream = urlConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder builder = new StringBuilder();

            String line;

            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            if(builder.length() == 0){
                return null;
            }
            newsJSONString = builder.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(urlConnection != null){
                urlConnection.disconnect();
            }
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Log.d("Pineapple", newsJSONString);
        return newsJSONString;
    }
}
