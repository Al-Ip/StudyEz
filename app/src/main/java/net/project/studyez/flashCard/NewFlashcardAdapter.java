package net.project.studyez.flashCard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wajahatkarim3.easyflipview.EasyFlipView;

import net.project.studyez.R;
import net.project.studyez.cards.Card;

import java.util.List;

public class NewFlashcardAdapter extends RecyclerView.Adapter<NewFlashcardAdapter.FlashcardViewHolder> {

    private List<Card> cards;
    private final int count;

    public NewFlashcardAdapter(List<Card> cards) {
        this.cards = cards;
        count = cards.size();
    }

    @NonNull
    @Override
    public FlashcardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.flashcard_flip_view, parent, false);
        return new FlashcardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FlashcardViewHolder holder, int position) {
        holder.txt_front.setText(cards.get(position).getQuestion());
        holder.txt_back.setText(cards.get(position).getAnswer());

        holder.frontCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.easyFlipView.flipTheView();
            }
        });
        holder.backCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.easyFlipView.flipTheView();
            }
        });
    }

    @Override
    public int getItemCount() {
        return count;
    }

    public List<Card> getItems() {
        return cards;
    }

    public void setItems(List<Card> items) {
        this.cards = items;
    }

    public static class FlashcardViewHolder extends RecyclerView.ViewHolder  {
        ImageButton star, speak;
        TextView txt_front, txt_back;
        EasyFlipView easyFlipView;
        FrameLayout frontCard, backCard;

        FlashcardViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_front = itemView.findViewById(R.id.cardFrontText);
            txt_back = itemView.findViewById(R.id.cardBackText);
            star = itemView.findViewById(R.id.starButton);
            speak = itemView.findViewById(R.id.speakButton);
            easyFlipView = itemView.findViewById(R.id.flashcard_easyflip);
            frontCard = itemView.findViewById(R.id.flashcard_front_framelayout);
            backCard = itemView.findViewById(R.id.flashcard_back_framelayout);
        }
    }
}