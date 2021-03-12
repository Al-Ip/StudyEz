package net.project.studyez.decks;

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

public class MenuDeckDialog extends BottomSheetDialogFragment {

    TextView update, makePublic, delete;

    public static MenuDeckDialog newInstance() {
        return new MenuDeckDialog();
    }

    @NotNull
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.custom_dialog_menu_deck, container, false);

        update = view.findViewById(R.id.updateTextView);
        makePublic = view.findViewById(R.id.makePublicTextView);
        delete = view.findViewById(R.id.deleteTextView);

        update.setOnClickListener(v -> Toast.makeText(getContext(), "Update Name Feature Coming Soon!", Toast.LENGTH_SHORT).show());
        makePublic.setOnClickListener(v -> Toast.makeText(getContext(), "Public Feature Coming Soon!", Toast.LENGTH_SHORT).show());
        delete.setOnClickListener(v -> {
            dismiss();
            assert getParentFragment() != null;
            ((DecksFragment)getParentFragment()).deleteDeckDialogConfirm();
        });

        return view;
    }

}