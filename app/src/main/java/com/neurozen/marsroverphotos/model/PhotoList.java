package com.neurozen.marsroverphotos.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PhotoList extends BaseModel {

    @SerializedName("photos")
    @Expose
    private List<Photo> photos;

    public List<Photo> getPhotos() {
        return photos;
    }

    public String toString() {
        return photos.toString();
    }
}
