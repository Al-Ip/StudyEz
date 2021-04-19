package net.project.studyez.decks;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import net.project.studyez.R;

import de.hdodenhof.circleimageview.CircleImageView;


public class DeckAdapter extends FirestoreRecyclerAdapter<Deck, DeckAdapter.DeckHolder> {

    private final FirestoreRecyclerOptions<Deck> options;
    private final FirebaseUser fUser;

    public DeckAdapter(@NonNull FirestoreRecyclerOptions<Deck> options) {
        super(options);
        this.options = options;
        fUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    @Override
    protected void onBindViewHolder(@NonNull DeckHolder deckHolder, int i, @NonNull Deck deck) {
        if (fUser.getPhotoUrl() != null) {
            deckHolder.deckName.setText(deck.getDeckName());
            deckHolder.creatorText.setText(deck.getCreator());
            deckHolder.deckSize.setText(" #" + deck.getNumCards() + " Cards");
            deckHolder.userImage.setImageURI(fUser.getPhotoUrl());
        } else {
            deckHolder.deckName.setText(deck.getDeckName());
            deckHolder.creatorText.setText(deck.getCreator());
            deckHolder.deckSize.setText(" #" + deck.getNumCards() + " Cards");
        }
    }

    @NonNull
    @Override
    public DeckHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.deck_list, parent, false);
        return new DeckHolder(view);
    }

    @Override
    public int getItemCount() {
        final int count = options.getSnapshots().size();
        return count;
    }

    static class DeckHolder extends RecyclerView.ViewHolder {

        View view;
        TextView deckName;
        TextView deckSize;
        TextView creatorText;
        TextView favoriteText;
        CircleImageView userImage;
        ImageButton favoriteButton;

        public DeckHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            deckName = itemView.findViewById(R.id.deckName);
            deckSize = itemView.findViewById(R.id.deckSize);
            creatorText = itemView.findViewById(R.id.creatorText);
            favoriteText = itemView.findViewById(R.id.favoriteText);
            userImage = itemView.findViewById(R.id.deckUserProfileImage);
            favoriteButton = itemView.findViewById(R.id.favoriteButton);
        }
    }
}
