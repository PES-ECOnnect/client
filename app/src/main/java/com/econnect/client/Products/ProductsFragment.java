package com.econnect.client.Products;

import android.widget.ArrayAdapter;

import com.econnect.API.ProductService;
import com.econnect.Utilities.CustomFragment;
import com.econnect.client.R;
import com.econnect.client.databinding.FragmentProductsBinding;

import java.util.List;

public class ProductsFragment extends CustomFragment<FragmentProductsBinding> {

    private final ProductsController _ctrl = new ProductsController(this);

    public ProductsFragment() {
        super(FragmentProductsBinding.class);
    }

    @Override
    protected void addListeners() {
        binding.filterDropdown.setOnItemClickListener(_ctrl.typesDropdown());
        //binding.itemList.setOnItemClickListener();
        _ctrl.updateTypesDropdownElements();
    }

    void setTypesDropdownElements(List<String> items) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.types_list_item, items);
        binding.filterDropdown.setAdapter(adapter);
        binding.filterDropdown.setText(_ctrl.getDefaultType(), false);
    }

    void setProductElements(ProductService.Product[] products) {
        ProductListAdapter adapter = new ProductListAdapter(getContext(), products);
        binding.itemList.setAdapter(adapter);
    }
}