package net.project.studyez.flashCard;

import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import net.project.studyez.cards.Card;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class FlashCardPagerAdapter extends FragmentStatePagerAdapter {

    public static final String CARD = "CARD";
    private static final float WIDTH_SCALE = 0.95f;

    private final int count;
    private final List<Card> cardList;

    private final long baseId = 0;

    public FlashCardPagerAdapter(FragmentManager fragmentManager, List<Card> cards) {
        super(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.cardList = cards;
        this.count = cards.size();
    }

    /**
     * Returns the fragment of the card in the ViewPager
     * @param position
     * @return
     */
    @NotNull
    @Override
    public Fragment getItem(int position) {
        FlashCardContainerFragment flashCardContainerFragment = new FlashCardContainerFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(CARD, cardList.get(position));
        flashCardContainerFragment.setArguments(bundle);
        Log.e("(1)AdapterCard_position", String.valueOf(cardList.size()));

        bundle = flashCardContainerFragment.getArguments();
        assert bundle != null;
        Card updatedCard = bundle.getParcelable(CARD);
        cardList.set(position, updatedCard);

        Log.e("(3)CardtoString", updatedCard.toString());

        return flashCardContainerFragment;
    }

    void deleteCurrentPage(int position) {
//        if (canDelete()) {
//            cardList.remove(position);
//            notifyDataSetChanged();
//        }
    }

    boolean canDelete() {
        return cardList.size() > 0;
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
        for (Card card : cardList) {
            if (card.isStarred()) {
                starredCards.add(card);
            }
        }
        return starredCards;
    }

    public boolean isShowingStarred() {
        ArrayList<Card> starredCards = this.getStarredCards();
        return starredCards.size() == cardList.size();
    }
}