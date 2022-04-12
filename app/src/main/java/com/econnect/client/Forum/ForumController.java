package com.econnect.client.Forum;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;

import com.econnect.API.ForumService;
import com.econnect.API.ServiceFactory;
import com.econnect.Utilities.ExecutionThread;
import com.econnect.Utilities.PopupMessage;
import com.econnect.client.R;

public class ForumController {

    private final ForumFragment _fragment;
    private String _selectedTag = "";

    public ForumController(ForumFragment fragment) {
        this._fragment = fragment;
    }

    View.OnClickListener addPost() { return view -> addPostClick(); }


    private void addPostClick() {
        ExecutionThread.UI(_fragment, ()->{
           // ExecutionThread.navigate(_fragment, );
        });
    }

    void updateLists() {
        ExecutionThread.nonUI(()-> {
            // Populate tag dropdown
            updateTagList();
            // Populate post list
            updatePostsList();
        });
    }

    private void updateTagList() {
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
    }

    private void updatePostsList() {
        try {
            // Get products of all types
            ForumService service = ServiceFactory.getInstance().getForumService();
            ForumService.Post[] posts = service.getPosts(1000, _selectedTag);

            ExecutionThread.UI(_fragment, () -> {
                _fragment.setPostElements(posts);
                _fragment.filterProductList();
                _fragment.enableInput();
            });
        }
        catch (Exception e) {
            ExecutionThread.UI(_fragment, ()->{
                PopupMessage.warning(_fragment, "Could not fetch products:\n" + e.getMessage());
            });
        }
    }



    // Update product list when dropdown or search text change

    AdapterView.OnItemClickListener typesDropdown() {
        return (parent, view, position, id) -> {
            // Update list
            _fragment.filterProductList();
        };
    }

    TextWatcher searchText() {
        return new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {}
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                _fragment.filterProductList();
            }
        };
    }


//    AdapterView.OnItemClickListener productClick() {
//        return (parent, view, position, id) -> {
//            // Launch new activity DetailsActivity
//            Intent intent = new Intent(_fragment.getContext(), DetailsActivity.class);
//
//            ProductService.Product p = (ProductService.Product) parent.getItemAtPosition(position);
//
//            // Pass parameters to activity
//            intent.putExtra("id", p.id);
//            intent.putExtra("type", "product");
//
//            _activityLauncher.launch(intent);
//        };
//    }
}
