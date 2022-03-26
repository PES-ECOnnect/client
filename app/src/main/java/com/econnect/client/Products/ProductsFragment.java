package com.econnect.client.Products;

import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.econnect.Utilities.CustomFragment;
import com.econnect.client.R;
import com.econnect.client.databinding.FragmentProductsBinding;

public class ProductsFragment extends CustomFragment<FragmentProductsBinding> {

    private final ProductsController _ctrl = new ProductsController(this);

    public ProductsFragment() {
        super(FragmentProductsBinding.class);
    }

    @Override
    protected void addListeners() {
        String[] items = new String[]{"This is a very long text", "aaaaaa", "hello there"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.list_item, items);
        binding.filterDropdown.setAdapter(adapter);
        binding.filterDropdown.setText("Any", false);
    }
}