package com.econnect.client.ItemDetails;

import static android.view.ViewGroup.FOCUS_BLOCK_DESCENDANTS;

import static java.security.AccessController.getContext;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;

import com.econnect.API.ProductService;
import com.econnect.API.ProductService.ProductDetails.Question;
import com.econnect.Utilities.CustomFragment;
import com.econnect.Utilities.PopupMessage;
import com.econnect.client.Products.ProductListAdapter;
import com.econnect.client.R;
import com.econnect.client.databinding.FragmentProductDetailsBinding;

public class ProductDetailsFragment extends CustomFragment<FragmentProductDetailsBinding> {

    private IDetailsController _ctrl;
    private AlertDialog.Builder reviewBuilder;
    private AlertDialog review;
    private ImageView star1Rpopup, star2Rpopup, star3Rpopup, star4Rpopup, star5Rpopup;
    private Button reviewpopup_cancel, reviewpopup_submit;


    public ProductDetailsFragment() {
        super(FragmentProductDetailsBinding.class);
    }

    public void setController(IDetailsController ctrl) {
        this._ctrl = ctrl;
    }

    @Override
    protected void addListeners() {
        binding.addRatingButton.setOnClickListener(view -> createReviewDialog());
        _ctrl.updateUIElements();
    }

    void question(int qId, IDetailsController.QuestionAnswer answer) {
        _ctrl.answerQuestion(qId, answer);
    }


    public void updateStars(int i){
        final Drawable fullStar = AppCompatResources.getDrawable(getContext(), R.drawable.ic_star_24);
        final Drawable emptyStar = AppCompatResources.getDrawable(getContext(), R.drawable.ic_star_empty_24);
        switch (i){
            case 1:
                star1Rpopup.setImageDrawable(fullStar);
                star2Rpopup.setImageDrawable(emptyStar);
                star3Rpopup.setImageDrawable(emptyStar);
                star4Rpopup.setImageDrawable(emptyStar);
                star5Rpopup.setImageDrawable(emptyStar);
                break;
            case 2:
                star1Rpopup.setImageDrawable(fullStar);
                star2Rpopup.setImageDrawable(fullStar);
                star3Rpopup.setImageDrawable(emptyStar);
                star4Rpopup.setImageDrawable(emptyStar);
                star5Rpopup.setImageDrawable(emptyStar);
                break;
            case 3:
                star1Rpopup.setImageDrawable(fullStar);
                star2Rpopup.setImageDrawable(fullStar);
                star3Rpopup.setImageDrawable(fullStar);
                star4Rpopup.setImageDrawable(emptyStar);
                star5Rpopup.setImageDrawable(emptyStar);
                break;
            case 4:
                star1Rpopup.setImageDrawable(fullStar);
                star2Rpopup.setImageDrawable(fullStar);
                star3Rpopup.setImageDrawable(fullStar);
                star4Rpopup.setImageDrawable(fullStar);
                star5Rpopup.setImageDrawable(emptyStar);
                break;
            case 5:
                star1Rpopup.setImageDrawable(fullStar);
                star2Rpopup.setImageDrawable(fullStar);
                star3Rpopup.setImageDrawable(fullStar);
                star4Rpopup.setImageDrawable(fullStar);
                star5Rpopup.setImageDrawable(fullStar);
                break;
        }
    }

    void setAverageRating(int[] votes) {
        final Drawable fullStar = AppCompatResources.getDrawable(getContext(), R.drawable.ic_star_24);
        final Drawable halfStar = AppCompatResources.getDrawable(getContext(), R.drawable.ic_star_half_24);
        final Drawable emptyStar = AppCompatResources.getDrawable(getContext(), R.drawable.ic_star_empty_24);
        final Drawable[] starDrawables = new Drawable[]{emptyStar, halfStar, fullStar};
        
        int rating = 0;
        int numVotes = 0;
        for (int i = 0; i < votes.length; i++) {
            rating += votes[i] * i;
            numVotes += votes[i];
        }
        float average = ((float) rating) / numVotes;

        int[] stars = new int[5]; // 0=empty, 1=half, 2=full
        // Set values of stars according to average
        for (int i = 0; i < 5; i++) {
            if (average >= i + 1) {
                stars[i] = 2;
            } else if (average >= i + 0.5) {
                stars[i] = 1;
            } else {
                stars[i] = 0;
            }
        }
        binding.star1.setImageDrawable(starDrawables[stars[0]]);
        binding.star2.setImageDrawable(starDrawables[stars[1]]);
        binding.star3.setImageDrawable(starDrawables[stars[2]]);
        binding.star4.setImageDrawable(starDrawables[stars[3]]);
        binding.star5.setImageDrawable(starDrawables[stars[4]]);

        // Set text for number of votes
        if (numVotes == 1) {
            binding.numberOfRatingsText.setText(getResources().getString(R.string.num_votes_text_one, numVotes));
        } else {
            binding.numberOfRatingsText.setText(getResources().getString(R.string.num_votes_text, numVotes));
        }
    }

    void setImage(Bitmap bmp) {
        binding.productImage.setImageBitmap(bmp);
    }

    void setQuestionsElements(Question[] questions) {
        int highlightColor = ContextCompat.getColor(getContext(), R.color.green);
        binding.questionsList.setAdapter(new QuestionListAdapter(this, questions, highlightColor));
    }

    void setTitle(String name) {
        binding.productNameText.setText(name);
    }

    public void createReviewDialog(){
        reviewBuilder = new AlertDialog.Builder(getContext());

        final View reviewPopupView = getLayoutInflater().inflate(R.layout.reviewpopup, null);


        star1Rpopup = (ImageView) reviewPopupView.findViewById(R.id.star1Rpopup);
        star2Rpopup = (ImageView) reviewPopupView.findViewById(R.id.star2Rpopup);
        star3Rpopup = (ImageView) reviewPopupView.findViewById(R.id.star3Rpopup);
        star4Rpopup = (ImageView) reviewPopupView.findViewById(R.id.star4Rpopup);
        star5Rpopup = (ImageView) reviewPopupView.findViewById(R.id.star5Rpopup);

        reviewpopup_cancel = (Button) reviewPopupView.findViewById(R.id.reviewpopup_cancel);
        reviewpopup_submit = (Button) reviewPopupView.findViewById(R.id.reviewpopup_submit);

        reviewBuilder.setView(reviewPopupView);
        review = reviewBuilder.create();
        review.show();

        star1Rpopup.setOnClickListener(view -> _ctrl.setStars(1));
        star2Rpopup.setOnClickListener(view -> _ctrl.setStars(2));
        star3Rpopup.setOnClickListener(view -> _ctrl.setStars(3));
        star4Rpopup.setOnClickListener(view -> _ctrl.setStars(4));
        star5Rpopup.setOnClickListener(view -> _ctrl.setStars(5));

        // Reset stars
        _ctrl.setStars(0);
        reviewpopup_cancel.setOnClickListener(view -> review.dismiss());

        reviewpopup_submit.setOnClickListener(view -> {
            _ctrl.reviewProduct();
            review.dismiss();
        });
    }


}