package com.econnect.client.Profile;

import android.app.AlertDialog;
import android.view.View;
import android.widget.ListView;

import androidx.core.content.ContextCompat;

import com.econnect.API.HomeService;
import com.econnect.Utilities.BasicTextWatcher;
import com.econnect.Utilities.CustomFragment;
import com.econnect.client.R;
import com.econnect.client.databinding.FragmentSetHomeBinding;

public class SetHomeFragment extends CustomFragment<FragmentSetHomeBinding> {

    private final SetHomeController _ctrl = new SetHomeController(this);

    public SetHomeFragment() {
        super(FragmentSetHomeBinding.class);
    }

    @Override
    protected void addListeners() {
        binding.checkHomeButton.setEnabled(false);
        binding.saveButton.setEnabled(false);
        binding.streetNameBox.setEnabled(false);
        binding.streetNumBox.setEnabled(false);
        binding.streetNameDropdown.addTextChangedListener(new BasicTextWatcher(()-> {
            boolean streetEmpty = binding.streetNameDropdown.getText().toString().isEmpty();
            boolean numEmpty = binding.streetNameDropdown.getText().toString().isEmpty();
            binding.checkHomeButton.setEnabled(!streetEmpty && !numEmpty);
        }));

        binding.postalCodeNum.addTextChangedListener(new BasicTextWatcher(()->
                _ctrl.zipcodeChanged(binding.postalCodeNum.getText().toString()))
        );

        /*
        street_num.addTextChangedListener(new BasicTextWatcher(()-> {
            building_button.setEnabled(true);
        }));
        change_street.setOnClickListener(view -> {
            _ctrl.getStreets(postal_code.getText().toString());
        });
*/
    }

    void disableInput() {
        binding.postalCodeNumBox.setEnabled(false);
        binding.streetNameBox.setEnabled(false);
        binding.streetNumBox.setEnabled(false);
        binding.checkHomeButton.setEnabled(false);
        binding.homeProgressBar.setVisibility(View.VISIBLE);
    }

    void enableFirstStep(boolean enabled) {
        if (!enabled) {
            // The first step is always available
            return;
        }
        binding.postalCodeNumBox.setEnabled(true);
        binding.homeProgressBar.setVisibility(View.INVISIBLE);
    }

    void enableSecondStep(boolean enabled) {
        enableFirstStep(enabled);

        binding.streetNameBox.setEnabled(enabled);
        binding.streetNumBox.setEnabled(enabled);

        if (!enabled) {
            binding.streetNameDropdown.setText("", false);
        }
    }



/*
    public void createStreetDialog() {

        AlertDialog.Builder streetBuilder = new AlertDialog.Builder(requireContext());

        View streetPopupView = getLayoutInflater().inflate(R.layout.street_list, null);

        ListView sl = streetPopupView.findViewById(R.id.streetList);
        int highlightColor = ContextCompat.getColor(requireContext(), R.color.green);
        StreetListAdapter _streetAdapter = ;
        sl.setAdapter(_streetAdapter);
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this.getActivity(), layout.simple_list_item_1, streets);

        //sl.setAdapter(arrayAdapter);

        streetBuilder.setView(streetPopupView);
        AlertDialog streetlist = streetBuilder.create();
        streetlist.show();

        sl.setOnItemClickListener((parent, view, position, id) -> {
            //street.setText(sl.getItemAtPosition(position).toString());
            streetlist.dismiss();
        });

    }
*/
    public void setStreetsList(String[] streets){
        StreetListAdapter adapter = new StreetListAdapter(this, streets);
        binding.streetNameDropdown.setAdapter(adapter);
    }

    public void selectHomeDialog(HomeService.Homes[] h) {
        String[] homes = transformHomes(h);

        AlertDialog.Builder homeBuilder = new AlertDialog.Builder(requireContext());

        View homePopupView = getLayoutInflater().inflate(R.layout.street_list, null);

        ListView sl = homePopupView.findViewById(R.id.streetList);
        int highlightColor = ContextCompat.getColor(requireContext(), R.color.green);
        //_homeAdapter = new StreetListAdapter(this, highlightColor, homes);
        //sl.setAdapter(_homeAdapter);
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this.getActivity(), layout.simple_list_item_1, streets);

        //sl.setAdapter(arrayAdapter);

        homeBuilder.setView(homePopupView);
        AlertDialog homeslist = homeBuilder.create();
        homeslist.show();

        sl.setOnItemClickListener((parent, view, position, id) -> {
            // Launch new activity DetailsActivity
            HomeService.Homes home = h[position];
            //home_value.setText(sl.getItemAtPosition(position).toString());
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
