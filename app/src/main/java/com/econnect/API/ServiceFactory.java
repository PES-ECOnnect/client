package com.econnect.API;

import com.econnect.API.HttpClient.OkHttpAdapter;

public class ServiceFactory {
    // Singleton
    private static ServiceFactory instance = null;
    private ServiceFactory() {}
    public static ServiceFactory getInstance() {
        if (instance == null) {
            instance = new ServiceFactory();
            // Use OkHttp library
            Service.injectHttpClient(new OkHttpAdapter());
            
            // TODO: Remove this once the backend works
            //Service.injectHttpClient(new StubHttpClient());
        }
        return instance;
    }
    
    
    // Admin Login
    private static LoginService loginService = null;
    public LoginService getLoginService() {
        if (loginService == null) {
            loginService = new LoginService();
        }
        return loginService;
    }

}
