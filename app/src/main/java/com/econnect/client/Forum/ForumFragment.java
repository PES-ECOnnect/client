package com.econnect.client.Forum;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.core.content.ContextCompat;

import com.econnect.API.ForumService;
import com.econnect.API.ProductService;
import com.econnect.Utilities.CustomFragment;
import com.econnect.client.R;
import com.econnect.client.databinding.FragmentForumBinding;

import java.util.List;

public class ForumFragment extends CustomFragment<FragmentForumBinding> {

    private final ForumController _ctrl = new ForumController(this);
    private PostListAdapter _posts_adapter;

    public ForumFragment() {
        super(FragmentForumBinding.class);
    }

    @Override
    protected void addListeners() {
        // TODO

        _ctrl.updateLists();
    }

    void setTagsDropdownElements(ForumService.Tag[] allTags) {
        TagListAdapter adapter = new TagListAdapter(getContext(), allTags);
        binding.tagDropdown.setAdapter(adapter);
    }

    void setPostElements(ForumService.Post[] posts) {
//        int highlightColor = ContextCompat.getColor(getContext(), R.color.green);
//        Drawable defaultImage = ContextCompat.getDrawable(getContext(), R.drawable.ic_products_24);
//        _posts_adapter = new PostListAdapter(this, highlightColor, defaultImage, posts);
//        binding.itemList.setAdapter(_posts_adapter);
//        binding.itemList.refreshDrawableState();
    }

    void filterProductList() {
//        String type = binding.filterDropdown.getText().toString();
//        if (type.equals(_ctrl.getDefaultType())) type = null;
//
//        _posts_adapter.setFilterType(type);
//        _posts_adapter.getFilter().filter(binding.searchText.getText());
    }

    void enableInput() {
        binding.forumProgressBar.setVisibility(View.GONE);
        binding.tagBox.setEnabled(true);
//        binding.searchBox.setEnabled(true);
    }
}
