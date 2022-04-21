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
    public void updateQuestions(int idQuestionUpdated, boolean newAnswer){
        ProductDetails.Question q = new ProductDetails.Question();
        for (ProductDetails.Question question : _product.questions){
            if(question.questionid == idQuestionUpdated){
                q = question;
                break;
            }
        }
        String oldAnswer = q.user_answer;
        if(!oldAnswer.equals("none")){
            if(newAnswer && oldAnswer.equals("no")){
                q.user_answer = "yes";
                q.num_no -= 1;
                q.num_yes += 1;
            }
            else if(!newAnswer && oldAnswer.equals("yes")){
                q.user_answer = "no";
                q.num_no += 1;
                q.num_yes -= 1;
            }
        }
        else{
            if(!newAnswer){
                q.user_answer = "no";
                q.num_no += 1;
            }
            else{
                q.user_answer = "yes";
                q.num_yes += 1;
            }
        }
        ExecutionThread.UI(_fragment, () -> {
            _fragment.setQuestionsElements(_product.questions);
        });
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
                updateReview();

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

    public void updateReview(){// _product.ratings_user;
        if(_product.userRate != 0) /* el usuario ha votado */{
            _product.ratings[_product.userRate]--;
        }
        _product.userRate = stars;
        _product.ratings[stars]++;
        ExecutionThread.UI(_fragment, () -> {
            _fragment.setAverageRating(_product.ratings);
        });
    }
}
