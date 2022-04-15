package com.econnect.client.Forum;

import com.econnect.API.ForumService;

public interface IPostCallback {
    void tagClicked(String tag);
    void share(ForumService.Post post);
    void vote(int id, boolean isLike, boolean remove);
}
