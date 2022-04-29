package com.econnect.client.Profile;

import android.view.View;

import com.econnect.Utilities.CustomFragment;
import com.econnect.Utilities.PopupMessage;
import com.econnect.client.databinding.FragmentEditProfileBinding;

public class EditFragment extends CustomFragment<FragmentEditProfileBinding> {

    private final EditProfileController _ctrl = new EditProfileController(this);
    private final String _username;
    private final String _email;
    private final Boolean _isPrivate;


    public EditFragment(String username, String email, Boolean isPrivate) {
        super(FragmentEditProfileBinding.class);
        this._username = username;
        this._email = email;
        this._isPrivate = isPrivate;
    }

    @Override
    protected void addListeners() {
        binding.changesButton.setOnClickListener(View -> changeAttributes());
        binding.changePassword.setOnClickListener(View -> changePassword());
        setDefaultValues();
    }

    private void changePassword() {
        if (!(binding.newPasswordText.getText().toString().isEmpty()) && !(binding.oldPasswordText.getText().toString().isEmpty())) {
            _ctrl.changePassword(binding.oldPasswordText.getText().toString(), binding.newPasswordText.getText().toString());
        }
        else {
            PopupMessage.warning(this, "You have to fill old and new passwords");
        }
    }

    private void changeAttributes() {
        if (!binding.editUsernameText.getText().equals(_username)) {
            _ctrl.changeUsername(binding.editUsernameText.getText().toString());
        }
        if (!binding.editEmailText.getText().equals(_email)) {
            _ctrl.changeEmail(binding.editEmailText.getText().toString());
        }
        if (binding.switchPrivate.isChecked() != _isPrivate) {
            _ctrl.changeIsPrivate(binding.switchPrivate.isChecked());
        }
    }

    public void setDefaultValues() {
        binding.editUsernameText.setText(_username);
        binding.editEmailText.setText(_email);
        binding.switchPrivate.setChecked(_isPrivate);
    }

}
