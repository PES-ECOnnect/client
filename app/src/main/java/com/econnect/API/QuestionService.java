package com.econnect.API;

import com.econnect.API.Exceptions.ApiException;

import java.util.TreeMap;

public class QuestionService extends Service{

    QuestionService(){}

    // this works for products and companies
    public void answerQuestionProduct(int productId, int questionID, String answer ) {
        TreeMap<String, String> params = new TreeMap<>();
        // Empty string means all products

        params.put(ApiConstants.PRODUCT_ID, String.valueOf(productId));
        params.put(ApiConstants.QUESTION_INDEX, String.valueOf(questionID));
        params.put(ApiConstants.CHOSEN_OPTION, answer);
        super.needsToken = true;


        JsonResult result = null;
        try {
            // Call API
            result = post(ApiConstants.PRODUCTS_PATH + "/" + productId + "/" + ApiConstants.ANSWER, params, null);
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
        ProductService.ProductDetails details = result.getObject(ApiConstants.RET_RESULT, ProductService.ProductDetails.class);
        if (details == null) {
            // This should never happen, the API should always return an object or an error
            throwInvalidResponseError(result, ApiConstants.RET_RESULT);
        }

    }

}
