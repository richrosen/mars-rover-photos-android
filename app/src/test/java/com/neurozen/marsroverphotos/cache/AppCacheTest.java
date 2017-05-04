package com.neurozen.marsroverphotos.cache;

import com.neurozen.marsroverphotos.model.Camera;
import com.neurozen.marsroverphotos.model.Photo;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AppCacheTest {

    private AppCache appCache;

    @Before
    public void setup() {
        List<Photo> photos = new ArrayList<>();

        Camera camera = new Camera.Builder()
                .withId(1)
                .withRoverId(1)
                .withName("ABC")
                .withFullName("Auxiliary Back Camera")
                .build();

        for (int i = 0; i < 10; i++) {
            photos.add(new Photo.Builder()
                    .withId(i)
                    .withSol(i * 10)
                    .withCamera(camera)
                    .withImgSrc("http://photos.net/fullsize/" + i)
                    .withEarthDate("2017-04-29")
                    .build());
        }

        appCache = new AppCache();
        appCache.addPhotos(photos);
    }

    @Test
    public void testCacheContent() {
        assertEquals("Should be 10 photos", 10, appCache.getPhotos().size());
    }

    @Test
    public void testCacheClear() {
        appCache.clear();
        assertTrue("Should be empty", appCache.getPhotos().isEmpty());
    }
}