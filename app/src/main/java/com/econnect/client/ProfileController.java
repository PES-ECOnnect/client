package com.econnect.client;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;

import com.econnect.API.LoginService;
import com.econnect.API.ServiceFactory;
import com.econnect.Utilities.ExecutionThread;
import com.econnect.Utilities.PopupMessage;

public class ProfileController {

    private final ProfileFragment fragment;

    ProfileController(ProfileFragment fragment) {
        this.fragment = fragment;
    }

    // Boilerplate for interfacing with the fragment
    View.OnClickListener logoutButton() {
        return view -> logoutButtonClick(); }

    public void logoutButtonClick() {

        AlertDialog.Builder builder = new AlertDialog.Builder(fragment.getContext());
        builder.setCancelable(true);
        builder.setTitle("LOG OUT");
        builder.setMessage("Are You Sure?");
        builder.setPositiveButton("Confirm",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        fragment.enableInput(false);
                        ExecutionThread.nonUI(() -> {
                            // Logout
                            LoginService loginService = ServiceFactory.getInstance().getLoginService();
                            try {
                                loginService.logout();
                                ExecutionThread.UI(fragment, ()->{
                                    fragment.enableInput(true);
                                    fragment.getActivity().finish();
                                    //ExecutionThread.navigate(fragment, R.id.macaco);
                                });


                            }
                            catch (Exception e) {
                                // Return to UI for showing errors
                                ExecutionThread.UI(fragment, ()->{
                                    fragment.enableInput(true);
                                    PopupMessage.warning(fragment, "There has been an error: " + e.getMessage());
                                });
                            }
                        });
                    }
                });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();



    }

}
