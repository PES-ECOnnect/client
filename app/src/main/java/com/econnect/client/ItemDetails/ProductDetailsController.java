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
                Bitmap bmp = BitmapLoader.fromURL(_product.imageURL, 128);
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

    @Override
    public void reviewProduct() {
        if(stars == 0) {
            PopupMessage.warning(_fragment, "You need to select some stars to review");
            return;
        }
        try{
            ReviewService reviewService = ServiceFactory.getInstance().getReviewService();
            reviewService.reviewProduct(_productId, stars);
        } catch (Exception e){
            throw e;
        }
    }

    @Override
    public void answerQuestion(String questionId, String answer){
        try{
            QuestionService questionService = ServiceFactory.getInstance().getQuestionService();
            questionService.answerQuestionProduct(_productId, questionId, answer);
            updateUIElements();
        } catch (Exception e){
            throw e;
        }
    }
}
