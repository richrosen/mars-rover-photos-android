package com.neurozen.marsroverphotos.network;

import com.neurozen.marsroverphotos.application.SampleApplication;
import com.neurozen.marsroverphotos.model.PhotoList;
import com.neurozen.marsroverphotos.model.Rover;
import com.neurozen.marsroverphotos.model.manifest.RoverManifest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import retrofit2.Call;
import retrofit2.Callback;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class NetworkControllerTest {

    @Mock
    PhotoApi api;

    @Mock
    SampleApplication application;

    @Mock
    Callback<PhotoList> callbackPhotoList;

    @Mock
    Call<PhotoList> callPhotoList;

    @Mock
    Callback<RoverManifest> callbackRoverManifest;

    @Mock
    Call<RoverManifest> callRoverManifest;

    NetworkController networkController;

    @Before
    public void setup() {
        initMocks(this);
        given(application.getApiKey()).willReturn("apikey");
        given(application.getPhotoApi()).willReturn(api);
        networkController = new NetworkController(application);
    }

    @Test
    public void shouldCallGetPhotosInApi() {
        when(api.getPhotos(anyString(), anyString(), anyString(), anyString())).thenReturn(callPhotoList);

        networkController.getPhotos(Rover.Vehicles.SPIRIT, "100", "1", callbackPhotoList);

        verify(api).getPhotos("spirit", "100", "apikey", "1");
        verify(callPhotoList).enqueue(callbackPhotoList);
    }

    @Test
    public void shouldCallGetRoverManifestInApi() {
        when(api.getRoverManifest(anyString(), anyString())).thenReturn(callRoverManifest);

        networkController.getRoverManifest(Rover.Vehicles.OPPORTUNITY, callbackRoverManifest);

        verify(api).getRoverManifest("opportunity", "apikey");
        verify(callRoverManifest).enqueue(callbackRoverManifest);
    }
}