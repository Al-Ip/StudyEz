package net.project.studyez.flashCard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import net.project.studyez.R;
import net.project.studyez.cards.Card;

import java.util.List;

public class NewFlashcardAdapter extends RecyclerView.Adapter<NewFlashcardAdapter.FlashcardViewHolder> {

    private List<Card> cards;

    public NewFlashcardAdapter(List<Card> cards) {
        this.cards = cards;
    }

    @NonNull
    @Override
    public FlashcardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_card, parent, false);
        return new FlashcardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FlashcardViewHolder holder, int position) {
        holder.setData(cards.get(position));
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    public List<Card> getItems() {
        return cards;
    }

    public void setItems(List<Card> items) {
        this.cards = items;
    }

    static class FlashcardViewHolder extends RecyclerView.ViewHolder{
        public boolean cardFlipped;
        CardView cardView;
        ImageButton star, speak;
        TextView cardText;

        FlashcardViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.flashcard_cardview);
            star = itemView.findViewById(R.id.starButton);
            speak = itemView.findViewById(R.id.speakButton);
            cardText = itemView.findViewById(R.id.cardText);
        }

        void setData(Card data) {
            cardText.setText(data.getQuestion());
            cardFlipped = false;
        }

//        public void flipCard() {
//            if (cardFlipped) {
//                cardView.setAnimation(
//                        R.anim.card_flip_left_in,
//                        R.anim.card_flip_left_out,
//                        R.anim.card_flip_right_in,
//                        R.anim.card_flip_right_out);
//            } else {
//                fragment = backFlashCardFragment;
//            }
//
//            getChildFragmentManager()
//                    .beginTransaction()
//                    .setCustomAnimations(
//                            R.anim.card_flip_left_in,
//                            R.anim.card_flip_left_out,
//                            R.anim.card_flip_right_in,
//                            R.anim.card_flip_right_out)
//                    .replace(R.id.flashcard_Frame, fragment)
//                    .commit();
//
//            cardFlipped = !cardFlipped;
//        }

    }
}