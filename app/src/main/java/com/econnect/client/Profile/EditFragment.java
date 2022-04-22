package com.econnect.client.Profile;

import com.econnect.Utilities.CustomFragment;
import com.econnect.client.databinding.FragmentEditProfileBinding;

public class EditFragment extends CustomFragment<FragmentEditProfileBinding> {

    private EditProfileController _ctrl;

    public EditFragment() {
        super(FragmentEditProfileBinding.class);
    }

    public void setController(EditProfileController ctrl) {
        this._ctrl = ctrl;
    }

    @Override
    protected void addListeners() {

    }

}
