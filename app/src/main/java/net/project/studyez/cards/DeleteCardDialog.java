package net.project.studyez.cards;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import net.project.studyez.R;


public class DeleteCardDialog extends DialogFragment {

    public DeleteCardDialog(){

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Warning: Deleting Card!");
        builder.setIcon(R.drawable.ic_baseline_delete_forever_24);
        builder.setMessage("Pressing Delete will erase this card!");
        builder.setCancelable(false);
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                assert getParentFragment() != null;
                ((CardsFragment)getParentFragment()).deleteCardDialogConfirm();
            }
        });
        builder.setNegativeButton("Cancel", null);
        return builder.create();
    }

}
