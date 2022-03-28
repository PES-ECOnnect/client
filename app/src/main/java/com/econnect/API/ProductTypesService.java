package com.econnect.API;

public class ProductTypesService extends Service {
    
    // Only allow instantiating from ServiceFactory
    ProductTypesService() {}
    
    public static class ProductType {
        // Important: The name of these attributes must match the ones in the returned JSON
        // Gson will initialize these fields to the received values
        public final String name;
        public final String[] questions;

        public ProductType(String name, String[] questions) {
            this.name = name;
            this.questions = questions;
        }
    }
    
    // Get all product types
    public ProductType[] getProductTypes() {
        // Call API
        super.needsToken = true;
        JsonResult result = get(ApiConstants.TYPES_PATH, null);
        
        // Parse result
        ProductType[] productTypes = result.getArray(ApiConstants.RET_RESULT, ProductType[].class);
        if (productTypes == null) {
            // This should never happen, the API should always return an array or an error
            throwInvalidResponseError(result, ApiConstants.RET_RESULT);
        }
        
        return productTypes;
    }
}
