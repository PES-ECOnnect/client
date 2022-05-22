package com.econnect.client.Profile.Medals;

import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.econnect.API.ProfileService;
import com.econnect.client.R;


public class MedalListAdapter extends BaseAdapter {
    private final Fragment owner;
    private final ProfileService.Medal[] medals;
    private static LayoutInflater _inflater = null;
    private final boolean _gray;

    public MedalListAdapter(Fragment owner, ProfileService.Medal[] medals, boolean gray) {
        this.owner = owner;
        this.medals = medals;
        _gray = gray;
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
        nameMedal.setText(MedalUtils.medalName(m.idmedal));

        // Set item image
        ImageView image = vi.findViewById(R.id.medal_item_image);
        image.setImageDrawable(MedalUtils.medalIcon(m.idmedal));
        if (_gray) {
            ColorMatrix matrix = new ColorMatrix();
            matrix.setSaturation(0);
            image.setColorFilter(new ColorMatrixColorFilter(matrix));
        }

        return vi;
    }
}
