package com.econnect.client.Companies;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.AdapterView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.econnect.API.CompanyService;
import com.econnect.API.ServiceFactory;
import com.econnect.Utilities.ExecutionThread;
import com.econnect.Utilities.PopupMessage;
import com.econnect.client.ItemDetails.DetailsActivity;

public class CompaniesController {
    private final CompaniesFragment _fragment;
    private final ActivityResultLauncher<Intent> _activityLauncher;

    public CompaniesController(CompaniesFragment fragment) {
        this._fragment = fragment;
        _activityLauncher = fragment.registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                this::launchDetailsCallback
        );
    }

    void updateList() {
        _fragment.enableInput(false);
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
                _fragment.enableInput(true);
            });
        }
        catch (Exception e){
            ExecutionThread.UI(_fragment, ()->{
                PopupMessage.warning(_fragment, "Could not fetch companies:\n" + e.getMessage());
            });
        }
    }

    private void launchDetailsCallback(ActivityResult result) {
        // Called once the user returns from details screen
        ExecutionThread.nonUI(this::updateCompaniesList);
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

    AdapterView.OnItemClickListener companyClick() {
        return (parent, view, position, id) -> {
            // Launch new activity DetailsActivity
            Intent intent = new Intent(_fragment.getContext(), DetailsActivity.class);

            CompanyService.Company p = (CompanyService.Company) parent.getItemAtPosition(position);

            // Pass parameters to activity
            intent.putExtra("id", p.id);
            intent.putExtra("type", "company");

            _activityLauncher.launch(intent);
        };
    }
}
