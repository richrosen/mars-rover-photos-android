package com.neurozen.marsroverphotos.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.neurozen.marsroverphotos.R;
import com.neurozen.marsroverphotos.adapter.PhotoListAdapter;
import com.neurozen.marsroverphotos.model.Photo;
import com.neurozen.marsroverphotos.model.PhotoList;
import com.neurozen.marsroverphotos.model.Rover;
import com.squareup.picasso.NetworkPolicy;

import java.lang.reflect.Type;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhotoListActivity extends SampleAbstractActivity {

    private static final String TAG = "PhotoListActivity";

    public static final String EXTRA_ROVER_NAME = "EXTRA_ROVER_NAME";
    public static final String EXTRA_SOL = "EXTRA_SOL";
    public static final String PHOTO_LIST_CACHE_PREFIX = "photolist_";

    private List<Photo> photos;
    private Rover.Vehicles vehicle;
    private int sol;

    @BindView(R.id.photo_list_refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.photo_list_content)
    ListView photoList;

    @BindView(R.id.full_size_image)
    ImageView fullSizeImage;

    @BindView(R.id.caption)
    TextView caption;

    @BindView(R.id.modal_background)
    View modalBackground;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photo_list);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        sol = bundle.getInt(EXTRA_SOL);
        String roverName = bundle.getString(EXTRA_ROVER_NAME);
        vehicle = Rover.Vehicles.valueOf(roverName.toUpperCase());

        photos = appCache.getPhotos();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.mipmap.ic_launcher);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle(roverName + " (Sol " + sol + ")");

        PhotoListAdapter adapter = new PhotoListAdapter(this, R.layout.list_item, photos, picasso);
        photoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Photo photo = photos.get(i);
                picasso.load(photo.getImgSrc())
                        .networkPolicy(NetworkPolicy.OFFLINE) // photo already downloaded so use cache
                        .placeholder(R.drawable.placeholder)
                        .into(fullSizeImage);
                caption.setText(getCaption(photo));

                modalBackground.setVisibility(View.VISIBLE);
                modalBackground.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        modalBackground.setVisibility(View.GONE);
                    }
                });
            }
        });

        photoList.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData(PhotoListActivity.this.vehicle, PhotoListActivity.this.sol);
            }
        });
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        getData(vehicle, sol);
    }

    private String getCaption(Photo photo) {
        StringBuilder sb = new StringBuilder()
                .append("ID: ")
                .append(photo.getId())
                .append("\n")
                .append(photo.getEarthDate())
                .append(" (Sol ")
                .append(photo.getSol())
                .append(")\nRover: ")
                .append(photo.getRover().getName())
                .append("\n")
                .append(photo.getCamera().getFullName());
        return(sb.toString());
    }

    protected void getData(Rover.Vehicles vehicle, int maxSol) {
        this.vehicle = vehicle;
        this.sol = maxSol;
        final String key = constructPhotoListCacheKey(vehicle, sol);
        networkController.getPhotos(vehicle, Integer.toString(maxSol), "1", new Callback<PhotoList>() {
            @Override
            public void onResponse(Call<PhotoList> call, Response<PhotoList> response) {
                if (response.code() != 200) {
                    Log.d(TAG, "*** FAIL: " + call.request().url().toString());
                    if (localCache.contains(key)) {
                        String json = localCache.getString(key, "");
                        Type listType = new TypeToken<List<Photo>>() {
                        }.getType();
                        photos = gson.fromJson(json, listType);
                    } else {
                        processFailedResponse(getString(R.string.cache_failure_photo_list_bad_response_code, response.code()));
                    }
                } else {
                    Log.d(TAG, "*** OK: " + call.request().url().toString());
                    Log.d(TAG, call.request().url().toString());
                    List<Photo> respPhotos = response.body().getPhotos();
                    appCache.clear();
                    appCache.addPhotos(respPhotos);
                    ((PhotoListAdapter) photoList.getAdapter()).notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);
                    localCache.edit().putString(key, gson.toJson(respPhotos)).commit();
                }
            }

            @Override
            public void onFailure(Call<PhotoList> call, Throwable t) {
                Log.d(TAG, "*** FAIL: " + call.request().url().toString());
                Log.e(TAG, t.getMessage() + " " + t.getCause());
                if (localCache.contains(key)) {
                    String json = localCache.getString(key, "");
                    Type listType = new TypeToken<List<Photo>>() {
                    }.getType();
                    photos = gson.fromJson(json, listType);
                } else {
                    processFailedResponse(getString(R.string.cache_failure_photo_list));
                }
            }
        });
    }

    private String constructPhotoListCacheKey(Rover.Vehicles vehicle, int sol) {
        StringBuilder sb = new StringBuilder()
                .append(PHOTO_LIST_CACHE_PREFIX)
                .append(vehicle.name().toLowerCase())
                .append("_")
                .append(sol);
        return sb.toString();
    }

    private void processFailedResponse(String message) {
        swipeRefreshLayout.setRefreshing(false);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(R.string.error);
        alert.setMessage(getString(R.string.photo_retrieval_failure) + message);
        alert.setNegativeButton(R.string.close, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                finish();
            }
        });
        hideSoftKeyboard(swipeRefreshLayout);
        alert.show();
    }
}
