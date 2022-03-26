package com.econnect.client.Products;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;

import com.econnect.API.ProductService;
import com.econnect.API.ProductTypesService;
import com.econnect.API.ProductTypesService.ProductType;
import com.econnect.API.ServiceFactory;
import com.econnect.Utilities.ExecutionThread;
import com.econnect.Utilities.PopupMessage;

import java.util.ArrayList;

public class ProductsController {

    private final ProductsFragment _fragment;
    private final String _ALL_TYPES = "Any";

    public ProductsController(ProductsFragment fragment) {
        this._fragment = fragment;
    }


    void updateLists() {
        ExecutionThread.nonUI(()-> {
            updateTypesList();
            // null means all products
            updateProductsList(null);
        });
    }

    String getDefaultType() {
        return _ALL_TYPES;
    }

    AdapterView.OnItemClickListener typesDropdown() {
        return (parent, view, position, id) -> {
            // Get type (if selected index is 0, null means all types)
            String type;
            if (position == 0) type = null;
            else type = (String) parent.getItemAtPosition(position);
            // Update list
            updateProductsList(type);
        };
    }

    private void updateTypesList() {
        // Get types
        ProductTypesService service = ServiceFactory.getInstance().getProductTypesService();
        ProductType[] types = service.getProductTypes();
        // Allocate space for items (with extra "Any" element)
        ArrayList<String> items = new ArrayList<>(types.length + 1);
        items.add(_ALL_TYPES);
        for (ProductType t : types) items.add(t.getName());

        ExecutionThread.UI(_fragment, ()->{
            _fragment.setTypesDropdownElements(items);
        });
    }

    private void updateProductsList(String type) {
        // Get products of this type
        ProductService service = ServiceFactory.getInstance().getProductService();
        ProductService.Product[] products = service.getProducts(type);

        ExecutionThread.UI(_fragment, ()->{
            _fragment.setProductElements(products);
            _fragment.filterProductList();
        });
    }

    public TextWatcher searchText() {
        return new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {}
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                _fragment.filterProductList(s.toString());
            }
        };
    }
}
