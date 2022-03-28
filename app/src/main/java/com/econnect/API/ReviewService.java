package com.econnect.API;

import com.econnect.API.Exceptions.ApiException;
import com.econnect.Utilities.ExecutionThread;

import java.util.TreeMap;

public class ReviewService extends Service{


    ReviewService(){}

    // this works for products and companies
    public void reviewProduct(int productId, int rating) {
        TreeMap<String, String> params = new TreeMap<>();
        // Empty string means all products

        params.put(ApiConstants.REVIEW, String.valueOf(rating));
        super.needsToken = true;



        ExecutionThread.nonUI(() -> {
            JsonResult result = null;
                        try {
                            // Call API
                            result = post(ApiConstants.PRODUCTS_PATH + "/" + productId + "/" + ApiConstants.REVIEW, params, null);
                        } catch (ApiException e) {
                            switch (e.getErrorCode()) {
                                case ApiConstants.ERROR_PRODUCT_NOT_EXISTS:
                                    throw new RuntimeException("The product with id " + productId + " does not exist");
                                default:
                                    throw e;
                            }
                        }


            // Parse result
            /*ProductService.ProductDetails details = result.getObject(ApiConstants.RET_RESULT, ProductService.ProductDetails.class);
            if (details == null) {
                // This should never happen, the API should always return an object or an error
                throwInvalidResponseError(result, ApiConstants.RET_RESULT);
            }*/
        });
    }
}
