package com.econnect.client.Companies;

import android.annotation.SuppressLint;
import android.view.View;

import com.econnect.Utilities.CustomFragment;
import com.econnect.client.R;
import com.econnect.client.databinding.FragmentCompaniesMapBinding;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

public class CompanyMapFragment extends CustomFragment<FragmentCompaniesMapBinding> {

    private final CompanyMapController _ctrl = new CompanyMapController(this);


    public CompanyMapFragment() {
        super(FragmentCompaniesMapBinding.class);
    }

    @Override
    protected void addListeners() {
        binding.centerMap.setOnClickListener(view -> _ctrl.centerOnLocation());

        SupportMapFragment mapFragment = binding.map.getFragment();
        mapFragment.getMapAsync(_ctrl);

        _ctrl.loadCompanies();
    }


    void enableLocationLoading(boolean loading) {
        if (loading) {
            binding.centerMap.setImageDrawable(null);
            binding.centerMapProgress.setVisibility(View.VISIBLE);
        }
        else {
            binding.centerMap.setImageResource(R.drawable.ic_location_24);
            binding.centerMapProgress.setVisibility(View.INVISIBLE);
        }
    }

    @SuppressLint("MissingPermission")
    void enableBlueDot(GoogleMap map) {
        SupportMapFragment mapFragment = binding.map.getFragment();
        final int defaultMyLocationButtonId = 0x2;
        try {
            map.setMyLocationEnabled(true);
            View locationButton = mapFragment.requireView().findViewById(defaultMyLocationButtonId);
            locationButton.setVisibility(View.GONE);
        }
        catch (Exception e) {
            // Could not remove default button, do nothing
        }
    }
}
