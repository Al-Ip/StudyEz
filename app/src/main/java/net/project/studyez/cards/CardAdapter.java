package net.project.studyez.cards;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import net.project.studyez.R;
import net.project.studyez.decks.DecksFragment;

public class CardAdapter extends FirestoreRecyclerAdapter<Card, CardAdapter.CardHolder> {

    String cardID;
    public CardPresenter presenter;

    public CardAdapter(@NonNull FirestoreRecyclerOptions<Card> options, CardPresenter presenter) {
        super(options);
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public CardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_list, parent, false);
        return new CardHolder(v);
    }

    @Override
    protected void onBindViewHolder(@NonNull CardHolder cardHolder, int i, @NonNull Card card) {
        cardHolder.frontText.setText(card.getQuestion());
        cardHolder.backText.setText(card.getAnswer());
        cardHolder.deleteCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardID = getSnapshots().getSnapshot(i).getId();
                presenter.clickRemoveImage(v.getRootView());
                //Log.e("Bind: ", cardID);
            }
        });

    }

    static class CardHolder extends RecyclerView.ViewHolder {

        TextView frontText;
        TextView backText;
        ImageButton deleteCardButton;
        ImageButton editCardButton;

        public CardHolder(@NonNull View itemView) {
            super(itemView);
            frontText = (TextView) itemView.findViewById(R.id.cardFrontText);
            backText = (TextView) itemView.findViewById(R.id.cardBackText);
            deleteCardButton = (ImageButton) itemView.findViewById(R.id.deleteCardButton);
            editCardButton = (ImageButton) itemView.findViewById(R.id.editCardButton);
        }
    }
}