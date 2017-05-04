package com.neurozen.marsroverphotos.network;

import com.neurozen.marsroverphotos.model.PhotoList;
import com.neurozen.marsroverphotos.model.manifest.RoverManifest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PhotoApi {

    @GET("/mars-photos/api/v1/rovers/{rover}/photos")
    Call<PhotoList> getPhotos(@Path("rover") String rover,
                              @Query("sol") String sol,
                              @Query("api_key") String apiKey,
                              @Query("page") String pageNumber);

    @GET("/mars-photos/api/v1/manifests/{rover}")
    Call<RoverManifest> getRoverManifest(@Path("rover") String rover,
                                         @Query("api_key") String apiKey);
}
