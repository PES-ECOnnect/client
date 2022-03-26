package com.econnect.client.Products;

import android.widget.ArrayAdapter;

import com.econnect.API.ProductService;
import com.econnect.Utilities.CustomFragment;
import com.econnect.client.R;
import com.econnect.client.databinding.FragmentProductsBinding;

import java.util.List;

public class ProductsFragment extends CustomFragment<FragmentProductsBinding> {

    private final ProductsController _ctrl = new ProductsController(this);
    private ProductListAdapter _products_adapter;

    public ProductsFragment() {
        super(FragmentProductsBinding.class);
    }

    @Override
    protected void addListeners() {
        binding.filterDropdown.setOnItemClickListener(_ctrl.typesDropdown());
        binding.searchText.addTextChangedListener(_ctrl.searchText());
        //binding.itemList.setOnItemClickListener();

        _ctrl.updateLists();
    }

    void setTypesDropdownElements(List<String> items) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.types_list_item, items);
        binding.filterDropdown.setAdapter(adapter);
        binding.filterDropdown.setText(_ctrl.getDefaultType(), false);
    }

    void setProductElements(ProductService.Product[] products) {
        _products_adapter = new ProductListAdapter(getContext(), products);
        binding.itemList.setAdapter(_products_adapter);
    }

    void filterProductList(String filter) {
        _products_adapter.getFilter().filter(filter);
    }
    void filterProductList() {
        filterProductList(binding.searchText.getText().toString());
    }
}