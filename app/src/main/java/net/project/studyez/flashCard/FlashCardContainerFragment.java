package net.project.studyez.flashCard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import net.project.studyez.R;

import java.util.Calendar;

public class FlashCardContainerFragment extends Fragment {

    public static final String FLIPPED = "FLIPPED";
    public static final float DISTANCE = 17000;

    private FlashCardFragment frontFlashCardFragment;
    private FlashCardFragment backFlashCardFragment;
    private boolean cardFlipped;

    private static final int MAX_CLICK_DURATION = 200;
    private long startClickTime;

    public FlashCardContainerFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Create the card fragment view
        final View rootView = inflater.inflate(R.layout.fragment_container_flashcard, container, false);

        this.cardFlipped = false;
        Bundle bundle = this.getArguments();

        // Make the front and back cards
        Bundle frontBundle = (Bundle) bundle.clone();
        Bundle backBundle = (Bundle) bundle.clone();

        frontFlashCardFragment = new FlashCardFragment();
        frontBundle.putByte(FLIPPED, (byte) 0);
        frontFlashCardFragment.setArguments(frontBundle);

        backFlashCardFragment = new FlashCardFragment();
        backBundle.putByte(FLIPPED, (byte) 1);
        backFlashCardFragment.setArguments(backBundle);

        // Set the first viewed fragment to be the the front of the card
        getChildFragmentManager()
                .beginTransaction()
                .add(R.id.flashcard_Frame, frontFlashCardFragment)
                .commit();

        // Flip the card once touched
        rootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        startClickTime = Calendar.getInstance().getTimeInMillis();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        long clickDuration = Calendar.getInstance().getTimeInMillis() - startClickTime;
                        if(clickDuration < MAX_CLICK_DURATION) {
                            //flipCard();
                        }
                    }
                }
                return true;
            }
        });
//        rootView.getRootView().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                flipCard();
//            }
//        });

        return rootView;
    }

    /**
     * Swaps the visible fragment with the other fragment (the other side of the card)
     * Animates the transition between the change with a flip animation.
     */
//    public void flipCard() {
//        Fragment fragment;
//        if (cardFlipped) {
//            fragment = frontFlashCardFragment;
//        } else {
//            fragment = backFlashCardFragment;
//        }
//
//        getChildFragmentManager()
//                .beginTransaction()
//                .setCustomAnimations(
//                        R.anim.card_flip_left_in,
//                        R.anim.card_flip_left_out,
//                        R.anim.card_flip_right_in,
//                        R.anim.card_flip_right_out)
//                .replace(R.id.flashcard_Frame, fragment)
//                .commit();
//
//        cardFlipped = !cardFlipped;
//    }
}