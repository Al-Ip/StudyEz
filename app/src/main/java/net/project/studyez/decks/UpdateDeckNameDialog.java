package net.project.studyez.decks;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import net.project.studyez.R;

import org.jetbrains.annotations.NotNull;

public class UpdateDeckNameDialog extends DialogFragment {

    String deckID;
    String deckName;
    private EditText editTextDeckName;

    public UpdateDeckNameDialog(String deckID, String deckName){
        this.deckID = deckID;
        this.deckName = deckName;
    }

    @NotNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_update_deck_name, null);

        editTextDeckName = view.findViewById(R.id.deckNameEditText);
        editTextDeckName.setText(deckName);

        builder.setTitle("Edit Deck Name");
        builder.setIcon(R.drawable.ic_baseline_edit_24);
        builder.setCancelable(false);
        builder.setPositiveButton("Update", (dialog, which) -> {
            if (!editTextDeckName.getText().toString().isEmpty()) {
                ((DecksFragment)getParentFragment()).updateDeckNameDialogConfirm(deckID, editTextDeckName.getText().toString());
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
