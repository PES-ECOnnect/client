package com.econnect.API;

import android.graphics.Bitmap;

import com.econnect.API.Exceptions.ApiException;
import com.econnect.Utilities.BitmapLoader;

import java.util.Locale;
import java.util.TreeMap;

public class ForumService extends Service {
    // Only allow instantiating from ServiceFactory
    ForumService() {}

    public static class Post {
        public static final int OPT_LIKE = 2;
        public static final int OPT_DISLIKE = 1;
        public static final int OPT_NONE = 0;

        // Important: The name of these attributes must match the ones in the returned JSON
        public final int postid;
        public final String username;
        public final int userid;
        public final String medal;
        public final String text;
        public final String imageurl;
        public int likes;
        public int dislikes;
        public int useroption;
        public final float timestamp;
        public final boolean ownpost;

        private Bitmap imageBitmap = null;
        
        public Post(int postId, String username, int userId, String medal, String text, String imageURL, int likes, int dislikes, int userOption, float timestamp, boolean ownpost) {
            this.postid = postId;
            this.username = username;
            this.userid = userId;
            this.medal = medal;
            this.text = text;
            this.imageurl = imageURL;
            this.likes = likes;
            this.dislikes = dislikes;
            this.useroption = userOption;
            this.timestamp = timestamp;
            this.ownpost = ownpost;
        }

        public Bitmap getImage(int width) {
            if (imageBitmap == null)
                imageBitmap = BitmapLoader.fromURLResizeWidth(imageurl, width);
            return imageBitmap;
        }
    }

    public static class Tag {
        // Important: The name of these attributes must match the ones in the returned JSON
        public final String tag;
        public final int count;

        public Tag(String tag, int count) {
            this.tag = tag;
            this.count = count;
        }
    }
    
    // Get all posts
    public Post[] getPosts(int numPosts, String tag) {
        // Add parameters
        TreeMap<String, String> params = new TreeMap<>();
        params.put(ApiConstants.POST_AMOUNT, Integer.toString(numPosts));
        // No tag means all posts
        if (tag != null) params.put(ApiConstants.POST_TAG, tag);
        
        JsonResult result = null;
        try {
            // Call API
            super.needsToken = true;
            result = get(ApiConstants.POSTS_PATH, params);
        }
        catch (ApiException e) {
            switch (e.getErrorCode()) {
                // This endpoint does not throw any errors
                default:
                    throw e;
            }
        }
        
        // Parse result
        Post[] posts = result.getArray(ApiConstants.RET_RESULT, Post[].class);
        if (posts == null) {
            // This should never happen, the API should always return an array or an error
            throwInvalidResponseError(result, ApiConstants.RET_RESULT);
        }
        
        return posts;
    }
    
    // Delete a post
    public void deletePost(int postId) {
        
        JsonResult result = null;
        try {
            // Call API
            super.needsToken = true;
            result = delete(ApiConstants.POSTS_PATH + "/" + postId, null);
        }
        catch (ApiException e) {
            switch (e.getErrorCode()) {
                case ApiConstants.ERROR_POST_NOT_EXISTS:
                    throw new RuntimeException("The post with id " + postId + " does not exist");
                case ApiConstants.ERROR_USER_NOT_POST_OWNER:
                    throw new RuntimeException("You don't have permission to delete this post");
                default:
                    throw e;
            }
        }
        
        // Parse result
        String status = result.getAttribute(ApiConstants.RET_STATUS);
        if (status == null || !status.equals(ApiConstants.STATUS_OK)) {
            // This should never happen, the API should always return an array or an error
            throwInvalidResponseError(result, ApiConstants.RET_STATUS);
        }
    }

    public Tag[] getAllTags() {

        JsonResult result = null;
        try {
            // Call API
            super.needsToken = true;
            result = get(ApiConstants.TAGS_PATH, null);
        }
        catch (ApiException e) {
            switch (e.getErrorCode()) {
                // This endpoint does not throw any errors
                default:
                    throw e;
            }
        }

        // Parse result
        Tag[] tags = result.getArray(ApiConstants.RET_RESULT, Tag[].class);
        if (tags == null) {
            // This should never happen, the API should always return an array or an error
            throwInvalidResponseError(result, ApiConstants.RET_RESULT);
        }

        return tags;
    }

    public void likePost(int id, boolean isLike, boolean remove) {
        TreeMap<String, String> params = new TreeMap<>();
        params.put(ApiConstants.POST_IS_LIKE, Boolean.toString(isLike));
        params.put(ApiConstants.POST_REMOVE, Boolean.toString(remove));

        JsonResult result = null;
        try {
            // Call API
            super.needsToken = true;
            result = post(String.format(ApiConstants.POST_LIKE_PATH, id), params, null);
        }
        catch (ApiException e) {
            switch (e.getErrorCode()) {
                case ApiConstants.ERROR_POST_NOT_EXISTS:
                    throw new RuntimeException("The post with id " + id + " does not exist");
                default:
                    throw e;
            }
        }

        // Parse result
        String status = result.getAttribute(ApiConstants.RET_STATUS);
        if (status == null || !status.equals(ApiConstants.STATUS_OK)) {
            // This should never happen, the API should always return an array or an error
            throwInvalidResponseError(result, ApiConstants.RET_STATUS);
        }
    }
}
