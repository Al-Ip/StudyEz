package net.project.studyez.flashCard;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import net.project.studyez.R;
import net.project.studyez.cards.Card;
import net.project.studyez.main.MainActivity;

import java.util.ArrayList;
import java.util.List;

import static net.project.studyez.dashboard.quickStudy.QuickStudyFragment.deckName;

public class FlashCardViewerFragment extends Fragment implements FlashCardContract.view {

    private FlashCardPresenter flashCardPresenter;
    private ViewPager pager;
    private FlashCardPagerAdapter pagerAdapter;
    private SeekBar deckSeekBar;
    private Toolbar toolbar;
    public List<Card> cardList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_flashcard_viewer, container, false);

        pager = root.findViewById(R.id.pager);
        toolbar = root.findViewById(R.id.flashcardToolbar);

        initToolbar();

        cardList = new ArrayList<>();
        flashCardPresenter = new FlashCardPresenter(this);
        flashCardPresenter.getCardsFromDeck();

        // Creating the seek bar
        deckSeekBar = root.findViewById(R.id.deckSeekBar);
        deckSeekBar.setMax(cardList.size()-1);

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
                if(pager.getCurrentItem() == cardList.size()){
                    Toast.makeText(getContext(), "Finished all the cards in deck", Toast.LENGTH_SHORT).show();
                }
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

    private void initToolbar(){
        ((MainActivity)getActivity()).getSupportActionBar().hide();
        toolbar.setTitle(deckName);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setTitleTextColor(color(R.color.white));
        toolbar.setNavigationOnClickListener(v -> getActivity().onBackPressed());
    }

    @ColorInt
    public int color(@ColorRes int res){
        return ContextCompat.getColor(getContext(), res);
    }

    @Override
    public void displayFlashCards(List list) {
        cardList = list;
        pagerAdapter = new FlashCardPagerAdapter(getFragmentManager(), cardList);
        pager.setAdapter(pagerAdapter);
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
            pagerAdapter = new FlashCardPagerAdapter(getFragmentManager(), cardList);
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
        for (Card card : cardList) {
            String oldFront = card.getQuestion();
            card.setQuestion(card.getAnswer());
            card.setAnswer(oldFront);
        }
        pagerAdapter = new FlashCardPagerAdapter(getFragmentManager(), cardList);
        pager.setAdapter(pagerAdapter);
    }

    @Override
    public void changeFragment(Fragment fragment, int id) {

    }

}