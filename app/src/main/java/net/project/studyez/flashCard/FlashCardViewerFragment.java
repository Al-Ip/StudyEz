package net.project.studyez.flashCard;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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
import java.util.List;

public class FlashCardViewerFragment extends Fragment implements FlashCardContract.view {

    private FlashCardPresenter flashCardPresenter;
    private ViewPager pager;
    private FlashCardPagerAdapter pagerAdapter;
    private SeekBar deckSeekBar;
    public List<Card> testCardList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_flashcard_viewer, container, false);

        testCardList = new ArrayList<>();
        pager = root.findViewById(R.id.pager);

        flashCardPresenter = new FlashCardPresenter(this);
        flashCardPresenter.getCardsFromDeck();

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

    @Override
    public void displayFlashCards(List list) {
        testCardList = list;
        pagerAdapter = new FlashCardPagerAdapter(getFragmentManager(), testCardList);
        pager.setAdapter(pagerAdapter);
        Log.e("View", String.valueOf(testCardList.size()));
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
            pagerAdapter = new FlashCardPagerAdapter(getFragmentManager(), testCardList);
            pager.setAdapter(pagerAdapter);
        } else {
            ArrayList<Card> starredCards = pagerAdapter.getStarredCards();
            if (starredCards.size() > 0) {
                pager.setAdapter(null);
                pagerAdapter = new FlashCardPagerAdapter(getFragmentManager(), starredCards);
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
        pagerAdapter = new FlashCardPagerAdapter(getFragmentManager(), testCardList);
        pager.setAdapter(pagerAdapter);
    }

    @Override
    public void changeFragment(Fragment fragment, int id) {

    }

}