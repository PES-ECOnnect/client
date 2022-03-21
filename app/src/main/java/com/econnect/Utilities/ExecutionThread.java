package com.econnect.Utilities;


import android.app.Activity;

public class ExecutionThread {
    public static void UI(Activity caller, Runnable runnable) {
        caller.runOnUiThread(runnable);
    }
    
    public static void nonUI(Runnable runnable) {
        new Thread(runnable).start();
    }
}
