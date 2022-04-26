package com.econnect.client.Profile;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.fragment.app.Fragment;
import com.econnect.API.ProfileService;

import java.util.ArrayList;


public class MedalListAdapter extends BaseAdapter {
    private final Fragment owner;
    private final int highlightColor;
    private final Drawable defaultImage;
    private final ArrayList<ProfileService.User.Medal> medals;

    public MedalListAdapter(Fragment owner, int highlightColor, Drawable defaultImage, ArrayList<ProfileService.User.Medal> medals) {
        this.owner = owner;
        this.highlightColor = highlightColor;
        this.defaultImage = defaultImage;
        this.medals = medals;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }
}
