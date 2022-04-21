package com.econnect.client.Forum;


import com.econnect.API.PostService;
import com.econnect.API.ServiceFactory;
import com.econnect.Utilities.ExecutionThread;
import com.econnect.Utilities.PopupMessage;
import com.econnect.client.R;


public class AddPostController {

    private final AddPostFragment _fragment;

    public AddPostController(AddPostFragment fragment) {
        this._fragment = fragment;
    }

    public void addPostOnClick() {
        //GET text and image
        String text_post = _fragment.getText();
        String url_post = _fragment.getUrl();

        // Local validation
        if (text_post.isEmpty()) {
            PopupMessage.warning(_fragment, "You have to fill the text area");
            return;
        }
        _fragment.enableInput(false);

        // This could take some time (and accesses the internet), run on non-UI thread
        ExecutionThread.nonUI(() -> {
            try {
                attemptPost(text_post, url_post);
            }
            catch (Exception e) {
                // Return to UI for showing errors
                ExecutionThread.UI(_fragment, ()->{
                    _fragment.enableInput(true);
                    PopupMessage.warning(_fragment, "There has been an error: " + e.getMessage());
                });
            }
        });
    }

    private void attemptPost(String text, String url) {
        // Post
        PostService postService = ServiceFactory.getInstance().getPostService();
        postService.post(text, url);
        // Success
        navigateToForum();
    }

    private void navigateToForum() {
        ExecutionThread.UI(_fragment, ()->{
            _fragment.enableInput(true);
            //no es correcto
            ExecutionThread.navigate(_fragment, R.id.action_successful_login);
        });
    }
}
