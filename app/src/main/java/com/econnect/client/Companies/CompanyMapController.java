package com.econnect.client.Companies;

import static com.google.android.gms.location.LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.provider.Settings;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.econnect.API.CompanyService;
import com.econnect.API.CompanyService.Company;
import com.econnect.API.ServiceFactory;
import com.econnect.Utilities.ExecutionThread;
import com.econnect.Utilities.LocationHelper;
import com.econnect.Utilities.PopupMessage;
import com.econnect.client.ItemDetails.DetailsActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;
import com.google.android.gms.tasks.Task;

public class CompanyMapController implements OnMapReadyCallback {

    private final CompanyMapFragment _fragment;
    private final ActivityResultLauncher<Intent> _activityLauncher;
    private final ActivityResultLauncher<String> _requestPermissionLauncher;
    private volatile GoogleMap _googleMap = null;

    // Initialization

    CompanyMapController(CompanyMapFragment fragment) {
        this._fragment = fragment;
        _activityLauncher = fragment.registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                this::launchDetailsCallback
        );
        _requestPermissionLauncher = fragment.registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                this::onRequestPermissionResult
        );
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        _googleMap = googleMap;

        CompanyMapInfoAdapter adapter = new CompanyMapInfoAdapter(_fragment.requireContext());
        _googleMap.setInfoWindowAdapter(adapter);
        _googleMap.setOnInfoWindowClickListener(this::onClickMarker);
    }


    // Company markers

    void loadCompanies() {
        ExecutionThread.nonUI(()->{
            try {
                // Get products of all types
                CompanyService service = ServiceFactory.getInstance().getCompanyService();
                Company[] companies = service.getCompanies();
                while (_googleMap == null) {
                    // Do nothing, poll _googleMap until it is initialized by another thread
                }
                ExecutionThread.UI(_fragment, () -> {
                    // Remove all markers
                    _googleMap.clear();
                    // Add new markers
                    for (Company c : companies) {
                        addMarker(c);
                    }
                });
            }
            catch (Exception e){
                ExecutionThread.UI(_fragment, ()->{
                    PopupMessage.warning(_fragment, "Could not fetch companies:\n" + e.getMessage());
                });
            }
        });
    }
    private void addMarker(Company company) {
        LatLng coords = new LatLng(company.lat, company.lon);
        MarkerOptions options = new MarkerOptions().position(coords);
        // Add marker to map
        Marker m = _googleMap.addMarker(options);
        assert m != null;
        m.setTag(company);
        // Create a task for loading images in the background
        ExecutionThread.nonUI(()-> company.getImage(64));
    }
    private void onClickMarker(Marker marker) {
        final Company company = (Company) marker.getTag();
        assert company != null;

        // Launch new activity DetailsActivity
        Intent intent = new Intent(_fragment.getContext(), DetailsActivity.class);

        // Pass parameters to activity
        intent.putExtra("id", company.id);
        intent.putExtra("type", "company");

        _activityLauncher.launch(intent);
    }

    private void launchDetailsCallback(ActivityResult result) {
        // Called once the user returns from details screen.
        loadCompanies();
    }


    // Get device location and center map

    @SuppressLint("MissingPermission")
    void centerOnLocation() {
        // Check that we have the required permissions
        if (!LocationHelper.hasLocationPermission(_fragment.requireContext())) {
            _requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
            return;
        }

        _fragment.enableLocationLoading(true);
        LocationHelper.getLoc(_fragment, new LocationHelper.ILocationCallback() {
            @Override
            public void success(Location location) {
                _fragment.enableLocationLoading(false);

                // Success: move camera and enable blue dot
                _fragment.enableBlueDot(_googleMap);
                LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());

                final CameraUpdate update;
                // Increment zoom only if we are zoomed out
                if (_googleMap.getCameraPosition().zoom < 10) {
                    update = CameraUpdateFactory.newLatLngZoom(loc, 15);
                } else {
                    update = CameraUpdateFactory.newLatLng(loc);
                }
                _googleMap.animateCamera(update);
            }

            @Override
            public void error(String error) {
                _fragment.enableLocationLoading(false);
                PopupMessage.showToast(_fragment, error);
            }
        });
    }

    private void onRequestPermissionResult(boolean isGranted) {
        if (isGranted) {
            // If we got the permission, try again.
            centerOnLocation();
        }
        else PopupMessage.showToast(_fragment, "Could not get location permission");
    }
}
