package com.econnect.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.econnect.API.ForumService;
import com.econnect.API.ForumService.Post;
import com.econnect.API.ForumService.Tag;
import com.econnect.API.HttpClient.StubHttpClient;
import com.econnect.API.ImageUpload.ImageService;
import com.econnect.API.ProfileService;
import com.econnect.API.ServiceFactory;

import org.junit.Before;
import org.junit.Test;

public class ProfileServiceTest {
    private ProfileService sv;
    
    @Before
    public void setUp() {
        sv = ServiceFactory.getInstance().getProfileService();
        ServiceTestHelper.injectHttpClient(new StubHttpClient());
        ServiceTestHelper.setToken();
    }
    
    private void expectException(Runnable r, String expectedMessage) {
        try {
            r.run();
            fail("Should have thrown an exception with message: " + expectedMessage);
        }
        catch (Exception e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }
}
