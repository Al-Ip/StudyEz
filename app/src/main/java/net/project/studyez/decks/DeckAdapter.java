package net.project.studyez.decks;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import net.project.studyez.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class DeckAdapter extends FirestoreRecyclerAdapter<Deck, DeckAdapter.DeckHolder> {

    private final FirestoreRecyclerOptions<Deck> options;

    public DeckAdapter(@NonNull FirestoreRecyclerOptions<Deck> options) {
        super(options);
        this.options = options;
    }

    @Override
    protected void onBindViewHolder(@NonNull DeckHolder deckHolder, int i, @NonNull Deck deck) {
        Uri imageUriParse = Uri.parse(deck.getImage());
        deckHolder.deckName.setText(deck.getName());
        deckHolder.creatorText.setText(deck.getCreator());
        deckHolder.deckSize.setText(" #" + deck.getNumCards() + " Cards");
        deckHolder.userImage.setImageURI(imageUriParse);
    }

    @NonNull
    @Override
    public DeckHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.deck_list, parent, false);
        return new DeckHolder(view);
    }

    @Override
    public int getItemCount()
    {
        final int count = options.getSnapshots().size();
        return count;
    }

    static class DeckHolder extends RecyclerView.ViewHolder{

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
