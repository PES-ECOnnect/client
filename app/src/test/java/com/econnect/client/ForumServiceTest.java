package com.econnect.client;

import org.junit.*;

import com.econnect.API.*;
import com.econnect.API.ForumService.Post;
import com.econnect.API.ForumService.Tag;
import com.econnect.API.HttpClient.StubHttpClient;

import static org.junit.Assert.*;

public class ForumServiceTest {
    private ForumService sv;
    
    @Before
    public void setUp() {
        sv = ServiceFactory.getInstance().getForumService();
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
    
    @Test
    public void testGetPostsOk() {
        Post[] posts = sv.getPosts(3, "");
        assertNotNull(posts);
        assertEquals(2, posts.length);
        
        assertEquals(1, posts[0].postid);
        assertEquals("user1", posts[0].username);
        assertEquals(1, posts[0].userid);
        assertEquals("m1", posts[0].medal);
        assertEquals("text1", posts[0].text);
        assertEquals("image1", posts[0].imageurl);
        assertEquals(1, posts[0].likes);
        assertEquals(2, posts[0].dislikes);
        assertEquals(1, posts[0].useroption);
        assertEquals(1649663866, posts[0].timestamp);
        
        assertEquals(2, posts[1].postid);
        assertEquals("user2", posts[1].username);
        assertEquals(2, posts[1].userid);
        assertEquals("m2", posts[1].medal);
        assertEquals("text2", posts[1].text);
        assertEquals("image2", posts[1].imageurl);
        assertEquals(3, posts[1].likes);
        assertEquals(4, posts[1].dislikes);
        assertEquals(0, posts[1].useroption);
        assertEquals(1649663810, posts[1].timestamp);
    }
    
    @Test
    public void testGetPostsWithTag() {
        Post[] posts = sv.getPosts(3, "tag1");
        assertNotNull(posts);
        assertEquals(1, posts.length);
        
        assertEquals(1, posts[0].postid);
        assertEquals("user1", posts[0].username);
        assertEquals(1, posts[0].userid);
        assertEquals("m1", posts[0].medal);
        assertEquals("text1", posts[0].text);
        assertEquals("image1", posts[0].imageurl);
        assertEquals(1, posts[0].likes);
        assertEquals(2, posts[0].dislikes);
        assertEquals(1, posts[0].useroption);
        assertEquals(1649663866, posts[0].timestamp);
    }
    
    @Test
    public void testGetPostsInvalidAmount() {
        expectException(() -> 
            sv.getPosts(-1, ""),
            "The server responded with error code invalid value of n"
        );
        expectException(() ->
            sv.getPosts(0, ""),
            "The server responded with error code invalid value of n"
        );
    }
    
    @Test
    public void testGetPostsTagNotExists() {
        Post[] posts = sv.getPosts(3, "badTag");
        assertNotNull(posts);
        assertEquals(0, posts.length);
    }
    
    @Test
    public void cannotGetPostsWithoutToken() {
        ServiceTestHelper.clearToken();
        expectException(()->
            sv.getPosts(3, ""),
            "User token not set"
        );
    }
    
    @Test
    public void cannotGetPostsWithWrongToken() {
        ServiceTestHelper.setToken("badToken");
        expectException(()->
            sv.getPosts(3, ""),
            "This session has expired, please logout and try again"
        );
    }
    
    
    @Test
    public void testGetTagsOk() {
        Tag[] tags = sv.getAllTags();
        assertNotNull(tags);
        assertEquals(3, tags.length);
        
        assertEquals("tag1", tags[0].tag);
        assertEquals(15, tags[0].count);
        
        assertEquals("tag2", tags[1].tag);
        assertEquals(2, tags[1].count);
        
        assertEquals("abc", tags[2].tag);
        assertEquals(1, tags[2].count);
    }
    
    @Test
    public void testGetTagsWithoutToken() {
        ServiceTestHelper.clearToken();
        expectException(()->
            sv.getAllTags(),
            "User token not set"
        );
    }
    
    @Test
    public void testGetTagsWithWrongToken() {
        ServiceTestHelper.setToken("badToken");
        expectException(()->
            sv.getAllTags(),
            "This session has expired, please logout and try again"
        );
    }
    
}
