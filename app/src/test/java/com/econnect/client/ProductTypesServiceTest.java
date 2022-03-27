package com.econnect.client;

import org.junit.*;

import com.econnect.API.*;
import com.econnect.API.HttpClient.StubHttpClient;

import static org.junit.Assert.*;

public class ProductTypesServiceTest {
    ProductTypesService sv;
   
    @Before
    public void setUp() {
        sv = ServiceFactory.getInstance().getProductTypesService();
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
    public void testGetProductTypesOk() {
        ProductTypesService.ProductType[] types = sv.getProductTypes();
        // This should not throw an exception
        assertNotNull(types);
        assert(types.length == 2);
        
        assertEquals("type1", types[0].getName());
        assertEquals("type2", types[1].getName());
        
        assertEquals("q1", types[0].getQuestions()[0]);
        assertEquals("q2", types[0].getQuestions()[1]);
        assertEquals("q6", types[1].getQuestions()[2]);
    }
    
    @Test
    public void cannotGetProductsWithoutToken() {
        ServiceTestHelper.clearToken();
        expectException(()->
            sv.getProductTypes(),
            "Admin token not set"
        );
    }
    
    @Test
    public void cannotGetProductsWithWrongToken() {
        ServiceTestHelper.setToken("badToken");
        expectException(()->
            sv.getProductTypes(),
            // This error is not very friendly, but it should never happen
            "The server responded with error code ERROR_INVALID_TOKEN"
        );
    }

}
