package com.econnect.client.Products;

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


    void updateTypesDropdownElements() {
        ExecutionThread.nonUI(()->{
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

            // Get products of this type (on a non-ui thread)
            ExecutionThread.nonUI(() -> {
                ProductService service = ServiceFactory.getInstance().getProductService();
                ProductService.Product[] products = service.getProducts(type);

                ExecutionThread.UI(_fragment, ()->{
                    _fragment.setProductElements(products);
                });
            });
        };
    }
}
