package com.econnect.client.ItemDetails;

import static android.view.ViewGroup.FOCUS_BLOCK_DESCENDANTS;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.PopupMenu;

import androidx.appcompat.content.res.AppCompatResources;
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

    public ProductDetailsFragment() {
        super(FragmentProductDetailsBinding.class);
    }

    public void setController(IDetailsController ctrl) {
        this._ctrl = ctrl;
    }

    @Override
    protected void addListeners() {
        // TODO: decide how many stars we can give (start at 0?)
        binding.star1.setOnClickListener(view -> _ctrl.setStars(1));
        binding.star2.setOnClickListener(view -> _ctrl.setStars(2));
        binding.star3.setOnClickListener(view -> _ctrl.setStars(3));
        binding.star4.setOnClickListener(view -> _ctrl.setStars(4));
        binding.star5.setOnClickListener(view -> _ctrl.setStars(5));
        binding.addRatingButton.setOnClickListener(view -> _ctrl.reviewProduct());
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
                binding.star1.setImageDrawable(fullStar);
                binding.star2.setImageDrawable(emptyStar);
                binding.star3.setImageDrawable(emptyStar);
                binding.star4.setImageDrawable(emptyStar);
                binding.star5.setImageDrawable(emptyStar);
                break;
            case 2:
                binding.star1.setImageDrawable(fullStar);
                binding.star2.setImageDrawable(fullStar);
                binding.star3.setImageDrawable(emptyStar);
                binding.star4.setImageDrawable(emptyStar);
                binding.star5.setImageDrawable(emptyStar);
                break;
            case 3:
                binding.star1.setImageDrawable(fullStar);
                binding.star2.setImageDrawable(fullStar);
                binding.star3.setImageDrawable(fullStar);
                binding.star4.setImageDrawable(emptyStar);
                binding.star5.setImageDrawable(emptyStar);
                break;
            case 4:
                binding.star1.setImageDrawable(fullStar);
                binding.star2.setImageDrawable(fullStar);
                binding.star3.setImageDrawable(fullStar);
                binding.star4.setImageDrawable(fullStar);
                binding.star5.setImageDrawable(emptyStar);
                break;
            case 5:
                binding.star1.setImageDrawable(fullStar);
                binding.star2.setImageDrawable(fullStar);
                binding.star3.setImageDrawable(fullStar);
                binding.star4.setImageDrawable(fullStar);
                binding.star5.setImageDrawable(fullStar);
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


}