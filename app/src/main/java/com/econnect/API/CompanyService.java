package com.econnect.API;

import android.graphics.Bitmap;

import com.econnect.Utilities.BitmapLoader;

public class CompanyService extends Service {
    
    // Only allow instantiating from ServiceFactory
    CompanyService() {}
    
    public static class Company implements IAbstractProduct {
        // Important: The name of these attributes must match the ones in the returned JSON
        // Gson will initialize these fields to the received values
        public final int id;
        public final String name;
        public final float avgRating;
        public final String imageURL;
        public final double lat;
        public final double lon;
        private Bitmap imageBitmap = null;

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
        @Override
        public float getAvgRating() {
            return avgRating;
        }
        @Override
        public Bitmap getImage(int height) {
            if (imageBitmap == null)
                imageBitmap = BitmapLoader.fromURL(imageURL, height);
            return imageBitmap;
        }
    }

    public static class CompanyDetails {
        public static class Question {
            public final int num_no = 0;
            public final int num_yes = 0;
            public final String text = null;
        }

        // Important: The name of these attributes must match the ones in the returned JSON
        // Gson will initialize these fields to the received values
        public final String imageURL = null;
        public final double latitude = 0;
        public final double longitude = 0;
        public final String name = null;
        public final Question[] questions = null;
        public final Integer[] ratings = null;
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
