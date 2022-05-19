package com.econnect.client.Companies;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Location;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.econnect.API.CompanyService;
import com.econnect.API.CompanyService.Company;
import com.econnect.API.ElektroGo.CarpoolService;
import com.econnect.API.ElektroGo.CarpoolService.CarpoolPoint;
import com.econnect.API.ServiceFactory;
import com.econnect.Utilities.ExecutionThread;
import com.econnect.Utilities.LocationHelper;
import com.econnect.Utilities.PopupMessage;
import com.econnect.client.ItemDetails.DetailsActivity;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

public class CompanyMapController {

    private final CompanyMapFragment _fragment;
    private final ActivityResultLauncher<Intent> _activityLauncher;
    private final ActivityResultLauncher<String> _requestPermissionLauncher;
    private volatile GoogleMap _googleMap = null;
    private boolean loadCarpool = true;

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

    public void onMapReady(GoogleMap googleMap) {
        _googleMap = googleMap;

        CompanyMapInfoAdapter adapter = new CompanyMapInfoAdapter(_fragment.requireContext());
        _googleMap.setInfoWindowAdapter(adapter);
        _googleMap.setOnInfoWindowClickListener(this::onClickMarker);
    }


    // Company and carpool markers

    void loadMarkers() {
        ExecutionThread.nonUI(()->{
            try {
                Company[] companies = getCompanies();
                // Poll _googleMap until it is initialized by another thread
                while (_googleMap == null);
                ExecutionThread.UI(_fragment, () -> {
                    // Remove all markers
                    _googleMap.clear();
                    // Add new markers
                    for (Company c : companies) {
                        _fragment.addMarker(c);
                    }
                });
            }
            catch (Exception e){
                ExecutionThread.UI(_fragment, ()->{
                    PopupMessage.warning(_fragment, "Could not fetch companies:\n" + e.getMessage());
                });
            }
            try {
                CarpoolPoint[] points = loadCarpool ? getPoints() : new CarpoolPoint[0];
                ExecutionThread.UI(_fragment, () -> {
                    // Add new markers
                    for (CarpoolPoint p : points) {
                        _fragment.addMarker(p);
                    }
                });
            }
            catch (Exception e){
                ExecutionThread.UI(_fragment, ()->{
                    PopupMessage.showToast(_fragment, "Could not fetch carpool points:\n" + e.getMessage());
                });
            }
        });
    }

    private Company[] getCompanies() {
        CompanyService service = ServiceFactory.getInstance().getCompanyService();
        return service.getCompanies();
    }
    private CarpoolPoint[] getPoints() {
        CarpoolService service = new CarpoolService();
        if (!service.pingServer()) {
            ExecutionThread.UI(_fragment, ()->
                    PopupMessage.showToast(_fragment, "Couldn't connect to ElektroGo server, please enable the UPC VPN")
            );
            return new CarpoolPoint[0];
        }

        // For now, get points of all the world
        return service.getPoints(0, 0, 40_000);
    }



    private void onClickMarker(Marker marker) {
        Object tag = marker.getTag();
        assert tag != null;

        if (tag instanceof Company) {
            final Company company = (Company) tag;
            // Launch new activity DetailsActivity
            Intent intent = new Intent(_fragment.getContext(), DetailsActivity.class);

            // Pass parameters to activity
            intent.putExtra("id", company.id);
            intent.putExtra("type", "company");

            _activityLauncher.launch(intent);
        }
        else if (tag instanceof CarpoolPoint) {
            PopupMessage.showToast(_fragment, "Download the ElektroGo app for more info");
        }
        else {
            throw new RuntimeException("Unrecognized tag type");
        }
    }

    private void launchDetailsCallback(ActivityResult result) {
        // Called once the user returns from details screen.
        loadMarkers();
    }


    // Get device location and center map

    @SuppressLint("MissingPermission")
    void centerOnLocation() {
        // Check that we have the required permissions
        if (!LocationHelper.hasLocationPermission(_fragment.requireContext())) {
            _requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
            return;
        }

        _fragment.showLocationLoadIcon(true);
        LocationHelper.getLoc(_fragment, new LocationHelper.ILocationCallback() {
            @Override
            public void success(Location location) {
                _fragment.showLocationLoadIcon(false);

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
                _fragment.showLocationLoadIcon(false);
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
