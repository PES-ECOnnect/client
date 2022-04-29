package com.econnect.client.Profile;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.econnect.API.ForumService;
import com.econnect.API.ProfileService;
import com.econnect.Utilities.ExecutionThread;
import com.econnect.client.R;


public class MedalListAdapter extends BaseAdapter {
    private final Fragment owner;
    private final Drawable defaultImage;
    private final ProfileService.User.Medal[] medals;
    private static LayoutInflater _inflater = null;

    public MedalListAdapter(Fragment owner, Drawable defaultImage, ProfileService.User.Medal[] medals) {
        this.owner = owner;
        this.defaultImage = defaultImage;
        this.medals = medals;
        _inflater = (LayoutInflater) owner.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return medals.length;
    }

    @Override
    public Object getItem(int position) {
        return medals[position];
    }

    @Override
    public long getItemId(int position) {
        return medals[position].idmedal;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Initialize view and product
        final ProfileService.User.Medal m = medals[position];
        final View vi;
        if (convertView != null) vi = convertView;
        else vi = _inflater.inflate(R.layout.medal_list_item, null);

        // Set name text
        TextView nameMedal = vi.findViewById(R.id.medalTextName);
        nameMedal.setText(m.medalname);

        // Set item image
        ImageView image = vi.findViewById(R.id.medal_item_image);
        image.setImageDrawable(defaultImage);
        /*ExecutionThread.nonUI(()->{
            // Poll image.getWidth() until the layout has been inflated
            int width = -1;
            while (width == -1) {
                width = image.getWidth();
            }
            Bitmap bmp = m.getImage(width);
            if (bmp == null) return;
            ExecutionThread.UI(owner, ()-> {
                // If the view has changed while we were fetching the image, do nothing
                if (hidden_id.getText().equals(m.idmedal)) {
                    image.setImageBitmap(bmp);
                    image.setVisibility(View.VISIBLE);
                }
            });
        });*/

        return vi;
    }
}
