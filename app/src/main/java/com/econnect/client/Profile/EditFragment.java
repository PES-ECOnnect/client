package com.econnect.client.Profile;

import com.econnect.Utilities.CustomFragment;
import com.econnect.client.databinding.FragmentEditProfileBinding;
import com.econnect.client.databinding.FragmentProfileBinding;
import com.econnect.client.databinding.FragmentRegisterBinding;

public class EditFragment extends CustomFragment<FragmentEditProfileBinding> {

    private final EditProfileController ctrl = new EditProfileController(this);

    public EditFragment() {
        super(FragmentEditProfileBinding.class);
    }

    @Override
    protected void addListeners() {

    }

}
