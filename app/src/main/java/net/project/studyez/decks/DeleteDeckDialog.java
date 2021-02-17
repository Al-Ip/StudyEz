package net.project.studyez.decks;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import net.project.studyez.R;

public class DeleteDeckDialog extends DialogFragment {

    public DeleteDeckDialog(){

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Warning: Deleting Deck!");
        builder.setIcon(R.drawable.ic_baseline_delete_forever_24);
        builder.setMessage("Pressing Delete will erase this deck and all the cards contained within it");
        builder.setCancelable(false);
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                assert getParentFragment() != null;
                ((DecksFragment)getParentFragment()).deleteDeckDialogConfirm();
            }
        });
        builder.setNegativeButton("Cancel", null);
        return builder.create();
    }

}
