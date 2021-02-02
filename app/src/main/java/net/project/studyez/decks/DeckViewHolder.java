package net.project.studyez.decks;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import net.project.studyez.R;

public class DeckViewHolder extends RecyclerView.ViewHolder {
    View view;
    TextView deckName;
    TextView deckSize;
    TextView creatorText;
    TextView favoriteText;
    Button renameButton;
    Button editButton;
    Button deleteButton;
    Button createButton;
    Button cancelButton;
    EditText nameEditText;
    ImageView userImage;
    ImageButton favoriteButton;


    public DeckViewHolder(View itemView) {
        super(itemView);
        view = itemView;
        deckName = itemView.findViewById(R.id.deckName);
        deckSize = itemView.findViewById(R.id.deckSize);
        creatorText = itemView.findViewById(R.id.creatorText);
        favoriteText = itemView.findViewById(R.id.favoriteText);
        userImage = itemView.findViewById(R.id.userImage);
        favoriteButton = itemView.findViewById(R.id.favoriteButton);
    }



}