package com.econnect.client.Forum;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.econnect.API.ForumService.Post;
import com.econnect.Utilities.ExecutionThread;
import com.econnect.client.R;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class PostListAdapter extends BaseAdapter {
    private final Fragment _owner;
    private final int _highlightColor;
    private final IPostCallback _callback;

    private static LayoutInflater _inflater = null;
    private final Post[] _data;

    public PostListAdapter(Fragment owner, IPostCallback callback, int highlightColor, Post[] posts) {
        this._owner = owner;
        this._highlightColor = highlightColor;
        this._data = posts;
        this._callback = callback;
        _inflater = (LayoutInflater) owner.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return _data.length;
    }

    @Override
    public Object getItem(int position) {
        return _data[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Initialize view and product
        final Post p = _data[position];
        final View vi;
        if (convertView != null) vi = convertView;
        else vi = _inflater.inflate(R.layout.post_list_item, null);

        // Set author name
        TextView authorName = vi.findViewById(R.id.postUsernameText);
        authorName.setText(p.username);

        // TODO: Set medal

        // Set time text
        TextView timeText = vi.findViewById(R.id.postTimeText);
        timeText.setText(timestampToString(p.timestamp));

        // Set post body
        TextView textBody = vi.findViewById(R.id.postContentText);
        // Required for clickable tags to work
        textBody.setMovementMethod(LinkMovementMethod.getInstance());
        Spannable spannable = new SpannableString(p.text);
        // Iterate for all instances of '#'
        for (int index = p.text.indexOf('#'); index >= 0; index = p.text.indexOf('#', index + 1)) {
            // Skip # symbols in the middle of a word
            if (index > 0 && p.text.charAt(index-1) != ' ')
                continue;
            int end = p.text.indexOf(' ', index + 1);
            if (end == -1) end = p.text.length();
            // Set color and make bold, also make clickable
            String tag = p.text.substring(index+1, end); // Skip '#'
            spannable.setSpan(new TagClickableSpan(tag), index, end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        textBody.setText(spannable, TextView.BufferType.SPANNABLE);

        // Set likes and dislikes
        TextView likes = vi.findViewById(R.id.likesAmountText);
        likes.setText(String.format(Locale.getDefault(), "%d", p.likes));
        TextView dislikes = vi.findViewById(R.id.dislikesAmountText);
        dislikes.setText(String.format(Locale.getDefault(), "%d", p.dislikes));

        // Set item image
        ImageView image = vi.findViewById(R.id.postImage);
        image.addOnLayoutChangeListener((v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> {
            // This is called once the layout is inflated, so that image.getWidth() returns a valid value
            ExecutionThread.nonUI(()->{
                // The post caches the bitmap, so subsequent redraws are faster and the returned pointer is always the same
                Bitmap bmp = p.getImage(image.getWidth());
                // If the bitmap has not changed, skip
                Drawable d = image.getDrawable();
                if (d != null && ((BitmapDrawable) d).getBitmap() == bmp)
                    return;
                // Else, set image
                ExecutionThread.UI(_owner, ()-> {
                    if (bmp == null) image.setVisibility(View.GONE);
                    else {
                        image.setImageBitmap(bmp);
                        image.setVisibility(View.VISIBLE);
                    }
                });
            });
        });


        // Set listeners
        ImageButton shareButton = vi.findViewById(R.id.sharePostButton);
        shareButton.setOnClickListener(view -> _callback.share(p));

        ImageButton likeButton = vi.findViewById(R.id.likePostButton);
        likeButton.setOnClickListener(view -> voteButtonListener(vi, true, p));

        ImageButton dislikeButton = vi.findViewById(R.id.dislikePostButton);
        dislikeButton.setOnClickListener(view -> voteButtonListener(vi, false, p));

        // Highlight previous user choice
        if (p.useroption == Post.OPT_LIKE) {
            likeButton.setColorFilter(_highlightColor);
        }
        else if (p.useroption == Post.OPT_DISLIKE) {
            dislikeButton.setColorFilter(_highlightColor);
        }
        else if (p.useroption != Post.OPT_NONE) {
            throw new RuntimeException("Invalid user option: " + p.useroption);
        }

        return vi;
    }

    private String timestampToString(long date) {
        long time = System.currentTimeMillis() / 1000;

        // Difference between dates, in seconds
        long mills = time - date;
        // Convert to hours + minutes
        int hours = (int) (mills / (60 * 60));
        int mins = (int) ((mills - (hours * 3600) / 60) % 60);

        if (hours == 0) {
            return mins + " minutes ago";
        }
        else if (hours < 24) {
            return hours + " hours ago";
        }
        else {
            Date dateTemp = new Date(date * 1000L);
            DateFormat dateFormat = DateFormat.getDateInstance();
            return dateFormat.format(dateTemp);
        }
    }

    private class TagClickableSpan extends ClickableSpan {
        String _tag;
        public TagClickableSpan(String tag) {
            _tag = tag;
        }

        @Override
        public void onClick(@NonNull View view) {
            _callback.tagClicked(_tag);
        }
        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            // No underline, set color and make bold
            ds.setUnderlineText(false);
            ds.setColor(_highlightColor);
            ds.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        }
    }

    private void voteButtonListener(View vi, boolean like, Post p) {
        final ImageButton button;
        final TextView text;
        final boolean removeThis, removeTwin;
        if (like) {
            button = vi.findViewById(R.id.likePostButton);
            text = vi.findViewById(R.id.likesAmountText);
            removeThis = (p.useroption == Post.OPT_LIKE);
            removeTwin = (p.useroption == Post.OPT_DISLIKE);
        }
        else {
            button = vi.findViewById(R.id.dislikePostButton);
            text = vi.findViewById(R.id.dislikesAmountText);
            removeThis = (p.useroption == Post.OPT_DISLIKE);
            removeTwin = (p.useroption == Post.OPT_LIKE);
        }

        // Decrement twin counter, emulate click on other counter
        if (removeTwin) {
            voteButtonListener(vi, !like, p);
        }

        // Update button color
        if (removeThis) button.clearColorFilter();
        else button.setColorFilter(_highlightColor);

        // Update text
        final int increment = removeThis ? -1 : 1;
        final int newCount;
        if (like) newCount = (p.likes += increment);
        else newCount = (p.dislikes += increment);
        text.setText(String.format(Locale.getDefault(), "%d", newCount));

        // Update user option
        if (removeThis) p.useroption = Post.OPT_NONE;
        else p.useroption = like ? Post.OPT_LIKE : Post.OPT_DISLIKE;

        // Call API
        _callback.vote(p.postid, like, removeThis);
    }
}
