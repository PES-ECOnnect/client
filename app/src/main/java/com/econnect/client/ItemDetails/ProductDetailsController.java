package com.econnect.client.ItemDetails;

import android.graphics.Bitmap;

import com.econnect.API.ProductService;
import com.econnect.API.ProductService.ProductDetails;
import com.econnect.API.ServiceFactory;
import com.econnect.Utilities.BitmapLoader;
import com.econnect.Utilities.ExecutionThread;
import com.econnect.Utilities.PopupMessage;

public class ProductDetailsController implements IDetailsController {

    private final ProductDetailsFragment _fragment;
    private final int _productId;
    private ProductDetails _product;

    public ProductDetailsController(ProductDetailsFragment fragment, int productId) {
        this._fragment = fragment;
        this._productId = productId;
    }

    @Override
    public void updateUIElements() {
        ExecutionThread.nonUI(() -> {
            try {
                // Get product
                ProductService service = ServiceFactory.getInstance().getProductService();
                _product = service.getProductDetails(_productId);

                ExecutionThread.UI(_fragment, () -> {
                    _fragment.setAverageRating(_product.getRatings());
                });

                // Fetch image
                Bitmap bmp = BitmapLoader.fromURL(_product.getImageURL(), 128);
                ExecutionThread.UI(_fragment, () -> {
                    if (bmp != null) _fragment.setImage(bmp);
                });
            }
            catch (Exception e) {
                ExecutionThread.UI(_fragment, () -> {
                    PopupMessage.warning(_fragment, "Could not get product info:\n" + e.getMessage());
                });
            }
        });
    }
}
