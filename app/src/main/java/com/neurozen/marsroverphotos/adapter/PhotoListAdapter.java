package com.neurozen.marsroverphotos.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.neurozen.marsroverphotos.R;
import com.neurozen.marsroverphotos.model.Photo;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PhotoListAdapter extends ArrayAdapter {

    private static final String TAG = "PhotoListAdapter";

    private List<Photo> photos;
    private Picasso picasso;

    public PhotoListAdapter(Context context, int resource, List<Photo> photos, Picasso picasso) {
        super(context, resource, photos);
        this.picasso = picasso;
        this.photos = photos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        if (rowView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            rowView = inflater.inflate(R.layout.list_item, null);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.thumbnail = (ImageView) rowView.findViewById(R.id.thumbnail);
            viewHolder.caption = (TextView) rowView.findViewById(R.id.caption);
            rowView.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder) rowView.getTag();
        Photo photo = photos.get(position);
        picasso.load(photo.getImgSrc())
                .placeholder(R.drawable.placeholder)
                .resize(500, 500)
                .onlyScaleDown()
                .into(holder.thumbnail);
        holder.caption.setText(getListItemCaption(photo));

        return rowView;
    }

    private String getListItemCaption(Photo photo) {
        StringBuilder sb = new StringBuilder()
                .append(photo.getEarthDate())
                .append(" (")
                .append(photo.getCamera().getName())
                .append(")");
        return sb.toString();
    }

    @Override
    public int getCount() {
        return photos.size();
    }

    static class ViewHolder {
        ImageView thumbnail;
        TextView caption;
    }
}
