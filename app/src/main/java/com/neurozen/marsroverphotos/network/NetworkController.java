package com.neurozen.marsroverphotos.network;

import com.neurozen.marsroverphotos.application.SampleApplication;
import com.neurozen.marsroverphotos.model.PhotoList;
import com.neurozen.marsroverphotos.model.Rover;
import com.neurozen.marsroverphotos.model.manifest.RoverManifest;

import retrofit2.Call;
import retrofit2.Callback;

public class NetworkController {

    private PhotoApi api;
    private SampleApplication application;

    public NetworkController(SampleApplication application) {
        this.application = application;
        this.api = application.getPhotoApi();
    }

    public void getPhotos(Rover.Vehicles rover, String dateParam, String pageNumber, Callback<PhotoList> callback) {
        Call<PhotoList> call = api.getPhotos(rover.name().toLowerCase(), dateParam, application.getApiKey(), pageNumber);
        call.enqueue(callback);
    }

    public void getRoverManifest(Rover.Vehicles rover, Callback<RoverManifest> callback) {
        Call<RoverManifest> call = api.getRoverManifest(rover.name().toLowerCase(), application.getApiKey());
        call.enqueue(callback);
    }

}
