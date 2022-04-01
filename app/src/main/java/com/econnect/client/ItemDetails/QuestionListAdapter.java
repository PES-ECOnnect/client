package com.econnect.client.ItemDetails;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.econnect.API.IAbstractProduct;
import com.econnect.API.ProductService.ProductDetails.Question;
import com.econnect.Utilities.ExecutionThread;
import com.econnect.Utilities.PopupMessage;
import com.econnect.client.R;

public class QuestionListAdapter extends BaseAdapter {

    private final Question[] _questions;
    private final ProductDetailsFragment _fragment;
    private static LayoutInflater inflater = null;

    public QuestionListAdapter(ProductDetailsFragment owner, Question[] questions) {
        this._questions = questions;
        this._fragment = owner;
        inflater = (LayoutInflater) owner.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return _questions.length;
    }

    @Override
    public Question getItem(int i) {
        return _questions[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Initialize view and product
        View vi = convertView;
        if (vi == null) {
            vi = inflater.inflate(R.layout.question_list_item, null);
        }
        final Question q = _questions[position];
        int totalVotes = q.num_no + q.num_yes;
        int percentVotes;
        if (totalVotes > 0) percentVotes = (100 * q.num_yes) / totalVotes;
        else percentVotes = -1;

        // Set question text
        TextView questionText = vi.findViewById(R.id.questionText);
        String text;
        if (totalVotes != 1) text = vi.getResources().getString(R.string.question_text_and_votes, q.text, totalVotes);
        else text = vi.getResources().getString(R.string.question_text_and_votes_one, q.text, totalVotes);
        questionText.setText(text);

        // Set percent
        if (percentVotes != -1) {
            ProgressBar bar = vi.findViewById(R.id.questionPercentBar);
            bar.setProgress(percentVotes);

            TextView yesPercent = vi.findViewById(R.id.yesPercentText);
            yesPercent.setText(percentVotes + "%");
            TextView noPercent = vi.findViewById(R.id.noPercentText);
            noPercent.setText(100-percentVotes + "%");
        }

        // Set listeners
        Button voteYesButton = vi.findViewById(R.id.voteYesButton);
        voteYesButton.setOnClickListener(view -> _fragment.question(position, true));
        Button voteNoButton = vi.findViewById(R.id.voteNoButton);
        voteNoButton.setOnClickListener(view -> _fragment.question(position, false));

        return vi;
    }
}
