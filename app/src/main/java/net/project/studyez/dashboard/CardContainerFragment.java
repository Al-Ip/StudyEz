package net.project.studyez.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import net.project.studyez.R;

public class CardContainerFragment extends Fragment {

    public static final String FLIPPED = "FLIPPED";
    public static final float DISTANCE = 17000;

    private CardFragment frontCardFragment;
    private CardFragment backCardFragment;
    private boolean cardFlipped;

    public CardContainerFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Create the card fragment view
        final View rootView = inflater.inflate(R.layout.fragment_card, container, false);

        this.cardFlipped = false;
        Bundle bundle = this.getArguments();

        // Make the front and back cards
        Bundle frontBundle = (Bundle) bundle.clone();
        Bundle backBundle = (Bundle) bundle.clone();

        frontCardFragment = new CardFragment();
        frontBundle.putByte(FLIPPED, (byte) 0);
        frontCardFragment.setArguments(frontBundle);

        backCardFragment = new CardFragment();
        backBundle.putByte(FLIPPED, (byte) 1);
        backCardFragment.setArguments(backBundle);

        // Set the first viewed fragment to be the the front of the card
        getChildFragmentManager()
                .beginTransaction()
                .add(R.id.containerssss, frontCardFragment)
                .commit();

        // Flip the card once touched
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flipCard();
            }
        });

        return rootView;
    }

    /**
     * Swaps the visible fragment with the other fragment (the other side of the card)
     * Animates the transition between the change with a flip animation.
     */
    public void flipCard() {
        Fragment fragment;
        if (cardFlipped) {
            fragment = frontCardFragment;
        } else {
            fragment = backCardFragment;
        }

        getChildFragmentManager()
                .beginTransaction()
                .setCustomAnimations(
                        R.anim.card_flip_left_in,
                        R.anim.card_flip_left_out,
                        R.anim.card_flip_right_in,
                        R.anim.card_flip_right_out)
                .replace(R.id.containerssss, fragment)
                .commit();

        cardFlipped = !cardFlipped;
    }
}