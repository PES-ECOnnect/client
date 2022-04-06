package com.econnect.API.HttpClient;

import com.econnect.API.ApiConstants;

import java.util.Map;
import java.util.TreeMap;

public class StubHttpClient implements HttpClient {
    private static final String EXPECTED_DOMAIN = ApiConstants.BASE_URL;
    
    @Override
    public String get(String path, Map<String, String> params) {
        path = checkDomain(path);
        if (params == null) {
            params = new TreeMap<String, String>();
        }
        checkNullParams(params);
        
        switch (path) {
            
            // Login
            case "/account/login":
                expectParamsExclusive(params, "email", "password");
                // For development purposes. Delete this line when done.
                if (equals(params, "email", "a")) {
                    return "{\"token\":\"okToken\"}";
                }
                
                if (equals(params, "email", "okEmail") && equals(params, "password", "okPassword")) {
                    return "{\"token\":\"okToken\"}";
                }
                if (equals(params, "email", "okEmailNoAdmin") && equals(params, "password", "okPassword")) {
                    return "{\"token\":\"okTokenNoAdmin\"}";
                }
                else if (equals(params, "email", "okEmail")) {
                    return "{\"error\":\"ERROR_USER_INCORRECT_PASSWORD\"}";
                }
                else {
                    return "{\"error\":\"ERROR_USER_NOT_FOUND\"}";
                }
            
            // Logout
            case "/account/logout":
                expectParamsExclusive(params, "token");
                if (equals(params, "token", "badToken")) {
                    return "{\"error\":\"ERROR_INVALID_TOKEN\"}";
                }
                else {
                    return "{status: 'success'}";
                }
                
            // Get list of product types
            case "/products/types":
                expectParamsExclusive(params, "token");
                if (equals(params, "token", "badToken")) {
                    return "{\"error\":\"ERROR_INVALID_TOKEN\"}";
                }
                else {
                    // For each type, return the name and an array of questions
                    return "{\"result\":[{\"name\":\"type1\",\"questions\":[\"q1\",\"q2\",\"q3\"]},{\"name\":\"type2\",\"questions\":[\"q4\",\"q5\",\"q6\"]}]}";
                }
                
            // Get list of products
            case "/products":
                expectParamsExclusive(params, "token", "type");
                if (equals(params, "token", "badToken")) {
                    return "{\"error\":\"ERROR_INVALID_TOKEN\"}";
                }
                // For each product of this type, return the id, name, avgRating, imageUrl, manufacturer and type
                if (equals(params, "type", "")) {
                    // For each product, return the id, name, avgRating, imageURL, manufacturer and type
                    return "{\"result\":[{\"id\":1,\"name\":\"product1\",\"avgrating\":1.0,\"imageurl\":\"https://miro.medium.com/max/500/0*-ouKIOsDCzVCTjK-.png\",\"manufacturer\":\"manufacturer1\",\"type\":\"type1\"},{\"id\":2,\"name\":\"product2\",\"avgrating\":2.0,\"imageurl\":\"https://wallpapercave.com/wp/wp4676582.jpg\",\"manufacturer\":\"manufacturer2\",\"type\":\"type1\"},{\"id\":3,\"name\":\"product3\",\"avgRating\":3.0,\"imageurl\":\"imageUrl3\",\"manufacturer\":\"manufacturer3\",\"type\":\"type2\"},{\"id\":4,\"name\":\"product4\",\"avgRating\":4.0,\"imageurl\":\"imageUrl4\",\"manufacturer\":\"manufacturer4\",\"type\":\"type2\"}, {\"id\":5,\"name\":\"during\",\"avgRating\":1.0,\"imageurl\":\"imageUrl1\",\"manufacturer\":\"manufacturer1\",\"type\":\"type1\"}]}";
                }
                else {
                    return "{\"error\":\"ERROR_TYPE_NOT_EXISTS\"}";
                }

            // Get detailed info about product
            case "/products/0":
                expectParamsExclusive(params, "token");
                if (equals(params, "token", "badToken")) {
                    return "{\"error\":\"ERROR_INVALID_TOKEN\"}";
                }
                return "{\"name\":\"product0\",\"imageURL\":\"imageUrl0\",\"manufacturer\":\"manufacturer0\",\"type\":\"type0\",\"questions\":[{\"text\":\"q0\",\"num_yes\":11,\"num_no\":12},{\"text\":\"q1\",\"num_yes\":21,\"num_no\":22},{\"text\":\"q2\",\"num_yes\":31,\"num_no\":32}],\"ratings\":[1,0,0,0,0,10]}";
                
            case "/products/2":
                expectParams(params, "token");
                return "{\"error\":\"ERROR_INCORRECT_ID_REVIEWABLE\"}";
                
            // Get list of companies
            case "/companies":
                expectParams(params, "token");
                if (equals(params, "token", "badToken")) {
                    return "{\"error\":\"ERROR_INVALID_TOKEN\"}";
                }
                else {
                    // For each company, return the id, name, avgRating, imageURL, lat and lon
                    return "{\"result\":[{\"id\":1,\"name\":\"company1\",\"avgrating\":1.0,\"imageurl\":\"https://wallpapercave.com/wp/wp4676582.jpg\",\"lat\":1.0,\"lon\":1.0},{\"id\":2,\"name\":\"company2\",\"avgrating\":2.0,\"imageurl\":\"http://www.company2.com/image.png\",\"lat\":2.0,\"lon\":2.0}]}";
                }

            // Get detailed info about company
            case "/companies/1":
                expectParams(params, "token");
                if (equals(params, "token", "badToken")) {
                    return "{\"error\":\"ERROR_INVALID_TOKEN\"}";
                }
                return "{\"imageURL\":\"https://company.com/img.png\",\"latitude\":12,\"longitude\":34,\"name\":\"test\",\"questions\":[{\"num_no\":1,\"num_yes\":2,\"text\":\"bon servei?\"},{\"num_no\":3,\"num_yes\":4,\"text\":\"q2\"}],\"ratings\":[1,2,3,4,5,6],\"type\":\"Company\"}";
                
            case "/companies/2":
                expectParams(params, "token");
                return "{\"error\":\"ERROR_INCORRECT_ID_REVIEWABLE\"}";
                
            default:
                throw new RuntimeException("Invalid path: " + path);
        }
    }

    @Override
    public String post(String path, Map<String, String> params, String json) {
        path = checkDomain(path);
        if (params == null) {
            params = new TreeMap<String, String>();
        }
        checkNullParams(params);
        
        switch (path) {
            // Create a new account
            case "/account":
                expectParamsExclusive(params, "username", "email", "password");
                if (equals(params, "username", "existingName")) {
                    return "{\"error\":\"ERROR_USERNAME_EXISTS\"}";
                }
                else if (equals(params, "email", "existingEmail")) {
                    return "{\"error\":\"ERROR_EMAIL_EXISTS\"}";
                }
                else {
                    return "{\"token\":\"okToken\"}";
                }
            
            default:
                throw new RuntimeException("Invalid path: " + path);
        }
        
    }
    
    
    // CHECKS
    
    // Throw an exception if the path doesn't start with the expected domain
    private String checkDomain(String path) {
        if (!path.startsWith(EXPECTED_DOMAIN)) {
            throw new IllegalArgumentException("Path must start with " + EXPECTED_DOMAIN);
        }
        return path.substring(EXPECTED_DOMAIN.length());
    }
    
    // Throw an exception if any of the params is null
    private void checkNullParams(Map<String, String> params) {
        for (String param : params.keySet()) {
            if (params.get(param) == null) {
                throw new IllegalArgumentException("Parameter " + param + " cannot be null");
            }
        }
    }
    
    // Throw an exception if params doesn't contain all of the expected params
    private void expectParams(Map<String, String> params, String... expected) {
        for (String param : expected) {
            if (!params.containsKey(param)) {
                throw new IllegalArgumentException("Missing parameter: " + param);
            }
        }
    }
    
    // Throw an exception if params does't contain EXACTLY all of the expected params
    private void expectParamsExclusive(Map<String, String> params, String... expected) {
        expectParams(params, expected);
        if (params.size() != expected.length) {
            throw new IllegalArgumentException("Too many parameters passed. Got: " + params.keySet() + ", expected: " + expected);
        }
    }
    
    // Check whether a parameter has a certain value
    boolean equals(Map<String, String> params, String param, String value) {
        if (!params.containsKey(param)) {
            throw new IllegalArgumentException("Missing parameter: " + param);
        }
        return params.get(param).equals(value);
    }
}
