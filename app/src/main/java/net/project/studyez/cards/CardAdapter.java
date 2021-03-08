package net.project.studyez.cards;

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

public class CardAdapter extends FirestoreRecyclerAdapter<Card, CardAdapter.CardHolder> {

    private final FirestoreRecyclerOptions<Card> options;
    String cardID;
    CardPresenter presenter;
    String question, answer;

    public CardAdapter(@NonNull FirestoreRecyclerOptions<Card> options, CardPresenter presenter) {
        super(options);
        this.options = options;
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
        cardHolder.deleteCardButton.setOnClickListener(v -> {
            cardID = getSnapshots().getSnapshot(i).getId();
            presenter.clickRemoveImage(v.getRootView());
        });
        cardHolder.editCardButton.setOnClickListener(v -> {
            cardID = getSnapshots().getSnapshot(i).getId();
            question = cardHolder.frontText.getText().toString();
            answer = cardHolder.backText.getText().toString();
            presenter.clickEditImage(v.getRootView(), question, answer);
        });

    }

    @Override
    public int getItemCount()
    {
        final int count = options.getSnapshots().size();
        return count;
    }

    static class CardHolder extends RecyclerView.ViewHolder {

        TextView frontText;
        TextView backText;
        ImageButton deleteCardButton;
        ImageButton editCardButton;

        public CardHolder(@NonNull View itemView) {
            super(itemView);
            frontText = itemView.findViewById(R.id.cardFrontText);
            backText = itemView.findViewById(R.id.cardBackText);
            deleteCardButton = itemView.findViewById(R.id.deleteCardButton);
            editCardButton = itemView.findViewById(R.id.editCardButton);
        }
    }
}