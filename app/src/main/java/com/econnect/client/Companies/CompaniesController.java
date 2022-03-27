package com.econnect.client.Companies;

import android.text.Editable;
import android.text.TextWatcher;

import com.econnect.API.CompanyService;
import com.econnect.API.ServiceFactory;
import com.econnect.Utilities.ExecutionThread;

public class CompaniesController {
    private final CompaniesFragment _fragment;

    public CompaniesController(CompaniesFragment fragment) {
        this._fragment = fragment;
    }

    void updateList() {
        ExecutionThread.nonUI(()-> {
            // Populate companies list
            updateCompaniesList();
        });
    }

    private void updateCompaniesList() {
        // Get products of all types
        CompanyService service = ServiceFactory.getInstance().getCompanyService();
        CompanyService.Company[] companies = service.getCompanies();

        ExecutionThread.UI(_fragment, ()->{
            _fragment.setCompanyElements(companies);
        });
    }

    TextWatcher searchText() {
        return new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {}
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                _fragment.filterCompaniesList();
            }
        };
    }
}
