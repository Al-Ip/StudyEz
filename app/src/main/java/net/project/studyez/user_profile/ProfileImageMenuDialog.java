package net.project.studyez.user_profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import net.project.studyez.R;

import org.jetbrains.annotations.NotNull;

public class ProfileImageMenuDialog extends BottomSheetDialogFragment {

    TextView files, camera, delete;
    UserProfilePresenter userProfilePresenter;

    public static ProfileImageMenuDialog newInstance() {
        return new ProfileImageMenuDialog();
    }

    @NotNull
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.custom_bottomsheetdialog_profile, container, false);

        userProfilePresenter = new UserProfilePresenter((UserProfileFragment)ProfileImageMenuDialog.this.getParentFragment());

        files = view.findViewById(R.id.filesTextView);
        camera = view.findViewById(R.id.cameraTextView);
        delete = view.findViewById(R.id.deleteTextView);

        files.setOnClickListener(v ->  userProfilePresenter.menuClickFiles());
        camera.setOnClickListener(v -> userProfilePresenter.menuClickCamera());
        delete.setOnClickListener(v -> userProfilePresenter.menuClickDelete());

        return view;
    }

}