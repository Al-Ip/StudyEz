package net.project.studyez.cards;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import net.project.studyez.R;

import org.jetbrains.annotations.NotNull;

public class EditCardDialog extends DialogFragment {

    private final String getTextQuestion;
    private final String getTextAnswer;
    private EditText editTextQuestion, editTextAnswer;

    public EditCardDialog(String getTextQuestion, String getTextAnswer){
        this.getTextAnswer = getTextAnswer;
        this.getTextQuestion = getTextQuestion;
    }

    @NotNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_new_card, null);

        editTextQuestion = view.findViewById(R.id.frontEditText);
        editTextAnswer = view.findViewById(R.id.backEditText);
        editTextQuestion.setText(getTextQuestion);
        editTextAnswer.setText(getTextAnswer);

        builder.setTitle("Edit Card");
        builder.setIcon(R.drawable.ic_baseline_edit_24);
        builder.setCancelable(false);
        builder.setPositiveButton("Update", (dialog, which) -> {
            if (!editTextQuestion.getText().toString().isEmpty() && !editTextAnswer.getText().toString().isEmpty()) {
                ((CardsFragment)getParentFragment()).frontAndBackOfCardText(editTextQuestion.getText().toString(), editTextAnswer.getText().toString());
                dialog.dismiss();
            } else {
                Toast.makeText(view.getContext(), "Please enter both a front and back for the card.", Toast.LENGTH_LONG).show();
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dismiss());
        builder.setView(view);
        return builder.create();
    }

}
