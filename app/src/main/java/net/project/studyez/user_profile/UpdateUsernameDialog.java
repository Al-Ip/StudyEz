package net.project.studyez.user_profile;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import net.project.studyez.R;

import org.jetbrains.annotations.NotNull;

public class UpdateUsernameDialog extends DialogFragment {

    String description;
    private EditText editTextUsername;

    public UpdateUsernameDialog(String description){
        this.description = description;
    }

    @NotNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_update_username, null);

        editTextUsername = view.findViewById(R.id.descriptionEditText);
        editTextUsername.setText(description);

        builder.setTitle("Edit Description about Yourself");
        builder.setIcon(R.drawable.ic_baseline_edit_24);
        builder.setCancelable(false);
        builder.setPositiveButton("Update", (dialog, which) -> {
            if (!editTextUsername.getText().toString().isEmpty()) {
                ((UserProfileFragment)getParentFragment()).updateUsernameDialogConfirm(editTextUsername.getText().toString());
                dialog.dismiss();
            } else {
                Toast.makeText(view.getContext(), "Please Enter a New Deck Name", Toast.LENGTH_LONG).show();
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dismiss());
        builder.setView(view);
        return builder.create();
    }

}
