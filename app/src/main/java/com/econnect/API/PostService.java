package com.econnect.API;

import com.econnect.API.Exceptions.ApiException;


import java.util.TreeMap;

public class PostService extends Service{

    // Only allow instantiating from ServiceFactory
    PostService() {}

    public void post(String text, String url) {

        // Add parameters
        TreeMap<String, String> params = new TreeMap<>();
        params.put(ApiConstants.POST_TEXT, text);
        params.put(ApiConstants.POST_IMAGE, url);

        // Call API
        super.needsToken = true;
        JsonResult result;

        try {
            result = post(ApiConstants.POSTS_PATH, params, null);
        }
        catch (ApiException e) {
            switch (e.getErrorCode()) {
                case ApiConstants.ERROR_INCORRECT_INSERTION:
                    throw new RuntimeException("Insertion error");
                default:
                    throw e;
            }
        }
    }
}
