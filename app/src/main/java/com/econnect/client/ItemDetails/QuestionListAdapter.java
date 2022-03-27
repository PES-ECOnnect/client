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
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.econnect.API.IAbstractProduct;
import com.econnect.API.ProductService.ProductDetails.Question;
import com.econnect.Utilities.ExecutionThread;
import com.econnect.client.R;

public class QuestionListAdapter extends BaseAdapter {

    private final Question[] _questions;
    private static LayoutInflater inflater = null;

    public QuestionListAdapter(Fragment owner, Question[] questions) {
        this._questions = questions;
        inflater = (LayoutInflater) owner.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return _questions.length;
    }

    @Override
    public Object getItem(int i) {
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
            vi = inflater.inflate(R.layout.product_list_item, null);
        }
        final Question q = _questions[position];

        // Set question text
        TextView questionText = vi.findViewById(R.id.questionText);
        String text = vi.getResources().getString(R.string.question_text_and_votes, "aaa", 5);
        questionText.setText(text);

        return vi;
    }
}
