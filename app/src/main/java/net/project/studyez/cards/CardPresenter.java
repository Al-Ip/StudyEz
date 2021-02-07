package net.project.studyez.cards;

import android.view.View;

import net.project.studyez.decks.DeckContract;
import net.project.studyez.decks.DeckInteractor;


public class CardPresenter implements CardContract.presenter {

    // to keep reference to view
    private final CardContract.view mView;

    public CardPresenter(CardContract.view view){
        mView = view;
    }

    @Override
    public void clickFab(View view) {
        mView.displayCreateCardPopupWindow();
    }
}
