package com.econnect.API;

import android.graphics.Bitmap;

import com.econnect.API.Exceptions.ApiException;
import com.econnect.Utilities.BitmapLoader;
import com.econnect.API.ProductService.ProductDetails.Question;

public class CompanyService extends Service {
    
    // Only allow instantiating from ServiceFactory
    CompanyService() {}
    
    public static class Company implements IAbstractProduct {
        // Important: The name of these attributes must match the ones in the returned JSON
        // Gson will initialize these fields to the received values
        public final int id;
        public final String name;
        public final float avgrating;
        public final String imageurl;
        public final double lat;
        public final double lon;
        private Bitmap imageBitmap = null;

        public Company(int id, String name, float avgRating, String imageURL, double lat, double lon) {
            this.id = id;
            this.name = name;
            this.avgrating = avgRating;
            this.imageurl = imageURL;
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
            return avgrating;
        }
        @Override
        public Bitmap getImage(int height) {
            if (imageBitmap == null)
                imageBitmap = BitmapLoader.fromURL(imageurl, height);
            return imageBitmap;
        }
    }

    public static class CompanyDetails {
        // Important: The name of these attributes must match the ones in the returned JSON
        // Gson will initialize these fields to the received values
        public final String imageURL;
        public final double latitude;
        public final double longitude;
        public final String name;
        public final Question[] questions;
        public final int[] ratings;

        public CompanyDetails(String imageURL, double latitude, double longitude, String name, Question[] questions, int[] ratings) {
            this.imageURL = imageURL;
            this.latitude = latitude;
            this.longitude = longitude;
            this.name = name;
            this.questions = questions;
            this.ratings = ratings;
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

    // Get company details
    public CompanyDetails getCompanyDetails(int companyId) {
        JsonResult result = null;
        try {
            // Call API
            super.needsToken = true;
            result = get(ApiConstants.COMPANIES_PATH + "/" + companyId, null);
        }
        catch (ApiException e) {
            switch (e.getErrorCode()) {
                case ApiConstants.ERROR_COMPANY_NOT_EXISTS:
                    throw new RuntimeException("The company with id " + companyId + " does not exist");
                default:
                    throw e;
            }
        }

        // Parse result
        CompanyDetails details = result.asObject(CompanyDetails.class);
        if (details == null) {
            // This should never happen, the API should always return an object or an error
            throwInvalidResponseError(result, ApiConstants.RET_RESULT);
        }

        // Trim spaces in questions
        for (int i = 0; i < details.questions.length; i++) {
            ProductService.ProductDetails.Question q = details.questions[i];
            details.questions[i] = new ProductService.ProductDetails.Question(q.num_no, q.num_yes, q.text.trim());
        }

        return details;
    }
}
