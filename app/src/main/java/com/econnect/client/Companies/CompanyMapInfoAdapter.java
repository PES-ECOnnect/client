package com.econnect.client.Companies;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;

import com.econnect.API.CompanyService.Company;
import com.econnect.Utilities.ExecutionThread;
import com.econnect.client.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class CompanyMapInfoAdapter implements GoogleMap.InfoWindowAdapter {

    private final LayoutInflater _inflater;

    public CompanyMapInfoAdapter(Context context) {
        _inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Nullable
    @Override
    public View getInfoContents(@NonNull Marker marker) {
        // Customize the contents of the map popup

        // Get view
        final View v = _inflater.inflate(R.layout.map_popup_item, null);

        final Company company = (Company) marker.getTag();
        assert company != null;

        TextView companyName = v.findViewById(R.id.popup_companyName);
        companyName.setText(company.name);

        // Set item image
        if (company.hasImage()) {
            ImageView image = v.findViewById(R.id.popup_image);
            image.setImageBitmap(company.getImage(-1));
        }

        // Set average rating
        setStars(v, company.getAvgRating());

        // Returning the view containing InfoWindow contents
        return v;
    }

    private void setStars(View v, float average) {
        final Drawable fullStar = AppCompatResources.getDrawable(v.getContext(), R.drawable.ic_star_24);
        final Drawable halfStar = AppCompatResources.getDrawable(v.getContext(), R.drawable.ic_star_half_24);
        final Drawable emptyStar = AppCompatResources.getDrawable(v.getContext(), R.drawable.ic_star_empty_24);
        final Drawable[] starDrawables = new Drawable[]{emptyStar, halfStar, fullStar};

        int[] stars = new int[5]; // 0=empty, 1=half, 2=full
        for (int i = 0; i < 5; i++) {
            if (average >= i + 1) {
                stars[i] = 2;
            } else if (average >= i + 0.5) {
                stars[i] = 1;
            } else {
                stars[i] = 0;
            }
        }
        ((ImageView) v.findViewById(R.id.popup_star1)).setImageDrawable(starDrawables[stars[0]]);
        ((ImageView) v.findViewById(R.id.popup_star2)).setImageDrawable(starDrawables[stars[1]]);
        ((ImageView) v.findViewById(R.id.popup_star3)).setImageDrawable(starDrawables[stars[2]]);
        ((ImageView) v.findViewById(R.id.popup_star4)).setImageDrawable(starDrawables[stars[3]]);
        ((ImageView) v.findViewById(R.id.popup_star5)).setImageDrawable(starDrawables[stars[4]]);
    }

    @Nullable
    @Override
    public View getInfoWindow(@NonNull Marker marker) {
        // Use default frame
        return null;
    }
}
