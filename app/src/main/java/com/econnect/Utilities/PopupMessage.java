package com.econnect.Utilities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class PopupMessage {
    public static void warning(Fragment caller, String text) {
        AlertDialog.Builder builder = new AlertDialog.Builder(caller.getContext());
        builder.setMessage(text).setTitle("Warning");
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public static void showToast(Fragment caller, String text) {
        Toast.makeText(caller.getContext(), text, Toast.LENGTH_SHORT).show();
    }
    public static void showToast(Activity caller, String text) {
        Toast.makeText(caller, text, Toast.LENGTH_SHORT).show();
    }

    // Show a new Yes/No dialog with custom behaviour for both buttons
    public static void yesNoDialog(Fragment caller,
                                   String title,
                                   String message,
                                   DialogInterface.OnClickListener yesListener,
                                   DialogInterface.OnClickListener noListener) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(caller.getContext());
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder
            .setMessage(message)
            .setCancelable(false)
            .setPositiveButton("Yes",yesListener)
            .setNegativeButton("No", noListener);

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    // Show a new Yes/No dialog with custom behaviour for the Yes button (No = cancel)
    public static void yesNoDialog(Fragment caller, String title, String message,
                                   DialogInterface.OnClickListener yesListener) {
        yesNoDialog(caller, title, message, yesListener, (dialog, id) -> dialog.cancel());
    }

    public static void okDialog(Fragment caller, String title, String message,
                                DialogInterface.OnClickListener okListener) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(caller.getContext());
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder
            .setMessage(message)
            .setCancelable(false)
            .setPositiveButton("OK", okListener);

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public static void showPopupMenu(int menuId, View anchor, OnMenuItemClickListener listener) {
        PopupMenu popupMenu = new PopupMenu(anchor.getContext(), anchor);
        popupMenu.setOnMenuItemClickListener(listener);
        popupMenu.inflate(menuId);
        popupMenu.show();
    }
}
