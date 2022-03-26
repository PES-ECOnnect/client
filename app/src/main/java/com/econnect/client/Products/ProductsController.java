package com.econnect.client.Products;

import android.view.View;

import com.econnect.Utilities.PopupMessage;

public class ProductsController {

    private final ProductsFragment _fragment;

    public ProductsController(ProductsFragment fragment) {
        this._fragment = fragment;
    }

    View.OnClickListener testButton() { return view -> testButtonClick(); }

    private void testButtonClick() {
        PopupMessage.warning(_fragment, "It works!");
    }
}
