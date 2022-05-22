package com.econnect.Utilities;

import com.econnect.client.StartupActivity;

public class Translate {
    public static String id(int id) {
        return StartupActivity.globalContext().getString(id);
    }
    public static String id(int id, Object... formatArgs) {
        return StartupActivity.globalContext().getString(id, formatArgs);
    }
}
