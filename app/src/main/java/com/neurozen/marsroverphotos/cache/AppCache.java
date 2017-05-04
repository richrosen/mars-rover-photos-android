package com.neurozen.marsroverphotos.cache;

import com.neurozen.marsroverphotos.model.Photo;

import java.util.ArrayList;
import java.util.List;

public class AppCache {

    List<Photo> photos;

    public AppCache() {
        photos = new ArrayList<>();
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void clear() {
        photos.clear();
    }

    public void addPhotos(List<Photo> morePhotos) {
        photos.addAll(morePhotos);
    }
}
