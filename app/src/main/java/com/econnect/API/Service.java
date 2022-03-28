package com.econnect.API;

import android.os.SystemClock;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import com.google.gson.*;

import com.econnect.API.Exceptions.ApiException;
import com.econnect.API.Exceptions.InvalidTokenApiException;
import com.econnect.API.HttpClient.HttpClient;

public abstract class Service {
    // Reference to the HttpClient used to communicate with the API
    private static HttpClient _httpClient;
    // Store the secret token and insert it into the request headers
    private static String _adminToken = null;
    // Gson object used to serialize and deserialize JSON
    private final Gson gson = new Gson();
    // Called when ERROR_INVALID_TOKEN is received
    private static ITokenInvalidCallback _tokenInvalidCallback;
    // Set by the subclass to indicate whether the request needs an adminToken
    protected boolean needsToken = true;

    public static void injectHttpClient(HttpClient client) {
        _httpClient = client;
    }

    // Instantiate only from ServiceFactory, after setting the HttpClient
    protected Service() {
        if (_httpClient == null) {
            throw new RuntimeException("HttpClient not set");
        }
    }

    // Update the admin token, called from AdminLoginService
    protected static void setToken(String token) {
        if (token == null) throw new IllegalArgumentException("Token cannot be null");
        if (_adminToken != null) throw new IllegalStateException("Token already set");
        _adminToken = token;
    }
    // Invalidate the admin token, called from AdminLogoutService
    protected static void deleteToken() {
        if (_adminToken == null) throw new IllegalStateException("Session token was already deleted");
        _adminToken = null;
    }

    // Generic GET request
    protected JsonResult get(String path, Map<String,String> params) throws ApiException {
        String url = ApiConstants.BASE_URL + path;
        params = addTokenToRequest(params);

        String result = null;
        try {
            result = _httpClient.get(url, params);
        }
        catch (IOException e) {
            throw new RuntimeException("Error executing GET on " + url + ": " + e.getMessage());
        }
        return parseResult(result);
    }

    // Generic POST request
    protected JsonResult post(String path, Map<String,String> params, Object content) throws ApiException {
        String url = ApiConstants.BASE_URL + path;
        addTokenToRequest(params);

        String result = null;
        try {
            result = _httpClient.post(url, params, gson.toJson(content));
        }
        catch (IOException e) {
            throw new RuntimeException("Error executing POST on " + url + ": " + e.getMessage());
        }
        return parseResult(result);
    }


    private Map<String,String> addTokenToRequest(Map<String,String> params) {
        if (!needsToken) return params;
        if (_adminToken == null) {
            throw new IllegalStateException("Admin token not set");
        }
        if (params == null) {
            params = new TreeMap<>();
        }
        params.put(ApiConstants.TOKEN, _adminToken);
        return params;
    }

    private JsonResult parseResult(String result) throws ApiException {
        if (result == null) return null;


        JsonResult json = null;
        try {
            json = new JsonResult(JsonParser.parseString(result));
        }
        catch (JsonSyntaxException | IllegalStateException e) {
            throw new RuntimeException("Invalid JSON response from server:\n" + result);
        }
        String error = json.getAttribute(ApiConstants.RET_ERROR);

        if (error != null) {
            // Special treatment for ERROR_INVALID_TOKEN
            if (error.equals(ApiConstants.ERROR_INVALID_TOKEN)) {
                // Call the invalid token callback
                if (_tokenInvalidCallback != null) {
                    _tokenInvalidCallback.invalidToken();
                }
                throw new InvalidTokenApiException();
            }
            // Generic API error
            throw new ApiException(error);
        }
        return json;
    }

    protected void throwInvalidResponseError(JsonResult result, String expectedAttr) {
        throw new RuntimeException("Invalid response from server: " + result.toString()
                + "\nExpected " + ApiConstants.RET_ERROR + " or attribute '" + expectedAttr + "'");
    }


    // Callback for invalid token errors
    public interface ITokenInvalidCallback {
        void invalidToken();
    }
    public static void setInvalidTokenCallback(ITokenInvalidCallback callback) {
        _tokenInvalidCallback = callback;
    }
}
