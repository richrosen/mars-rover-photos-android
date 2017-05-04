package com.neurozen.marsroverphotos.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.neurozen.marsroverphotos.R;
import com.neurozen.marsroverphotos.model.Rover;
import com.neurozen.marsroverphotos.model.manifest.PhotoManifest;
import com.neurozen.marsroverphotos.model.manifest.PhotoMetadata;
import com.neurozen.marsroverphotos.model.manifest.RoverManifest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends SampleAbstractActivity {

    private static final String TAG = "MainActivity";

    private static final String MANIFEST_CACHE_PREFIX = "manifest_";

    private Map<Integer, Integer> photoCountMapBySol = new HashMap<>();

    private int maxSol;
    private int sol;

    @BindView(R.id.choose_rover_spinner)
    Spinner chooseRoverSpinner;

    @BindView(R.id.lower_layout)
    View lowerLayout;

    @BindView(R.id.sols_ago_picker)
    NumberPicker solsAgoPicker;

    @BindView(R.id.num_photos)
    TextView numPhotos;

    @BindView(R.id.view_photos_button)
    Button viewPhotosButton;

    private String roverName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.mipmap.ic_launcher);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_USE_LOGO | ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_SHOW_HOME);

        prepareRoverSpinner();
        prepareSolsAgoNumberPicker();
    }

    private void prepareRoverSpinner() {
        final List<String> list = new ArrayList<String>();
        list.add(getString(R.string.choose_one));
        for (Rover.Vehicles vehicle : Rover.Vehicles.values()) {
            list.add(vehicle.name());
        }
        ArrayAdapter<String> dataAdapter =
                new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item,
                        list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        chooseRoverSpinner.setAdapter(dataAdapter);
        chooseRoverSpinner.setPrompt(getString(R.string.choose_one));
        chooseRoverSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    roverName = list.get(position);
                    getManifest(Rover.Vehicles.valueOf(roverName));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void prepareSolsAgoNumberPicker() {
        solsAgoPicker.setMaxValue(100);
        solsAgoPicker.setMinValue(0);
        solsAgoPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                setPhotoCountForIndex(newVal);
            }
        });
    }

    @OnClick(R.id.view_photos_button)
    void viewPhotos() {
        Intent intent = new Intent();
        intent.setClass(this, PhotoListActivity.class);
        intent.putExtra(PhotoListActivity.EXTRA_ROVER_NAME, roverName);
        intent.putExtra(PhotoListActivity.EXTRA_SOL, sol);
        startActivity(intent);
    }

    protected void getManifest(Rover.Vehicles vehicle) {
        final String key = constructManifestCacheKey(vehicle.name().toLowerCase());
        networkController.getRoverManifest(vehicle, new Callback<RoverManifest>() {
            @Override
            public void onResponse(Call<RoverManifest> call, Response<RoverManifest> response) {
                if (response.code() != 200) {
                    Log.d(TAG, "*** FAIL: " + call.request().url().toString());
                    if (localCache.contains(key)) {
                        PhotoManifest manifest =
                                gson.fromJson(localCache.getString(key, null), PhotoManifest.class);
                        processManifest(manifest);
                        revealSolChooser();
                    } else {
                        processFailedResponse(getString(R.string.cache_failure_rover_manifest_bad_response_code, response.code()));
                    }
                } else {
                    Log.d(TAG, "*** OK: " + call.request().url().toString());
                    PhotoManifest manifest = response.body().getPhotoManifest();
                    processManifest(manifest);
                    revealSolChooser();
                    localCache.edit().putString(key, gson.toJson(manifest)).commit();
                }
            }

            @Override
            public void onFailure(Call<RoverManifest> call, Throwable t) {
                Log.d(TAG, "*** FAIL: " + call.request().url().toString());
                Log.e(TAG, t.getMessage() + " " + t.getCause());
                if (localCache.contains(key)) {
                    PhotoManifest manifest =
                            gson.fromJson(localCache.getString(key, null), PhotoManifest.class);
                    processManifest(manifest);
                    revealSolChooser();
                } else {
                    processFailedResponse(getString(R.string.cache_failure_rover_manifest));
                }
            }
        });
    }

    private void processManifest(PhotoManifest manifest) {
        maxSol = manifest.getMaxSol();
        List<PhotoMetadata> photoMetadataList = manifest.getPhotos();

        photoCountMapBySol.clear();
        for (PhotoMetadata pm : photoMetadataList) {
            photoCountMapBySol.put(pm.getSol(), pm.getTotalPhotos());
        }

        solsAgoPicker.setValue(0);
        setPhotoCountForIndex(0);
    }

    private void revealSolChooser() {
        lowerLayout.setVisibility(View.VISIBLE);
        viewPhotosButton.setVisibility(View.VISIBLE);
    }

    private void setPhotoCountForIndex(int index) {
        sol = maxSol - index;
        StringBuilder sb = new StringBuilder()
                .append("[ ")
                .append(photoCountMapBySol.containsKey(sol) ? photoCountMapBySol.get(sol) : "0")
                .append(" ]");
        numPhotos.setText(sb.toString());
    }

    private void processFailedResponse(String message) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(R.string.error);
        alert.setMessage(getString(R.string.manifest_retrieval_failure) + message);
        alert.setNegativeButton(R.string.close, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                finish();
            }
        });
        hideSoftKeyboard(lowerLayout);
        alert.show();
    }

    private static String constructManifestCacheKey(String roverName) {
        StringBuilder sb = new StringBuilder()
                .append(MANIFEST_CACHE_PREFIX)
                .append(roverName);
        return sb.toString();
    }
}