package com.econnect.client.Profile.Medals;

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
    private final ProfileService.Medal[] medals;
    private static LayoutInflater _inflater = null;

    public MedalListAdapter(Fragment owner, Drawable defaultImage, ProfileService.Medal[] medals) {
        this.owner = owner;
        this.defaultImage = defaultImage;
        this.medals = medals;
        _inflater = (LayoutInflater) owner.requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        final ProfileService.Medal m = medals[position];
        final View vi;
        if (convertView != null) vi = convertView;
        else vi = _inflater.inflate(R.layout.medal_list_item, null);

        // Set name text
        TextView nameMedal = vi.findViewById(R.id.medalTextName);
        nameMedal.setText(MedalUtils.medalName(owner, m.idmedal));

        // Set item image
        ImageView image = vi.findViewById(R.id.medal_item_image);
        image.setImageDrawable(MedalUtils.medalIcon(owner, m.idmedal));

        return vi;
    }
}
