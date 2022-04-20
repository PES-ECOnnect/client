package com.econnect.client.Profile;

import com.econnect.Utilities.CustomFragment;
import com.econnect.client.Forum.AddPostController;
import com.econnect.client.databinding.FragmentEditProfileBinding;
import com.econnect.client.databinding.FragmentProfileBinding;
import com.econnect.client.databinding.FragmentRegisterBinding;

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
