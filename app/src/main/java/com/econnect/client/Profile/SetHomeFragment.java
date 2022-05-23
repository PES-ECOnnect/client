package com.econnect.client.Profile;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.econnect.Utilities.CustomFragment;
import com.econnect.Utilities.ExecutionThread;
import com.econnect.Utilities.PopupMessage;
import com.econnect.client.R;
import com.econnect.client.databinding.FragmentEditProfileBinding;

public class SetHomeFragment extends CustomFragment<FragmentEditProfileBinding> {

    EditText postal_code, street, street_num, escala, floor, door;
    Button change_street, verify, cancel, save;
    private final SetHomeController _ctrl = new SetHomeController(this);



    public SetHomeFragment() {
        super(FragmentEditProfileBinding.class);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = this.getLayoutInflater().inflate(R.layout.fragment_set_home, null);

        postal_code = v.findViewById(R.id.postal_code_num);
        street = v.findViewById(R.id.street_text);
        street_num = v.findViewById(R.id.num_street_value);
        escala = v.findViewById(R.id.escala_value);
        floor = v.findViewById(R.id.floor_num);
        door = v.findViewById(R.id.door_value);

        change_street = v.findViewById(R.id.change_street_button);
        verify = v.findViewById(R.id.check_home_button);
        cancel = v.findViewById(R.id.cancel_button);
        save = v.findViewById(R.id.save_button);

        street.setFocusable(false);
        street_num.setFocusable(false);
        escala.setFocusable(false);
        floor.setFocusable(false);
        door.setFocusable(false);

        change_street.setEnabled(false);
        verify.setEnabled(false);
        save.setEnabled(false);
    }

    @Override
    protected void addListeners() {
        postal_code.addTextChangedListener(new AccountTextWatcher(()->{
            if (postal_code.getText().toString().length() == 5){
                change_street.setEnabled(true);
            }
        }));
        street.addTextChangedListener(new AccountTextWatcher(()-> {
            street_num.setFocusable(true);
        }));
        street_num.addTextChangedListener(new AccountTextWatcher(()-> {
            escala.setFocusable(true);
            floor.setFocusable(true);
            door.setFocusable(true);
            verify.setEnabled(true);
        }));


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

        final View streetPopupView = getLayoutInflater().inflate(R.layout.delete_account, null);


        streetBuilder.setView(streetPopupView);
        AlertDialog streetlist = streetBuilder.create();
        streetlist.show();

    }
}
