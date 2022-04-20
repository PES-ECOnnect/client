package com.econnect.client.Forum;

import com.econnect.Utilities.CustomFragment;
import com.econnect.client.ItemDetails.IDetailsController;
import com.econnect.client.databinding.FragmentAddPostBinding;


public class AddPostFragment extends CustomFragment<FragmentAddPostBinding>  {

    private AddPostController _ctrl;

    public void setController(AddPostController ctrl) {
        this._ctrl = ctrl;
    }

    public AddPostFragment() {
        super(FragmentAddPostBinding.class);
    }

    @Override
    protected void addListeners() {

    }
}
