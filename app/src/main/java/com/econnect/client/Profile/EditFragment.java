package com.econnect.client.Profile;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Switch;

import com.econnect.Utilities.CustomFragment;
import com.econnect.client.R;
import com.econnect.client.databinding.FragmentEditProfileBinding;
import com.google.android.material.textfield.TextInputEditText;

public class EditFragment extends CustomFragment<FragmentEditProfileBinding> {

    private EditProfileController _ctrl;
    private String username;
    private String email;
    private Boolean isPrivate;
    private TextInputEditText username_form, email_form;
    private Switch isPrivate_form;


    public EditFragment(String username, String email, Boolean isPrivate) {
        super(FragmentEditProfileBinding.class);
        this.username = username;
        this.email = email;
        this.isPrivate = isPrivate;
    }

    public void setController(EditProfileController ctrl) {
        this._ctrl = ctrl;
    }

    @Override
    protected void addListeners() {
        binding.changesButton.setOnClickListener(View -> _ctrl.changeAtributes());
        setDefaultValors();
    }

    public void setDefaultValors() {
        View v = this.getView();
        username_form = v.findViewById(R.id.editUsernameText);
        username_form.setText(username);
        email_form = v.findViewById(R.id.editEmailText);
        email_form.setText(email);
        isPrivate_form = v.findViewById(R.id.switchPrivate);
        isPrivate_form.setChecked(isPrivate);
    }

}
