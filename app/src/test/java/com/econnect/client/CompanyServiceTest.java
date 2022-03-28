package com.econnect.client;

import org.junit.*;

import com.econnect.API.*;
import com.econnect.API.CompanyService.Company;
import com.econnect.API.HttpClient.StubHttpClient;

import static org.junit.Assert.*;

public class CompanyServiceTest {
    private CompanyService sv;
    
    @Before
    public void setUp() {
        sv = ServiceFactory.getInstance().getCompanyService();
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
    public void testGetCompanyOk() {
        Company[] companies = sv.getCompanies();
        // This should not throw an exception
        
        assertNotNull(companies);
        assertEquals(2, companies.length);
        
        assertEquals(1, companies[0].id);
        assertEquals("company1", companies[0].getName());
        assertEquals(1.0f, companies[0].getAvgRating(), 0.0f);
        assertEquals("https://wallpapercave.com/wp/wp4676582.jpg", companies[0].imageURL);
        assertEquals(1.0, companies[0].lat, 0.0);
        assertEquals(1.0, companies[0].lon, 0.0);
        
        assertEquals(2, companies[1].id);
        assertEquals("company2", companies[1].getName());
        assertEquals(2.0f, companies[1].getAvgRating(), 0.0f);
        assertEquals("http://www.company2.com/image.png", companies[1].imageURL);
        assertEquals(2.0, companies[1].lat, 0.0);
        assertEquals(2.0, companies[1].lon, 0.0);
    }
    
    @Test
    public void cannotGetCompaniesWithInvalidToken() {
        ServiceTestHelper.setToken("badToken");
        expectException(() ->
            sv.getCompanies(),
            "This session has expired, please logout and try again"
        );
    }
    
    @Test
    public void cannotGetCompaniesWithoutToken() {
        ServiceTestHelper.clearToken();
        expectException(() ->
            sv.getCompanies(),
            "Admin token not set"
        );
    }

}
