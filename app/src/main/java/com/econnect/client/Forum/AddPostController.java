package com.econnect.client.Forum;


import android.net.Uri;

import com.econnect.API.ImageUpload.ImageService;
import com.econnect.API.PostService;
import com.econnect.API.ServiceFactory;
import com.econnect.Utilities.ExecutionThread;
import com.econnect.Utilities.FileUtils;
import com.econnect.Utilities.PopupMessage;

import java.io.*;


public class AddPostController {

    private final AddPostFragment _fragment;

    public AddPostController(AddPostFragment fragment) {
        this._fragment = fragment;
    }

    public void addPostOnClick() {
        //GET text and image
        String text_post = _fragment.getText();

        // Local validation
        if (text_post.isEmpty()) {
            PopupMessage.warning(_fragment, "You have to fill the text area");
            return;
        }
        _fragment.enableInput(false);

        // This could take some time (and accesses the internet), run on non-UI thread
        ExecutionThread.nonUI(() -> {
            try {
                attemptPost(text_post, getImageUrl());
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

    private String getImageUrl() {
        Uri image = _fragment.getSelectedImage();
        if (image == null)
            return "";

        File tempFile;
        try {
            tempFile = FileUtils.getFileFromUri(_fragment.requireContext(), image);
        } catch (Exception e) {
            throw new RuntimeException("Could not convert URI to File: " + e.getMessage(), e);
        }

        final ImageService service = new ImageService();
        return service.uploadImageToUrl(tempFile);
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
            _fragment.requireActivity().finish();
        });
    }
}
