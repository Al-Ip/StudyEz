package net.project.studyez.flashCard;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import net.project.studyez.cards.Card;

import java.util.ArrayList;
import java.util.List;

public class FlashCardPagerAdapter extends FragmentStatePagerAdapter {

    public static final String CARD = "CARD";
    private static final float WIDTH_SCALE = 0.95f;

    private final int count;
    private final List<Card> testCardList;

    public FlashCardPagerAdapter(FragmentManager fragmentManager, List<Card> cards) {
        super(fragmentManager);
        this.testCardList = cards;
        this.count = cards.size();
    }

    /**
     * Returns the fragment of the card in the ViewPager
     * @param position
     * @return
     */
    @Override
    public Fragment getItem(int position) {
        FlashCardContainerFragment flashCardContainerFragment = new FlashCardContainerFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(CARD, testCardList.get(position));
        flashCardContainerFragment.setArguments(bundle);

        bundle = flashCardContainerFragment.getArguments();
        Card updatedCard = bundle.getParcelable(CARD);
        testCardList.set(position, updatedCard);

        return flashCardContainerFragment;
    }

    @Override
    public int getItemPosition(Object item) {
        return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return count;
    }

    /**
     * Override the pageWidth in order to be able
     * to see other cards on the side of the current card.
     * @param position The position of the current card
     * @return The multiplier by which to change the width by
     */
    @Override
    public float getPageWidth(int position) {
        return WIDTH_SCALE;
    }

    public ArrayList<Card> getStarredCards() {
        ArrayList<Card> starredCards = new ArrayList<>();
        for (Card card : testCardList) {
            if (card.isStarred()) {
                starredCards.add(card);
            }
        }
        return starredCards;
    }

    public boolean isShowingStarred() {
        ArrayList<Card> starredCards = this.getStarredCards();
        return starredCards.size() == testCardList.size();
    }
}