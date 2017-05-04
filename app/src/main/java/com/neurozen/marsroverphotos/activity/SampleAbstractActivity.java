package com.neurozen.marsroverphotos.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;

import com.google.gson.Gson;
import com.neurozen.marsroverphotos.R;
import com.neurozen.marsroverphotos.application.SampleApplication;
import com.neurozen.marsroverphotos.cache.AppCache;
import com.neurozen.marsroverphotos.network.NetworkController;
import com.squareup.picasso.Picasso;

public abstract class SampleAbstractActivity extends AppCompatActivity {

    protected NetworkController networkController;
    protected Gson gson;
    protected Picasso picasso;
    protected AppCache appCache;
    protected SampleApplication application;
    protected SharedPreferences localCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application = (SampleApplication) getApplication();
        networkController = new NetworkController(application);
        gson = application.getGson();
        picasso = application.getPicasso();
        appCache = application.getCache();
        localCache = application.getLocalCache();
    }

    protected final void hideSoftKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    protected final void presentModalDialog(String content, int titleId, String contentType) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(getString(titleId));
        WebView wv = new WebView(this);
        wv.loadData(content, contentType, "UTF-8");
        alert.setView(wv);
        alert.setNegativeButton(getString(R.string.close), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        alert.show();
    }

    protected final boolean isNetworkConnected() {
        return application.isNetworkConnected();
    }

}

