package com.econnect.client.Forum;

import androidx.core.content.ContextCompat;

import com.econnect.API.ForumService;
import com.econnect.Utilities.CustomFragment;
import com.econnect.client.R;
import com.econnect.client.databinding.FragmentForumBinding;

public class ForumFragment extends CustomFragment<FragmentForumBinding> {

    private final ForumController _ctrl = new ForumController(this);

    public ForumFragment() {
        super(FragmentForumBinding.class);
    }

    @Override
    protected void addListeners() {
        binding.tagDropdown.setOnItemClickListener(_ctrl.tagsDropdown());
        binding.tagDropdown.addTextChangedListener(_ctrl.tagFilterText());
        binding.pullToRefresh.setOnRefreshListener(_ctrl::updateData);
        binding.addPostButton.setOnClickListener(_ctrl.addPostOnClick());

        _ctrl.updateData();
    }

    void setTagsDropdownElements(ForumService.Tag[] allTags) {
        TagListAdapter adapter = new TagListAdapter(requireContext(), allTags);
        binding.tagDropdown.setAdapter(adapter);
    }

    void setTagsDropdownText(String text) {
        binding.tagDropdown.setText(text);
    }

    void setPostElements(ForumService.Post[] posts) {
        int highlightColor = ContextCompat.getColor(requireContext(), R.color.green);
        PostListAdapter _posts_adapter = new PostListAdapter(this, _ctrl.postCallback, highlightColor, posts);
        binding.postList.setAdapter(_posts_adapter);
        binding.postList.refreshDrawableState();
    }

    void enableInput(boolean enabled) {
        binding.pullToRefresh.setRefreshing(!enabled);
        binding.postList.setEnabled(enabled);
        binding.tagBox.setEnabled(enabled);
    }

}
