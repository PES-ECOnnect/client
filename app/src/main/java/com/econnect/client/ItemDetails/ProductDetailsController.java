package com.econnect.client.ItemDetails;

import android.graphics.Bitmap;

import com.econnect.API.ProductService;
import com.econnect.API.ProductService.ProductDetails;
import com.econnect.API.ServiceFactory;
import com.econnect.Utilities.BitmapLoader;
import com.econnect.Utilities.ExecutionThread;
import com.econnect.Utilities.PopupMessage;
import com.econnect.API.QuestionService;
import com.econnect.API.ReviewService;

public class ProductDetailsController implements IDetailsController {

    private final ProductDetailsFragment _fragment;
    private final int _productId;
    private ProductDetails _product;
    private int stars;

    public ProductDetailsController(ProductDetailsFragment fragment, int productId) {
        this._fragment = fragment;
        this._productId = productId;
        stars = 0;
    }

    public void setStars(int i){
        stars = i;
        _fragment.updateStars(stars);
    }
    @Override
    public void updateUIElements() {
        ExecutionThread.nonUI(() -> {
            try {
                // Get product
                ProductService service = ServiceFactory.getInstance().getProductService();
                _product = service.getProductDetails(_productId);

                ExecutionThread.UI(_fragment, () -> {
                    _fragment.setTitle(_product.name);
                    _fragment.setAverageRating(_product.ratings);
                    _fragment.setQuestionsElements(_product.questions);
                });

                // Fetch image
                Bitmap bmp = BitmapLoader.fromURLResizeHeight(_product.imageURL, 128);
                ExecutionThread.UI(_fragment, () -> {
                    if (bmp != null) _fragment.setImage(bmp);
                });
            }
            catch (Exception e) {
                ExecutionThread.UI(_fragment, () -> {
                    PopupMessage.warning(_fragment, "Could not get product info:\n" + e.getMessage());
                });
            }
        });
    }

    // answer = true => yes // false otherwise
    public void updateQuestions(int idQuestionUpdated, boolean answer){
        if(_product.questions[idQuestionUpdated].user_answer != "none"){
          if(answer && _product.questions[idQuestionUpdated].user_answer != "no"){
                _product.questions[idQuestionUpdated].user_answer = "yes";
                _product.questions[idQuestionUpdated].num_no -= 1;
                _product.questions[idQuestionUpdated].num_yes += 1;
          }
          else if(!answer && _product.questions[idQuestionUpdated].user_answer != "yes"){
                _product.questions[idQuestionUpdated].user_answer = "no";
                _product.questions[idQuestionUpdated].num_no += 1;
                _product.questions[idQuestionUpdated].num_yes -= 1;
          }
        }
        else{
            if(_product.questions[idQuestionUpdated].user_answer != "no"){
                _product.questions[idQuestionUpdated].user_answer = "no";
                _product.questions[idQuestionUpdated].num_no += 1;
            }
            else{
                _product.questions[idQuestionUpdated].user_answer = "yes";
                _product.questions[idQuestionUpdated].num_yes += 1;
            }
        }
        _fragment.setQuestionsElements(_product.questions);
    }

    @Override
    public void reviewProduct() {


        if(stars == 0) {
            PopupMessage.warning(_fragment, "You need to select some stars to review");
            return;
        }
        ExecutionThread.nonUI(() -> {
            try {
                ReviewService reviewService = ServiceFactory.getInstance().getReviewService();
                reviewService.reviewProduct(_productId, stars);
                updateUIElements();

            } catch (Exception e) {
                ExecutionThread.UI(_fragment, () -> {
                    PopupMessage.warning(_fragment, "Could not add review:\n" + e.getMessage());
                });
            }
        });
    }

    @Override
    public void answerQuestion(int questionId, QuestionAnswer answer){
        ExecutionThread.nonUI(()->{
            try{
                QuestionService questionService = ServiceFactory.getInstance().getQuestionService();
                if (answer == QuestionAnswer.yes) {
                    questionService.answerQuestionProduct(_productId, questionId, true);
                }
                else if (answer == QuestionAnswer.no) {
                    questionService.answerQuestionProduct(_productId, questionId, false);
                }
                else {
                    questionService.removeQuestionProduct(_productId, questionId);
                }
                updateQuestions(questionId, answer == QuestionAnswer.yes);
            }
            catch (Exception e){
                ExecutionThread.UI(_fragment, () -> {
                    PopupMessage.warning(_fragment, "Could not cast vote:\n" + e.getMessage());
                });
            }
        });
    }

    public void updateReview(int review){
        String s = "none"; // _product.ratings_user;
        if(s.equals("none") /* el usuario no ha votado */){
            _product.ratings[review-1]++;
        }
        else{
            switch (s) {
                case "1":
                    break;
            }
        }
    }
}
