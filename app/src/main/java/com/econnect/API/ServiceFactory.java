package com.econnect.API;

import com.econnect.API.HttpClient.OkHttpAdapter;
import com.econnect.API.HttpClient.StubHttpClient;

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
    
    
    // Login
    private static LoginService loginService = null;
    public LoginService getLoginService() {
        if (loginService == null) {
            loginService = new LoginService();
        }
        return loginService;
    }

    // Register
    private static RegisterService registerService = null;
    public RegisterService getRegisterService() {
        if (registerService == null) {
            registerService = new RegisterService();
        }
        return registerService;
    }

    // Product Types
    private static ProductTypesService _productTypesService = null;
    public ProductTypesService getProductTypesService() {
        if (_productTypesService == null) {
            _productTypesService = new ProductTypesService();
        }
        return _productTypesService;
    }
    
    // Products
    private static ProductService _productService = null;
    public ProductService getProductService() {
        if (_productService == null) {
            _productService = new ProductService();
        }
        return _productService;
    }
    
    // Companies
    private static CompanyService _companyService = null;
    public CompanyService getCompanyService() {
        if (_companyService == null) {
            _companyService = new CompanyService();
        }
        return _companyService;
    }
}
