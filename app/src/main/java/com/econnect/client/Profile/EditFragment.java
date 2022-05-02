package com.econnect.client.Profile;

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
        binding.changesButton.setOnClickListener(view -> changeAttributes());
        binding.changePassword.setOnClickListener(view -> changePassword());
        setDefaultValues();
    }

    private void changePassword() {
        String newPassword = binding.newPasswordText.getText().toString();
        String oldPassword = binding.oldPasswordText.getText().toString();
        if (oldPassword.isEmpty() || newPassword.isEmpty()) {
            PopupMessage.warning(this, "You have to fill old and new passwords");
            return;
        }
        if (newPassword.equals(oldPassword)) {
            PopupMessage.warning(this, "The new password must not be the same as the old one");
            return;
        }
        _ctrl.changePassword(oldPassword, newPassword);
    }

    private void changeAttributes() {
        if (!binding.editUsernameText.getText().toString().equals(_username)) {
            _ctrl.changeUsername(binding.editUsernameText.getText().toString());
        }
        if (!binding.editEmailText.getText().toString().equals(_email)) {
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

    public void clearPasswordFields() {
        binding.oldPasswordText.setText("");
        binding.newPasswordText.setText("");
    }

}
