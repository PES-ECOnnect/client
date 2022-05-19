package com.econnect.client.Profile;

import android.app.AlertDialog;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.econnect.API.ProfileService;
import com.econnect.Utilities.ExecutionThread;
import com.econnect.Utilities.PopupMessage;
import com.econnect.client.Profile.Medals.MedalUtils;
import com.econnect.client.R;

public class LoggedUserProfileFragment extends ProfileFragment {

    // This controller is instantiated using instantiateController(), we can cast it to LoggedUserProfileController
    protected final LoggedUserProfileController _ctrl = (LoggedUserProfileController) super._ctrl;

    @Override
    protected ProfileController instantiateController() {
        return new LoggedUserProfileController(this);
    }

    @Override
    protected void addListeners() {
        super.addListeners();
        // Show floating button for logged user profile
        binding.profileMenuButton.setVisibility(View.VISIBLE);
        binding.profileMenuButton.setOnClickListener(view -> profileMenuClicked());
        // Add click listener to medal list
        binding.medalsList.setOnItemClickListener(createActiveDialog());
    }

    @Override
    void enableInput(boolean enabled) {
        super.enableInput(enabled);
        binding.profileMenuButton.setEnabled(true);
    }

    void profileMenuClicked(){
        PopupMessage.showPopupMenu(R.menu.profile_menu, binding.profileMenuButton, menuItem -> {
            // Called when an item is selected
            switch (menuItem.getItemId()){
                case R.id.profile_logout:
                    _ctrl.logoutButtonClick();
                    break;
                case R.id.profile_edit:
                    _ctrl.editButtonClick();
                    break;
                case R.id.profile_delete_account:
                    createDeleteAccountDialog();
                    break;
                case R.id.profile_translate:
                    createTranslateDialog();
                    break;
                default:
                    break;
            }
            return true;
        });
    }

    private void createTranslateDialog() {
        AlertDialog.Builder idiomBuilder = new AlertDialog.Builder(requireContext());

        final View idiomPopupView = getLayoutInflater().inflate(R.layout.change_idiom, null);

        Button englishButton = idiomPopupView.findViewById(R.id.englishButton);
        Button spanishButton = idiomPopupView.findViewById(R.id.spanishButton);
        Button catalanButton = idiomPopupView.findViewById(R.id.catalanButton);

        idiomBuilder.setView(idiomPopupView);
        AlertDialog idiom = idiomBuilder.create();
        idiom.show();

        englishButton.setOnClickListener(view -> {
           _ctrl.changeIdiom("english");
        });
        spanishButton.setOnClickListener(view -> {
            _ctrl.changeIdiom("spanish");
        });
        catalanButton.setOnClickListener(view -> {
            _ctrl.changeIdiom("catalan");
        });

    }

    public void createDeleteAccountDialog() {
        AlertDialog.Builder deleterBuilder = new AlertDialog.Builder(requireContext());

        final View deleterPopupView = getLayoutInflater().inflate(R.layout.delete_account, null);

        Button deleteButton = deleterPopupView.findViewById(R.id.deleteAccountButton);
        Button cancelButton = deleterPopupView.findViewById(R.id.deleteAccountCancel);

        deleterBuilder.setView(deleterPopupView);
        AlertDialog deleter = deleterBuilder.create();
        deleter.show();

        deleteButton.setOnClickListener(view -> {
            TextView confirmation = deleterPopupView.findViewById(R.id.deleteAccountConfirmation);
            if (!confirmation.getText().toString().equals("I ACCEPT")) {
                PopupMessage.warning(this, "You must type exactly 'I ACCEPT' (in uppercase)");
                return;
            }
            _ctrl.deleteAccount();
            deleter.dismiss();
        });

        cancelButton.setOnClickListener(view -> {
            deleter.dismiss();
        });
    }

    private AdapterView.OnItemClickListener createActiveDialog() {

        return (parent, view, position, id) -> {
            final AlertDialog.Builder medalBuilder = new AlertDialog.Builder(requireContext());
            final View medalPopupView = getLayoutInflater().inflate(R.layout.set_active_medal, null);
            medalBuilder.setView(medalPopupView);
            final AlertDialog review = medalBuilder.create();
            review.show();

            final Button yes_option = medalPopupView.findViewById(R.id.yesChangeActiveMedal);
            final Button no_option = medalPopupView.findViewById(R.id.noChangeActiveMedal);

            no_option.setOnClickListener(View -> review.dismiss());
            yes_option.setOnClickListener(View -> {
                ProfileService.Medal m = (ProfileService.Medal) parent.getItemAtPosition(position);
                ExecutionThread.nonUI(() -> {
                    _ctrl.changeActiveMedal(m.idmedal);
                    ExecutionThread.UI(this, ()-> {
                        binding.idMedalText.setText(MedalUtils.medalName(this, m.idmedal));
                        binding.medalActiveImage.setImageDrawable(MedalUtils.medalIcon(this, m.idmedal));
                    });
                });
                review.dismiss();

            });

        };
    }
}
