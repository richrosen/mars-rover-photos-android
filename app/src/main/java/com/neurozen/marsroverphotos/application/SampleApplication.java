package com.neurozen.marsroverphotos.application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.neurozen.marsroverphotos.cache.AppCache;
import com.neurozen.marsroverphotos.network.PhotoApi;
import com.squareup.picasso.LruCache;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SampleApplication extends Application {

    private static final String PROTOCOL = "https";
    private static final String DOMAIN = "api.nasa.gov";
    private static final String API_KEY = "DEMO_KEY";  // replace with your own API key
    private static final String LOCAL_CACHE_NAME = "mars_rover_app_cache";
    private static final int PICASSO_MEMORY_CACHE_SIZE = 10 * 1024 * 1024;

    private PhotoApi api;
    private Gson gson;
    private Picasso picasso;
    private AppCache appCache;
    private SharedPreferences localCache;
    private ConnectivityManager connectivityManager;

    @Override
    public void onCreate() {
        gson = new GsonBuilder()
                .setLenient()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(constructBaseUrl())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        api = retrofit.create(PhotoApi.class);

        picasso = new Picasso.Builder(getApplicationContext())
                .downloader(new OkHttpDownloader(getApplicationContext()))
                .memoryCache(new LruCache(PICASSO_MEMORY_CACHE_SIZE))
                .build();

        appCache = new AppCache();

        localCache = getApplicationContext().getSharedPreferences(LOCAL_CACHE_NAME, MODE_PRIVATE);
        connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    private String constructBaseUrl() {
        StringBuilder sb = new StringBuilder()
                .append(PROTOCOL)
                .append("://")
                .append(DOMAIN)
                .append("/")
                ;
        return sb.toString();
    }

    public String getApiKey() {
        return API_KEY;
    }

    public PhotoApi getPhotoApi() {
        return api;
    }

    public Gson getGson() {
        return gson;
    }

    public Picasso getPicasso() {
        return picasso;
    }

    public AppCache getCache() {
        return appCache;
    }

    public SharedPreferences getLocalCache() {
        return localCache;
    }

    public boolean isNetworkConnected() {
//        NetworkInfo network = connectivityManager.getActiveNetworkInfo();
//        return (network != null && network.isConnected());
        return true;
    }
}
