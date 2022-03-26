package com.econnect.client.Products;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.econnect.API.ProductService.Product;
import com.econnect.client.R;

public class ProductListAdapter extends BaseAdapter {
    Context context;
    Product[] data;
    private static LayoutInflater inflater = null;

    public ProductListAdapter(Context context, Product[] data) {
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.length;
    }

    @Override
    public Object getItem(int position) {
        return data[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.product_list_item, null);

        TextView name = vi.findViewById(R.id.product_item_name);
        name.setText(data[position].getName());
        TextView manufacturer = vi.findViewById(R.id.product_item_manufacturer);
        manufacturer.setText(data[position].getManufacturer());
        TextView rating = vi.findViewById(R.id.product_item_rating);
        rating.setText(String.format("%.02f", data[position].getAvgRating()));

        // TODO: Set image

        return vi;
    }
}
