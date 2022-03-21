package com.econnect.Utilities;

import android.app.AlertDialog;

import androidx.fragment.app.Fragment;

public class PopupMessage {
    public static void warning(Fragment caller, String text) {
        AlertDialog.Builder builder = new AlertDialog.Builder(caller.getActivity());
        builder.setMessage(text).setTitle("Warning");
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
