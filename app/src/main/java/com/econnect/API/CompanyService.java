package com.econnect.API;

import android.graphics.Bitmap;

import java.util.TreeMap;

import com.econnect.API.Exceptions.ApiException;

public class CompanyService extends Service {
    
    // Only allow instantiating from ServiceFactory
    CompanyService() {}
    
    public class Company implements IAbstractProduct {
        // Important: The name of these attributes must match the ones in the returned JSON
        private int id;
        private String name;
        private float avgRating;
        private String imageURL;
        private double lat;
        private double lon;
        
        public Company(int id, String name, float avgRating, String imageURL, double lat, double lon) {
            this.id = id;
            this.name = name;
            this.avgRating = avgRating;
            this.imageURL = imageURL;
            this.lat = lat;
            this.lon = lon;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String getSecondaryText() {
            // Display coordinates in format: "12.3456N 34.5678W"
            String latStr, lonStr;
            if (lat >= 0) latStr = String.format("%.04fN", lat);
            else latStr = String.format("%.04fS", -lat);
            if (lon >= 0) lonStr = String.format("%.04fE", lon);
            else lonStr = String.format("%.04fW", -lon);

            return latStr + ", " + lonStr;
        }

        public float getAvgRating() {
            return avgRating;
        }

        @Override
        public Bitmap getImage(int height) {
            return null;
        }

        public int getId() {
            return id;
        }
        public String getImageUrl() {
            return imageURL;
        }
        public double getLat() {
            return lat;
        }
        public double getLon() {
            return lon;
        }
    }
    
    // Get all companies
    public Company[] getCompanies() {
        
        // Call API
        super.needsToken = true;
        JsonResult result = get(ApiConstants.COMPANIES_PATH, null);
        
        // Parse result
        Company[] companies = result.getArray(ApiConstants.RET_RESULT, Company[].class);
        if (companies == null) {
            // This should never happen, the API should always return an array or an error
            throwInvalidResponseError(result, ApiConstants.RET_RESULT);
        }
        
        return companies;
    }
    
    // Get questions for the company type
    public String[] getQuestions() {
        
        // Call API
        super.needsToken = true;
        JsonResult result = get(ApiConstants.COMPANY_QUESTIONS_PATH, null);
        
        // Parse result
        String[] questions = result.getArray(ApiConstants.RET_RESULT, String[].class);
        if (questions == null) {
            // This should never happen, the API should always return an array or an error
            throwInvalidResponseError(result, ApiConstants.RET_RESULT);
        }
        
        return questions;
    }
}
