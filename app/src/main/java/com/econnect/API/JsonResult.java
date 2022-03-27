package com.econnect.API;

import com.google.gson.*;

public class JsonResult {
    private final JsonObject _jsonObject;

    public JsonResult(JsonElement jsonElement) {
        this._jsonObject = jsonElement.getAsJsonObject();
    }

    public String getAttribute(String attrName) {
        JsonElement element = _jsonObject.get(attrName);
        if (element == null) return null;

        return element.getAsString();
    }

    // Get an array of objects
    public <T> T getArray(String attrName, Class<T> arrayClass) {
        if (!arrayClass.isArray()) {
            throw new IllegalArgumentException("classOfT must be an array type");
        }

        JsonElement element = _jsonObject.get(attrName);
        if (element == null) return null;

        JsonArray array = element.getAsJsonArray();
        if (array == null) return null;

        return new Gson().fromJson(array, arrayClass);
    }

    public <T> T getObject(String attrName, Class<T> objectClass) {
        JsonElement element = _jsonObject.get(attrName);
        if (element == null) return null;
        return new Gson().fromJson(element, objectClass);
    }

    @Override
    public String toString() {
        return _jsonObject.toString();
    }

    // Return the number of attributes in the JsonResult
    public int size() {
        return _jsonObject.size();
    }
}
