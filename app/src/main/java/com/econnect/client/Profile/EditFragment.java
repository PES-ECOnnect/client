package com.econnect.client.Profile;

import com.econnect.Utilities.CustomFragment;
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
        binding.changesButton.setOnClickListener(View -> _ctrl.changeAtributes());
        setDefaultValues();
    }

    public void setDefaultValues() {
        binding.editUsernameText.setText(_username);
        binding.editEmailText.setText(_email);
        binding.switchPrivate.setChecked(_isPrivate);
    }

}
