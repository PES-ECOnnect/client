package com.econnect.API;

import android.graphics.Bitmap;

import java.util.TreeMap;

import com.econnect.API.Exceptions.ApiException;
import com.econnect.Utilities.BitmapLoader;

public class ProductService extends Service {
    
    // Only allow instantiating from ServiceFactory
    ProductService() {}
    
    public static class Product implements IAbstractProduct {
        // Important: The name of these attributes must match the ones in the returned JSON
        private final int id;
        private final String name;
        private final float avgRating;
        private final String manufacturer;
        private final String imageURL;
        private final String type;
        private Bitmap imageBitmap = null;
        
        public Product(int id, String name, float avgRating, String manufacturer, String imageURL, String type) {
            this.id = id;
            this.name = name;
            this.avgRating = avgRating;
            this.manufacturer = manufacturer;
            this.imageURL = imageURL;
            this.type = type;
        }

        @Override
        public String getName() {
            return name;
        }
        @Override
        public String getSecondaryText() {
            return manufacturer;
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

        public int getId() {
            return id;
        }
        public String getManufacturer() {
            return manufacturer;
        }
        public String getImageUrl() {
            return imageURL;
        }
        public String getType() {
            return type;
        }
    }


    public class ProductDetails {
        public class Question {
            private final int num_no;
            private final int num_yes;
            private final String text;

            public Question(String text, int num_no, int num_yes) {
                this.text = text;
                this.num_no = num_no;
                this.num_yes = num_yes;
            }
            public String getText() { return text; }
            public int numNo() { return num_no; }
            public int numYes() { return num_yes; }
        }

        // Important: The name of these attributes must match the ones in the returned JSON
        private final String imageURL;
        private final String manufacturer;
        private final String name;
        private final Question[] questions;
        private final Integer[] ratings;

        public ProductDetails(String imageURL, String manufacturer, String name, Question[] questions, Integer[] ratings) {
            this.imageURL = imageURL;
            this.manufacturer = manufacturer;
            this.name = name;
            this.questions = questions;
            this.ratings = ratings;
        }
        
        public String getImageURL() {
            return imageURL;
        }
        public String getManufacturer() {
            return manufacturer;
        }
        public String getName() {
            return name;
        }
        public Question[] getQuestions() {
            return questions;
        }
        public Integer[] getRatings() {
            return ratings;
        }
    }



    // Get product of specific type (or all products if type is null)
    public Product[] getProducts(String type) {
        // Add parameters
        TreeMap<String, String> params = new TreeMap<>();
        // Empty string means all products
        if (type == null) type = "";
        params.put(ApiConstants.PRODUCT_TYPE, type);
        
        
        JsonResult result = null;
        try {
            // Call API
            super.needsToken = true;
            result = get(ApiConstants.PRODUCTS_PATH, params);
        }
        catch (ApiException e) {
            switch (e.getErrorCode()) {
                case ApiConstants.ERROR_TYPE_NOT_EXISTS:
                    throw new RuntimeException("The product type " + type + " does not exist");
                default:
                    throw e;
            }
        }
        
        // Parse result
        Product[] products = result.getArray(ApiConstants.RET_RESULT, Product[].class);
        if (products == null) {
            // This should never happen, the API should always return an array or an error
            throwInvalidResponseError(result, ApiConstants.RET_RESULT);
        }
        
        return products;
    }
    
    // Get product details
    public ProductDetails getProductDetails(int productId) {
        JsonResult result = null;
        try {
            // Call API
            super.needsToken = true;
            result = get(ApiConstants.PRODUCTS_PATH + "/" + productId, null);
        }
        catch (ApiException e) {
            switch (e.getErrorCode()) {
                case ApiConstants.ERROR_PRODUCT_NOT_EXISTS:
                    throw new RuntimeException("The product with id " + productId + " does not exist");
                default:
                    throw e;
            }
        }
        
        // Parse result
        ProductDetails details = result.asObject(ProductDetails.class);
        if (details == null) {
            // This should never happen, the API should always return an object or an error
            throwInvalidResponseError(result, ApiConstants.RET_RESULT);
        }
        
        return details;
    }
}
