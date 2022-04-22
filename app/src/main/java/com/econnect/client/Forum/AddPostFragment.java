package com.econnect.client.Forum;

import com.econnect.Utilities.CustomFragment;
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
        binding.addPostButton.setOnClickListener(view -> _ctrl.addPostOnClick());
    }

    void enableInput(boolean enabled) {
        binding.addPostButton.setEnabled(enabled);
    }

    String getText() {
        return binding.textPost.getText().toString();
    }

    String getUrl() {
        return binding.urlPost.getText().toString();
    }
}
