package net.project.studyez.flashcard;

import android.content.Context;
import android.os.Build;
import android.speech.tts.TextToSpeech;
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
import java.util.Locale;

public class FlashcardAdapter extends RecyclerView.Adapter<FlashcardAdapter.FlashcardViewHolder> {

    private final Context context;
    private List<Card> cards;
    private final int count;
    private TextToSpeech textToSpeech;

    public FlashcardAdapter(Context context, List<Card> cards) {
        this.context = context;
        this.cards = cards;
        count = cards.size();
        textToSpeech();
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

        holder.frontCard.setOnClickListener(v -> holder.easyFlipView.flipTheView());
        holder.backCard.setOnClickListener(v -> holder.easyFlipView.flipTheView());

        holder.speak.setOnClickListener(v -> { textToSpeech.speak(cards.get(position).getQuestion(), TextToSpeech.QUEUE_FLUSH, null, "Speak"); });

        // Make the star button and set it based on if the card is starred
        if (cards.get(position).isStarred()) {
            holder.favourite.setImageResource(R.drawable.ic_star_white_48dp);
        } else {
            holder.favourite.setImageResource(R.drawable.ic_star_border_black_48dp);
        }
        holder.favourite.setOnClickListener(v -> {
            if (!cards.get(position).isStarred()) {
                holder.favourite.setImageResource(R.drawable.ic_star_white_48dp);
            } else {
                holder.favourite.setImageResource(R.drawable.ic_star_border_black_48dp);
            }
            cards.get(position).toggleStarred();
        });
    }

    public void textToSpeech() {
        // Makes the TextToSpeech object and sets its language to the current locale
        textToSpeech = new TextToSpeech(context, i -> { });
        final Locale currentLocale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            currentLocale = context.getResources().getConfiguration().getLocales().get(0);
        } else {
            //noinspection deprecation
            currentLocale = context.getResources().getConfiguration().locale;
        }
        textToSpeech.setLanguage(currentLocale);

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
        View view;
        ImageButton favourite, speak;
        TextView txt_front, txt_back;
        EasyFlipView easyFlipView;
        FrameLayout frontCard, backCard;

        FlashcardViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            txt_front = itemView.findViewById(R.id.cardFrontText);
            txt_back = itemView.findViewById(R.id.cardBackText);
            favourite = itemView.findViewById(R.id.starButton);
            speak = itemView.findViewById(R.id.speakButton);
            easyFlipView = itemView.findViewById(R.id.flashcard_easyflip);
            frontCard = itemView.findViewById(R.id.flashcard_front_framelayout);
            backCard = itemView.findViewById(R.id.flashcard_back_framelayout);
        }
    }
}