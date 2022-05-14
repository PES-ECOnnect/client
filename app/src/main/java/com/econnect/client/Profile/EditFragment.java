package com.econnect.client.Profile;

import android.text.Editable;
import android.text.NoCopySpan;
import android.text.TextWatcher;

import com.econnect.Utilities.CustomFragment;
import com.econnect.Utilities.ExecutionThread;
import com.econnect.Utilities.PopupMessage;
import com.econnect.client.databinding.FragmentEditProfileBinding;

public class EditFragment extends CustomFragment<FragmentEditProfileBinding> {

    private final EditProfileController _ctrl = new EditProfileController(this);
    private String _username;
    private String _email;
    private String _about;
    private final Boolean _isPrivate;


    public EditFragment(String username, String email, String about,Boolean isPrivate) {
        super(FragmentEditProfileBinding.class);
        this._username = username;
        this._email = email;
        this._about = about;
        this._isPrivate = isPrivate;
    }

    @Override
    protected void addListeners() {
        binding.changeNameButton.setOnClickListener(view ->
            _ctrl.changeUsername(binding.editUsernameText.getText().toString())
        );
        binding.changeEmailButton.setOnClickListener(view ->
            _ctrl.changeEmail(binding.editEmailText.getText().toString())
        );
        binding.changeAboutButton.setOnClickListener(view ->
            _ctrl.changeAbout(binding.editAboutText.getText().toString())
        );
        binding.switchPrivate.setOnClickListener(view ->
            _ctrl.changeIsPrivate(binding.switchPrivate.isChecked())
        );

        binding.editUsernameText.addTextChangedListener(new AccountTextWatcher(()->{
            boolean sameText = binding.editUsernameText.getText().toString().equals(_username);
            binding.changeNameButton.setEnabled(!sameText);
        }));
        binding.editEmailText.addTextChangedListener(new AccountTextWatcher(()->{
            boolean sameText = binding.editEmailText.getText().toString().equals(_email);
            binding.changeEmailButton.setEnabled(!sameText);
        }));
        binding.editAboutText.addTextChangedListener(new AccountTextWatcher(()->{
            boolean sameText = binding.editAboutText.getText().toString().equals(_about);
            binding.changeAboutButton.setEnabled(!sameText);
        }));

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

    void updateUsername(String newUsername) {
        _username = newUsername;
        ExecutionThread.UI(this, ()->binding.changeNameButton.setEnabled(false));

    }
    void updateEmail(String newEmail) {
        _email = newEmail;
        ExecutionThread.UI(this, ()->binding.changeEmailButton.setEnabled(false));
    }

    void updateAbout(String newAbout) {
        _about = newAbout;
        ExecutionThread.UI(this, ()->binding.changeAboutButton.setEnabled(false));
    }

    public void setDefaultValues() {
        binding.editUsernameText.setText(_username);
        binding.editEmailText.setText(_email);
        binding.editAboutText.setText(_about);
        binding.switchPrivate.setChecked(_isPrivate);
    }

    public void clearPasswordFields() {
        binding.oldPasswordText.setText("");
        binding.newPasswordText.setText("");
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


}
