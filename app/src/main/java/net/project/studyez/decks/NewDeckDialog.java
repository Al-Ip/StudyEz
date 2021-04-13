package net.project.studyez.decks;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import net.project.studyez.R;

import java.util.Calendar;

public class NewDeckDialog extends DialogFragment {

    private EditText deckName;
    private String dateTime;
    private String creator;
    private int numCards;

    public NewDeckDialog(){

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.custom_dialog_create_deck, null);
        builder.setTitle("Create new Card");
        builder.setIcon(R.drawable.add_deck);
        builder.setCancelable(false);
        builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deckName = view.findViewById(R.id.newDeckNameEditTextField);
                dateTime = Calendar.getInstance().getTime().toString();
                creator = "placeholder";
                numCards = 0;
                ((DecksFragment)getParentFragment()).getDetailsFromDeckDialog(deckName.getText().toString(), dateTime, creator, numCards);
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.setView(view);
        return builder.create();
    }

}
