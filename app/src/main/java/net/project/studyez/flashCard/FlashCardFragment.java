package net.project.studyez.flashCard;

import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import net.project.studyez.R;
import net.project.studyez.cards.Card;

import java.util.Locale;

import static net.project.studyez.flashCard.FlashCardContainerFragment.DISTANCE;
import static net.project.studyez.flashCard.FlashCardContainerFragment.FLIPPED;

public class FlashCardFragment extends Fragment {

    public static  TextView cardText;
    private ImageButton starButton;
    private ImageButton speakButton;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        // Create the card fragment view and get the card object
        View view = inflater.inflate(R.layout.flashcard_view, container, false);

        cardText = view.findViewById(R.id.cardText);
        starButton = view.findViewById(R.id.starButton);
        speakButton = view.findViewById(R.id.speakButton);

        final Bundle bundle = this.getArguments();
        assert bundle != null;
        final Card card = bundle.getParcelable(FlashCardPagerAdapter.CARD);

        boolean cardFlipped = bundle.getByte(FLIPPED) != 0;

        // Scroll long text cards
        cardText.setMovementMethod(new ScrollingMovementMethod());

        // Change camera perspective to not have the flip animation be distorted
        float scale = getResources().getDisplayMetrics().density;
        view.setCameraDistance(DISTANCE * scale);

        // Set the text from the card to the fragment's textview
        if (cardFlipped) {
            cardText.setText(card.getAnswer());
        } else {
            cardText.setMovementMethod(null);
            cardText.setText(card.getQuestion());
        }

        // Make the star button and set it based on if the card is starred
        if (card.isStarred()) {
            starButton.setImageResource(R.drawable.ic_star_white_48dp);
        } else {
            starButton.setImageResource(R.drawable.ic_star_border_black_48dp);
        }
        starButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!card.isStarred()) {
                    starButton.setImageResource(R.drawable.ic_star_white_48dp);
                } else {
                    starButton.setImageResource(R.drawable.ic_star_border_black_48dp);
                }
                card.toggleStarred();
            }
        });

        // Makes the TextToSpeech object and sets its language to the current locale
        final TextToSpeech textToSpeech = new TextToSpeech(view.getContext(),
                new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int i) {
                    }
                });
        final Locale currentLocale;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            currentLocale = getResources().getConfiguration().getLocales().get(0);
        } else {
            //noinspection deprecation
            currentLocale = getResources().getConfiguration().locale;
        }
        textToSpeech.setLanguage(currentLocale);

        speakButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                textToSpeech.speak(cardText.getText().toString(),
                        TextToSpeech.QUEUE_FLUSH,
                        null,
                        "Speak");
            }
        });

        return view;
    }

}