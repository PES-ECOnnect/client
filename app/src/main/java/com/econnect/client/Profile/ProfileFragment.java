package com.econnect.client.Profile;

import static com.econnect.API.ProfileService.*;

import android.app.AlertDialog;
import android.graphics.drawable.Drawable;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.econnect.Utilities.CustomFragment;
import com.econnect.client.Products.ProductListAdapter;
import com.econnect.client.R;
import com.econnect.client.databinding.FragmentProfileBinding;


public class ProfileFragment extends CustomFragment<FragmentProfileBinding> implements PopupMenu.OnMenuItemClickListener {
    
    private final ProfileController ctrl = new ProfileController(this);
    private MedalListAdapter medals_adapter;

    private AlertDialog.Builder deleterBuilder;
    private AlertDialog deleter;
    private TextView passwordDelete, acceptDelete, username, email, id_medal;
    private Button deleteButton, cancelButton;

    public ProfileFragment() {
        super(FragmentProfileBinding.class);
    }

    @Override
    protected void addListeners() {
        binding.profileMenuButton.setOnClickListener( view -> showProfileMenu(view));
        ctrl.getInfoUser();
    }

    void enableInput() {
        binding.profileMenuButton.setEnabled(true);
    }

    void setActiveMedal(User u) {
        View v = this.getView();
        id_medal = v.findViewById(R.id.idMedalText);
        if (u != null) {
            int name = u.activeMedal;
            id_medal.setText(String.valueOf(name));
        }
    }

    void setEmail(User u) {
        View v = this.getView();
        email = v.findViewById(R.id.emailText);
        if (u != null) {
            email.setText(u.email);
        }
    }

    void setUsername(User u) {
        View v = this.getView();
        username = v.findViewById(R.id.usernameText);
        if (u != null) {
            username.setText(u.username);
        }
    }

    void setMedals(User u) {
        /*int highlightColor = ContextCompat.getColor(getContext(), R.color.green);
        Drawable defaultImage = ContextCompat.getDrawable(getContext(), R.drawable.ic_medal_24);
        medals_adapter = new MedalListAdapter(this, highlightColor, defaultImage, u.medals);
        binding.medalsList.setAdapter(medals_adapter);
        binding.medalsList.refreshDrawableState();*/
    }


    void showProfileMenu(View v){
        PopupMenu popupMenu = new PopupMenu(this.getContext(), v);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.profile_menu);
        popupMenu.show();

    }
    public void createReviewDialog() {
        deleterBuilder = new AlertDialog.Builder(getContext());

        final View deleterPopupView = getLayoutInflater().inflate(R.layout.delete_account, null);

        deleteButton = deleterPopupView.findViewById(R.id.deleteAccountButton);
        cancelButton = deleterPopupView.findViewById(R.id.deleteAccountCancel);

        passwordDelete = deleterPopupView.findViewById(R.id.deleteAccountPassword);
        acceptDelete = deleterPopupView.findViewById(R.id.deleteAccountText);

        deleterBuilder.setView(deleterPopupView);
        deleter = deleterBuilder.create();
        deleter.show();

        deleteButton.setOnClickListener(view -> {
            deleter.dismiss();
        });

        cancelButton.setOnClickListener(view -> {
            deleter.dismiss();
        });
    }

        @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.profile_logout:
                ctrl.logoutButtonClick();
                break;
            case R.id.profile_edit:
                ctrl.editButtonClick();
                break;
            case R.id.profile_delete_account:
                createReviewDialog();
                break;
            default:
                break;
        }
        return true;
    }
}