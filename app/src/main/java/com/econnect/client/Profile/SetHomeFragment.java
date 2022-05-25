package com.econnect.client.Profile;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.R.layout;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.econnect.API.HomeService;
import com.econnect.API.IAbstractProduct;
import com.econnect.API.ProductService;
import com.econnect.Utilities.CustomFragment;
import com.econnect.Utilities.ExecutionThread;
import com.econnect.Utilities.PopupMessage;
import com.econnect.client.Companies.CompaniesListAdapter;
import com.econnect.client.ItemDetails.DetailsActivity;
import com.econnect.client.R;
import com.econnect.client.databinding.FragmentEditProfileBinding;
import com.econnect.client.databinding.FragmentSetHomeBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SetHomeFragment extends CustomFragment<FragmentSetHomeBinding> {

    EditText postal_code, street, street_num, home_value;
    Button change_street, building_button, cancel, save;
    String[] streets;
    HomeService.Homes home;

    private final SetHomeController _ctrl = new SetHomeController(this);
    private StreetListAdapter _streetAdapter, _homeAdapter;


    public SetHomeFragment() {
        super(FragmentSetHomeBinding.class);

    }


    @Override
    protected void addListeners() {
        View v = this.getView();

        postal_code = v.findViewById(R.id.postal_code_num);
        street = v.findViewById(R.id.street_text);
        street_num = v.findViewById(R.id.num_street_value);
        home_value = v.findViewById(R.id.home_value);

        change_street = v.findViewById(R.id.change_street_button);
        building_button = v.findViewById(R.id.check_home_button);
        cancel = v.findViewById(R.id.cancel_button);
        save = v.findViewById(R.id.save_button);

        street.setFocusable(false);

        home_value.setFocusable(false);

        change_street.setEnabled(false);
        building_button.setEnabled(false);
        save.setEnabled(false);

        postal_code.addTextChangedListener(new AccountTextWatcher(()->{
            if (postal_code.getText().toString().length() == 5){
                change_street.setEnabled(true);
            }
        }));
        street.addTextChangedListener(new AccountTextWatcher(()-> {
            street_num.setFocusable(true);
            street_num.setEnabled(true);
        }));
        street_num.addTextChangedListener(new AccountTextWatcher(()-> {
            building_button.setEnabled(true);
        }));
        change_street.setOnClickListener(view -> {
            _ctrl.getStreets(postal_code.getText().toString());
            street_num.setFocusable(true);
        });
        building_button.setOnClickListener(view -> {
            _ctrl.getBuilding(postal_code.getText().toString(), street.getText().toString(), street_num.getText().toString());
            save.setEnabled(true);
        });
        save.setOnClickListener(view -> {
            _ctrl.setHome(postal_code.getText().toString(), street.getText().toString(), street_num.getText().toString(), home.escala, home.pis, home.porta );
        });



    }

    private static class AccountTextWatcher implements TextWatcher {
        private final Runnable _runnable;
        public AccountTextWatcher(Runnable runnable) {
            _runnable = runnable;
        }
        public void beforeTextChanged(CharSequence var1, int var2, int var3, int var4) {
            // Do nothing
        }
        public void onTextChanged(CharSequence var1, int var2, int var3, int var4) {
            // Call runnable
            _runnable.run();
        }
        public void afterTextChanged(Editable var1) {
            // Do nothing
        }
    }

    public void createStreetDialog() {


        AlertDialog.Builder streetBuilder = new AlertDialog.Builder(requireContext());

        View streetPopupView = getLayoutInflater().inflate(R.layout.street_list, null);

        ListView sl = streetPopupView.findViewById(R.id.streetList);
        int highlightColor = ContextCompat.getColor(requireContext(), R.color.green);
        _streetAdapter = new StreetListAdapter(this, highlightColor, streets);
        sl.setAdapter(_streetAdapter);
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this.getActivity(), layout.simple_list_item_1, streets);

        //sl.setAdapter(arrayAdapter);

        streetBuilder.setView(streetPopupView);
        AlertDialog streetlist = streetBuilder.create();
        streetlist.show();

        sl.setOnItemClickListener((parent, view, position, id) -> {
            // Launch new activity DetailsActivity
            street.setText(sl.getItemAtPosition(position).toString());
            streetlist.dismiss();
        });

    }

    public void setStreetsList(String[] s){
        this.streets = s;
    }

    public void selectHomeDialog(HomeService.Homes[] h) {
        String[] homes = transformHomes(h);

        AlertDialog.Builder homeBuilder = new AlertDialog.Builder(requireContext());

        View homePopupView = getLayoutInflater().inflate(R.layout.street_list, null);

        ListView sl = homePopupView.findViewById(R.id.streetList);
        int highlightColor = ContextCompat.getColor(requireContext(), R.color.green);
        _homeAdapter = new StreetListAdapter(this, highlightColor, homes);
        sl.setAdapter(_homeAdapter);
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this.getActivity(), layout.simple_list_item_1, streets);

        //sl.setAdapter(arrayAdapter);

        homeBuilder.setView(homePopupView);
        AlertDialog homeslist = homeBuilder.create();
        homeslist.show();

        sl.setOnItemClickListener((parent, view, position, id) -> {
            // Launch new activity DetailsActivity
            home = h[position];
            home_value.setText(sl.getItemAtPosition(position).toString());
            homeslist.dismiss();
        });

    }

    public String[] transformHomes(HomeService.Homes[] h){
        int n = h.length;
        String[] res = new String[n];
        for(int i = 0; i < n; ++i){
            res[i] = h[i].numero + " " + h[i].escala + " " + h[i].pis + " " + h[i].porta;
        }
        return res;
    }
}
