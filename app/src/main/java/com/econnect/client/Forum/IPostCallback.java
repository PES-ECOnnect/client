package com.econnect.client.Forum;

import com.econnect.API.ForumService;

public interface IPostCallback {
    void tagClicked(String tag);
    void share(ForumService.Post post);
    void like(int index);
    void dislike(int index);
}
