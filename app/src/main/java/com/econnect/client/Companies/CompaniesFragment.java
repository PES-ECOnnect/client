package com.econnect.client.Companies;

import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import com.econnect.API.CompanyService.Company;
import com.econnect.Utilities.CustomFragment;
import com.econnect.client.R;
import com.econnect.client.databinding.FragmentCompaniesBinding;

public class CompaniesFragment extends CustomFragment<FragmentCompaniesBinding> {

    private final CompaniesController _ctrl = new CompaniesController(this);
    private CompaniesListAdapter _companiesAdapter;

    public CompaniesFragment() {
        super(FragmentCompaniesBinding.class);
    }

    @Override
    protected void addListeners() {
        binding.searchText.addTextChangedListener(_ctrl.searchText());
        binding.itemList.setOnItemClickListener(_ctrl.companyClick());

        _ctrl.updateList();
    }

    void setCompanyElements(Company[] products) {
        int highlightColor = ContextCompat.getColor(getContext(), R.color.green);
        Drawable defaultImage = ContextCompat.getDrawable(getContext(), R.drawable.ic_companies_24);
        _companiesAdapter = new CompaniesListAdapter(this, highlightColor, defaultImage, products);
        binding.itemList.setAdapter(_companiesAdapter);
    }

    void filterCompaniesList() {
        _companiesAdapter.getFilter().filter(binding.searchText.getText());
    }
}