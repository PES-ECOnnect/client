package com.econnect.Utilities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.fragment.app.Fragment;

public class SettingsFile {
    private final SharedPreferences _sharedPref;
    private final String _SETTINGS_FILENAME = "stored_settings";

    public SettingsFile(Activity owner) {
        // load file
        _sharedPref = owner.getSharedPreferences(_SETTINGS_FILENAME, Context.MODE_PRIVATE);
    }
    public SettingsFile(Fragment owner) {
        this(owner.getActivity());
    }

    // Get a stored value. Returns null if key was not present
    public String getString(String key) {
        return _sharedPref.getString(key, null);
    }
    public Integer getInt(String key) {
        if (!_sharedPref.contains(key))
            return null;
        return _sharedPref.getInt(key, -1);
    }

    public void putString(String key, String value) {
        _sharedPref.edit().putString(key, value).apply();
    }
    public void putInt(String key, int value) {
        _sharedPref.edit().putInt(key, value).apply();
    }

    // Remove a key
    public void remove(String key) {
        _sharedPref.edit().remove(key).apply();
    }

    // Remove all keys
    public void clear() {
        _sharedPref.edit().clear().apply();
    }
}
