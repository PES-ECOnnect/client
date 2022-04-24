package com.econnect.API;

import com.econnect.API.Exceptions.ApiException;
import com.econnect.Utilities.ExecutionThread;

import java.util.TreeMap;

public class QuestionService extends Service{

    QuestionService(){}

    // this works for products and companies
    private void answerQuestionProduct(String path, int productId, int questionId, String answer) {
        TreeMap<String, String> params = new TreeMap<>();
        // Empty string means all products

        params.put(ApiConstants.QUESTION_INDEX, Integer.toString(questionId));
        params.put(ApiConstants.CHOSEN_OPTION, answer);
        super.needsToken = true;


        JsonResult result = null;
        try {
            // Call API
            result = post(path + "/" + productId + "/" + ApiConstants.ANSWER, params, null);
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
        expectOkStatus(result);
    }

    public void answerQuestionProduct(int productId, int questionId, boolean answer ) {
        answerQuestionProduct(ApiConstants.PRODUCTS_PATH, productId, questionId, answer ? "1" : "0" );
    }

    public void removeQuestionProduct(int productId, int questionId ) {
        answerQuestionProduct(ApiConstants.PRODUCTS_PATH, productId, questionId, "none" );
    }

    public void answerQuestionCompany(int productId, int questionId, boolean answer ) {
        answerQuestionProduct(ApiConstants.COMPANIES_PATH, productId, questionId, answer ? "1" : "0" );
    }

    public void removeQuestionCompany(int productId, int questionId ) {
        answerQuestionProduct(ApiConstants.COMPANIES_PATH, productId, questionId, "none" );
    }
}
