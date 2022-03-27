package com.econnect.client.Companies;

import android.text.Editable;
import android.text.TextWatcher;

import com.econnect.API.CompanyService;
import com.econnect.API.ServiceFactory;
import com.econnect.Utilities.ExecutionThread;
import com.econnect.Utilities.PopupMessage;

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
        try {
            // Get products of all types
            CompanyService service = ServiceFactory.getInstance().getCompanyService();
            CompanyService.Company[] companies = service.getCompanies();

            ExecutionThread.UI(_fragment, () -> {
                _fragment.setCompanyElements(companies);
            });
        }
        catch (Exception e){
            ExecutionThread.UI(_fragment, ()->{
                PopupMessage.warning(_fragment, "Could not fetch companies:\n" + e.getMessage());
            });
        }
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
