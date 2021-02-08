package net.project.studyez.cards;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import net.project.studyez.R;
import net.project.studyez.decks.Deck;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;

import static net.project.studyez.decks.DeckInteractor.deck;

public class NewCardDialog extends DialogFragment {

    private EditText question;
    private EditText answer;
    private String dateTime;
    private boolean isStarred;

    @NotNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_new_card, null);
        builder.setTitle("Create new Deck");
        builder.setIcon(R.drawable.add_deck);
        builder.setCancelable(false);
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                question = getDialog().findViewById(R.id.frontEditText);
                answer = getDialog().findViewById(R.id.backEditText);
                dateTime = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
                isStarred = false;

                if (!question.getText().toString().isEmpty() && !answer.getText().toString().isEmpty()) {
                    ((CardsFragment)getParentFragment()).frontAndBackOfCardText(deck.getName(), question.getText().toString(), answer.getText().toString(), dateTime, isStarred);
                    dialog.dismiss();
                } else {
                    Toast.makeText(view.getContext(), "Please enter both a front and back for the card.", Toast.LENGTH_LONG).show();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });
        builder.setView(view);
        return builder.create();
    }
}
