package com.econnect.client.Products;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.econnect.API.ProductService.Product;
import com.econnect.client.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductListAdapter extends BaseAdapter implements Filterable {
    Context context;
    Product[] allProducts;
    List<Product> data;
    private static LayoutInflater inflater = null;

    public ProductListAdapter(Context context, Product[] allProducts) {
        this.context = context;
        this.allProducts = allProducts;
        data = Arrays.asList(allProducts);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (vi == null) {
            vi = inflater.inflate(R.layout.product_list_item, null);
        }
        Product p = data.get(position);

        TextView name = vi.findViewById(R.id.product_item_name);
        name.setText(p.getName());

        TextView manufacturer = vi.findViewById(R.id.product_item_manufacturer);
        manufacturer.setText(p.getManufacturer());

        TextView rating = vi.findViewById(R.id.product_item_rating);
        rating.setText(String.format("%.02f", p.getAvgRating()));

        // TODO: Set image

        return vi;
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();

                if (constraint == null || constraint.length() == 0) {
                    results.values = Arrays.asList(allProducts);
                    results.count = allProducts.length;
                    return results;
                }

                ArrayList<Product> filteredResults = new ArrayList<>();
                constraint = constraint.toString().toLowerCase();
                for (Product p : allProducts) {
                    if (p.getName().toLowerCase().contains(constraint))  {
                        filteredResults.add(p);
                    }
                }
                results.values = filteredResults;
                results.count = filteredResults.size();
                return results;
            }

            @Override
            @SuppressWarnings("unchecked")
            protected void publishResults(CharSequence constraint, FilterResults results) {
                data = (List<Product>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
