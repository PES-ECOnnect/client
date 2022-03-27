package com.econnect.client.ItemDetails;

import com.econnect.Utilities.CustomFragment;
import com.econnect.client.databinding.FragmentProductDetailsBinding;

public class ProductDetailsFragment extends CustomFragment<FragmentProductDetailsBinding> {

    ProductDetailsController ctrl;
    Integer productId;
    public ProductDetailsFragment() {
        super(FragmentProductDetailsBinding.class);
        ctrl = new ProductDetailsController(this);
        productId = 0; // Aqui añadiremos id del producto a valorar, preguntar, etc.
    }



    @Override
    protected void addListeners() {
        binding.star1.setOnClickListener(view -> ctrl.valorateProduct(1, productId));
        binding.star2.setOnClickListener(view -> ctrl.valorateProduct(2, productId));
        binding.star3.setOnClickListener(view -> ctrl.valorateProduct(3, productId));
        binding.star4.setOnClickListener(view -> ctrl.valorateProduct(4, productId));
        binding.star5.setOnClickListener(view -> ctrl.valorateProduct(5, productId));
    }
}