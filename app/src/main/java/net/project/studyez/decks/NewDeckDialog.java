package net.project.studyez.decks;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import net.project.studyez.R;

public class NewDeckDialog extends DialogFragment implements DeckContract.view  {

    private DeckPresenter deckPresenter;
    EditText editText;

    public NewDeckDialog(){
        deckPresenter = new DeckPresenter(this);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.custom_dialog_create_deck, null);
        builder.setTitle("Create new Deck");
        builder.setIcon(R.drawable.add_deck);
        builder.setCancelable(false);
        builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText editText = (EditText) getDialog().findViewById(R.id.newDeckNameEditTextField);
                ((DecksFragment)getParentFragment()).getDeckNameFromDialog(editText.getText().toString());
                //deckPresenter.enterDeckName(getActivity(), editText.getText().toString());
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.setView(view);
        return builder.create();
    }

    @Override
    public void displayAllDecks() {

    }

    @Override
    public void displayCreateDeckPopupWindow() {

    }

    @Override
    public void onDeckCreationSuccess(String message) {

    }


    @Override
    public void onDeckCreationFailure(String message) {

    }
}
