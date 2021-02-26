package net.project.studyez.dashboard;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import net.project.studyez.R;
import net.project.studyez.cards.Card;

import java.util.ArrayList;

public class DeckViewerFragment extends Fragment {

    private ViewPager pager;
    private CardPagerAdapter pagerAdapter;
    private SeekBar deckSeekBar;
    public ArrayList<Card> testCardList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.activity_deck_viewer, container, false);

        //testing
        testCardList = new ArrayList<>();
        Card c1 = new Card("What is my favourite ice cream flavour?", "Mint");
        Card c2 = new Card("Is the sky blue?", "Yes");
        Card c3 = new Card("What is my dogs name?", "Bella");
        Card c4 = new Card("What is 2 + 2?", "4");
        Card c5 = new Card("Capital of Romania?", "Bucharest");
        testCardList.add(c1);
        testCardList.add(c2);
        testCardList.add(c3);
        testCardList.add(c4);
        testCardList.add(c5);


        // Creating the ViewPager with the CardAdapter
        pager = root.findViewById(R.id.pager);
        pagerAdapter = new CardPagerAdapter(getFragmentManager(), testCardList);
        pager.setAdapter(pagerAdapter);

        // Creating the seek bar
        deckSeekBar = root.findViewById(R.id.deckSeekBar);
        deckSeekBar.setMax(testCardList.size()-1);

        // Change the seek bar progress when cards are swiped
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                setDeckSeekBar();
            }
        });

        // Change the current card when the seek bar is dragged
        deckSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                pager.setCurrentItem(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    // Change the current card when the seek bar is dragged
        deckSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            pager.setCurrentItem(i);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    });
        return root;
    }


    /**
     * Sets the seek bar correctly to the fill out how far the user is into the deck.
     */
    private void setDeckSeekBar() {
        deckSeekBar.setMax(pagerAdapter.getCount() - 1);
        // Allows the progress bar to animate for API 24 and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            deckSeekBar.setProgress(pager.getCurrentItem(), true);
        } else {
            deckSeekBar.setProgress(pager.getCurrentItem());
        }
    }

    /**
     * Resets the ViewPager to only display the starred cards
     */
    private void toggleStarred() {
        if (pagerAdapter.isShowingStarred()) {
            pager.setAdapter(null);
            pagerAdapter = new CardPagerAdapter(getFragmentManager(), testCardList);
            pager.setAdapter(pagerAdapter);
        } else {
            ArrayList<Card> starredCards = pagerAdapter.getStarredCards();
            if (starredCards.size() > 0) {
                pager.setAdapter(null);
                pagerAdapter = new CardPagerAdapter(getFragmentManager(), starredCards);
                pager.setAdapter(pagerAdapter);
            } else {
                Toast.makeText(getContext(), "There are no starred cards.", Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * Resets the ViewPager to display the backs of cards first
     */
    private void toggleBackside() {
        pager.setAdapter(null);
        for (Card card : testCardList) {
            String oldFront = card.getQuestion();
            card.setQuestion(card.getAnswer());
            card.setAnswer(oldFront);
        }
        pagerAdapter = new CardPagerAdapter(getFragmentManager(), testCardList);
        pager.setAdapter(pagerAdapter);
    }
}