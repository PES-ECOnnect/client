package com.econnect.client.Profile;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.econnect.API.IAbstractProduct;
import com.econnect.Utilities.ExecutionThread;
import com.econnect.client.Profile.Medals.MedalUtils;
import com.econnect.client.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class StreetListAdapter extends BaseAdapter implements Filterable {
    private final Fragment owner;
    private final int highlightColor;


    private static LayoutInflater inflater = null;
    private List<String> data;

    private String lastQueryLower = null; // Store last query (in lowercase)

    public StreetListAdapter(Fragment owner, int highlightColor, String[] streets) {
        this.owner = owner;
        this.highlightColor = highlightColor;
        data = new ArrayList<>();
        inflater = (LayoutInflater) owner.requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        setInitialValues(streets);
    }

    protected void setInitialValues(String[] streets) {
        data = Arrays.asList(streets);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public List<String> getList(){
        return data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Initialize view and product
        View vi = convertView;
        if (vi == null) {
            vi = inflater.inflate(R.layout.street_list_item, null);
        }

        TextView nameStreet = vi.findViewById(R.id.street_name);
        nameStreet.setText(data.get(position));

        return vi;
    }

    // Implement search

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                // Store query for highlighting the item names
                if (constraint.length() == 0) constraint = null;

                // If filter is empty, return all selectable products
                if (constraint == null) {
                    ArrayList<String> res = new ArrayList<>();
                    for (String p : getList()) {
                        res.add(p);
                    }
                    results.values = res;
                    results.count = res.size();
                    lastQueryLower = null;
                    return results;
                }

                // Ignore case and trim
                String queryLower = constraint.toString().toLowerCase().trim();
                // Get only the products that contain (or begin with) the query
                ArrayList<String> containQuery = new ArrayList<>();
                ArrayList<String> beginWithQuery = new ArrayList<>();
                for (String p : getList()) {
                    // Skip all non-selectable products
                    // Add type to the corresponding list
                    String name = p.toLowerCase();
                    if (name.startsWith(queryLower)) {
                        beginWithQuery.add(p);
                    }
                    else if (name.contains(queryLower)) {
                        containQuery.add(p);
                    }
                }

                // Combine the two lists (beginWithQuery is preferred)
                beginWithQuery.addAll(containQuery);
                results.values = beginWithQuery;
                results.count = beginWithQuery.size();
                lastQueryLower = queryLower;

                return results;
            }

            @Override
            @SuppressWarnings("unchecked")
            protected void publishResults(CharSequence constraint, FilterResults results) {
                data = (List<String>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
