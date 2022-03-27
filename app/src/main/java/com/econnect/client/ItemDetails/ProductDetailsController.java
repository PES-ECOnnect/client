package com.econnect.client.ItemDetails;

import com.econnect.API.QuestionService;
import com.econnect.API.ReviewService;
import com.econnect.API.ServiceFactory;

public class ProductDetailsController {

    ProductDetailsFragment fragment;

    public ProductDetailsController(ProductDetailsFragment productDetailsFragment) {
        fragment = productDetailsFragment;
    }

    public void valorateProduct(int review, int pId) {
        try{
            ReviewService reviewService = ServiceFactory.getInstance().getReviewService();
            reviewService.reviewProduct(pId, review);
        } catch (Exception e){
            throw e;
        }
    }

    public void answerQuestion(int pId, int questionId, String answer){
        try{
            QuestionService questionService = ServiceFactory.getInstance().getQuestionService();
            questionService.answerQuestionProduct(pId, questionId, answer);
        } catch (Exception e){
            throw e;
        }
    }


}
