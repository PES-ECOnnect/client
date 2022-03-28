package com.econnect.client.ItemDetails;

import android.graphics.Bitmap;

import com.econnect.API.CompanyService;
import com.econnect.API.CompanyService.CompanyDetails;
import com.econnect.API.QuestionService;
import com.econnect.API.ReviewService;
import com.econnect.API.ServiceFactory;
import com.econnect.Utilities.BitmapLoader;
import com.econnect.Utilities.ExecutionThread;
import com.econnect.Utilities.PopupMessage;

public class CompanyDetailsController implements IDetailsController {

    private final ProductDetailsFragment _fragment;
    private final int _companyId;
    private CompanyDetails _company;
    private int stars;


    public CompanyDetailsController(ProductDetailsFragment fragment, int companyId) {
        this._fragment = fragment;
        this._companyId = companyId;
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
                CompanyService service = ServiceFactory.getInstance().getCompanyService();
                _company = service.getCompanyDetails(_companyId);

                ExecutionThread.UI(_fragment, () -> {
                    _fragment.setTitle(_company.name);
                    _fragment.setAverageRating(_company.ratings);
                    _fragment.setQuestionsElements(_company.questions);
                });

                // Fetch image
                Bitmap bmp = BitmapLoader.fromURL(_company.imageURL, 128);
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
            reviewService.reviewProduct(_companyId, stars);
        } catch (Exception e){
            throw e;
        }
    }

    @Override
    public void answerQuestion(String questionId, String answer){
        try{
            QuestionService questionService = ServiceFactory.getInstance().getQuestionService();
            questionService.answerQuestionProduct(_companyId, questionId, answer);
        } catch (Exception e){
            throw e;
        }
    }
}
