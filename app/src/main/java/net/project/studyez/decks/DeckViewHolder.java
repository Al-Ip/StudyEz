package net.project.studyez.decks;

import android.content.Context;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import net.project.studyez.R;

import java.util.HashMap;

import static net.project.studyez.decks.DecksFragment.USERS;
import static net.project.studyez.decks.DecksFragment.VIBRATE_TIME;

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