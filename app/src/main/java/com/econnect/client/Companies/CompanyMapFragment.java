package com.econnect.client.Companies;

import com.econnect.Utilities.CustomFragment;
import com.econnect.client.databinding.FragmentCompaniesMapBinding;
import com.google.android.gms.maps.SupportMapFragment;

public class CompanyMapFragment extends CustomFragment<FragmentCompaniesMapBinding> {

    private final CompanyMapController _ctrl = new CompanyMapController(this);


    public CompanyMapFragment() {
        super(FragmentCompaniesMapBinding.class);
    }

    @Override
    protected void addListeners() {
        SupportMapFragment mapFragment = binding.map.getFragment();
        mapFragment.getMapAsync(_ctrl);

        _ctrl.loadCompanies();
    }

}
