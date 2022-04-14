package com.econnect.client.Forum;

import android.graphics.Bitmap;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.AdapterView;

import com.econnect.API.ForumService;
import com.econnect.API.ServiceFactory;
import com.econnect.Utilities.BitmapLoader;
import com.econnect.Utilities.ExecutionThread;
import com.econnect.Utilities.PopupMessage;
import com.econnect.Utilities.ShareManager;
import com.econnect.client.BuildConfig;
import com.econnect.client.R;

public class ForumController {

    private final ForumFragment _fragment;
    private boolean _listContainsAllTags = true;

    public ForumController(ForumFragment fragment) {
        this._fragment = fragment;
    }

    public void updateData() {
        // Populate tag dropdown
        updateTagList();
        // Populate post list (no tag)
        updatePostsList(null);
        _fragment.setTagsDropdownText("");
    }

    private void updateTagList() {
        ExecutionThread.nonUI(()-> {
            try {
                // Get types
                ForumService service = ServiceFactory.getInstance().getForumService();
                ForumService.Tag[] tags = service.getAllTags();

                ExecutionThread.UI(_fragment, () -> {
                    _fragment.setTagsDropdownElements(tags);
                });
            }
            catch (Exception e) {
                ExecutionThread.UI(_fragment, ()->{
                    PopupMessage.warning(_fragment, "Could not fetch tags:\n" + e.getMessage());
                });
            }
        });
    }

    private void updatePostsList(String tag) {
        ExecutionThread.nonUI(()-> {
            // Keep track of whether the list is dirty (skip unnecessary calls to backend)
            _listContainsAllTags = (tag == null);
            try {
                // Get products of all types
                ForumService service = ServiceFactory.getInstance().getForumService();
                ForumService.Post[] posts = service.getPosts(1000, tag);

                ExecutionThread.UI(_fragment, () -> {
                    _fragment.setPostElements(posts);
                    _fragment.enableInput(true);
                });
            } catch (Exception e) {
                ExecutionThread.UI(_fragment, () -> {
                    PopupMessage.warning(_fragment, "Could not fetch posts:\n" + e.getMessage());
                    _fragment.enableInput(true);
                });
            }
        });
    }



    // Update product list when dropdown or search text change

    AdapterView.OnItemClickListener tagsDropdown() {
        return (parent, view, position, id) -> {
            // Update list
            updatePostsList((String) parent.getItemAtPosition(position));
        };
    }

    TextWatcher tagFilterText() {
        return new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {}
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // If the list is dirty and the new text is blank, delete filter
                if (!_listContainsAllTags && s.toString().trim().isEmpty())
                    updatePostsList(null);
            }
        };
    }

    final IPostCallback postCallback = new IPostCallback() {
        @Override
        public void tagClicked(String tag) {
            // Called when a tag from the post body is clicked. Update the search bar and the post list
            _fragment.setTagsDropdownText(tag);
            updatePostsList(tag);
        }

        @Override
        public void share(ForumService.Post post) {
            _fragment.enableInput(false);
            ExecutionThread.nonUI(()->{
                // [username] on ECOnnect: [text]  Check out ECOnnect at ...
                String text = _fragment.getString(R.string.on_econnect, post.username) + "\n" + post.text + "\n\n" +
                        _fragment.getString(R.string.check_out_econnect) +
                        " https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID;
                Bitmap bmp = BitmapLoader.fromURL(post.imageurl);
                if (bmp != null) ShareManager.shareTextAndImage(text, bmp, _fragment.requireContext());
                else ShareManager.shareText(text, _fragment.requireContext());
                ExecutionThread.UI(_fragment, ()-> _fragment.enableInput(true));
            });
        }

        @Override
        public void vote(int id, boolean isLike, boolean remove) {
            String text = (remove?"remove ":"") + (isLike?"like":"dislike") + " of post " + id;
            PopupMessage.warning(_fragment, text);
        }

    };

}
