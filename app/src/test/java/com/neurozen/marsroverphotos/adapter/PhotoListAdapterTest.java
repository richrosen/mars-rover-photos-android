package com.neurozen.marsroverphotos.adapter;

import android.app.Activity;

import com.neurozen.marsroverphotos.model.Camera;
import com.neurozen.marsroverphotos.model.Photo;
import com.neurozen.marsroverphotos.runner.CustomRobolectricTestRunner;
import com.squareup.picasso.Picasso;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.Robolectric;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(CustomRobolectricTestRunner.class)
public class PhotoListAdapterTest {

    private PhotoListAdapter adapter;

    @Mock
    Picasso mockPicasso;

    List<Photo> photos = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        Activity activity = Robolectric.buildActivity(Activity.class).create().get();
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

        adapter = new PhotoListAdapter(activity, 0, photos, mockPicasso);
    }

    @Test
    public void testGetItem() {
        for (int i = 0; i < photos.size(); i++) {
            Photo photo = photos.get(i);
            assertEquals("\"Photo " + i + "\" was expected", i * 10,
                    ((Photo) adapter.getItem(i)).getSol());
            assertEquals("Wrong URL", photos.get(i).getImgSrc(),
                    ((Photo) adapter.getItem(i)).getImgSrc());
        }
    }

    @Test
    public void testGetItemId() {
        assertEquals("Wrong ID.", 0, adapter.getItemId(0));
    }

    @Test
    public void testGetCount() {
        assertEquals("Photos count should be 10", 10, adapter.getCount());
    }
}