package com.econnect.client.Companies;

import androidx.annotation.NonNull;

import com.econnect.API.CompanyService;
import com.econnect.API.ServiceFactory;
import com.econnect.Utilities.ExecutionThread;
import com.econnect.Utilities.PopupMessage;
import com.econnect.client.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Locale;

public class CompanyMapController implements OnMapReadyCallback {

    private final CompanyMapFragment _fragment;
    private volatile GoogleMap _googleMap = null;

    CompanyMapController(CompanyMapFragment fragment) {
        this._fragment = fragment;
    }

    void loadCompanies() {
        ExecutionThread.nonUI(()->{
            try {
                // Get products of all types
                CompanyService service = ServiceFactory.getInstance().getCompanyService();
                CompanyService.Company[] companies = service.getCompanies();
                while (_googleMap == null) {
                    // Do nothing, poll _googleMap until it is initialized by another thread
                }
                for (CompanyService.Company c : companies) {
                    addMarker(c);
                }
            }
            catch (Exception e){
                ExecutionThread.UI(_fragment, ()->{
                    PopupMessage.warning(_fragment, "Could not fetch companies:\n" + e.getMessage());
                });
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        _googleMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(41.387, 2.163);
        _googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in BCN"));
        _googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    private void addMarker(CompanyService.Company company) {
        LatLng coords = new LatLng(company.lat, company.lon);
        MarkerOptions options = new MarkerOptions()
                .position(coords)
                .title(company.name)
                .icon(BitmapDescriptorFactory.fromBitmap(company.getImage(200)))
                .snippet(String.format(Locale.ENGLISH, "Average rating: %.2f", company.avgrating));
        ExecutionThread.UI(_fragment, () -> {
            _googleMap.addMarker(options);
        });
    }
}
