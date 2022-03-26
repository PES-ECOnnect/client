package com.econnect.client.Products;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        binding.myButton.setOnClickListener(_ctrl.testButton());
    }
}