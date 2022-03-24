package com.econnect.Utilities;


import android.app.Activity;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

public class ExecutionThread {
    // Execute a runnable on the UI thread
    public static void UI(Fragment caller, Runnable runnable) {
        caller.getActivity().runOnUiThread(runnable);
    }

    // Execute a runnable on a non-UI thread
    public static void nonUI(Runnable runnable) {
        new Thread(runnable).start();
    }

    // Navigate to another fragment
    public static void navigate(Fragment caller, int action) {
        UI(caller, ()-> {
            NavController nc =NavHostFragment.findNavController(caller);
            nc.navigate(action);
        });
    }
}
