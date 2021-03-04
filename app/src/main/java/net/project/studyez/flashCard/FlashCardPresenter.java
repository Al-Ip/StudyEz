package net.project.studyez.flashCard;

import android.util.Log;

import com.google.firebase.firestore.QuerySnapshot;

import net.project.studyez.cards.Card;

public class FlashCardPresenter implements FlashCardContract.presenter, FlashCardContract.onCardGetListener {

    private final FlashCardContract.Interactor mInteractor;
    private final FlashCardContract.view mView;

    public FlashCardPresenter(FlashCardContract.view view){
        this.mView = view;
        this.mInteractor = new FlashCardInteractor(this);
    }

    @Override
    public void clickFlashCard() {

    }

    @Override
    public void getCardsFromDeck() {
        mInteractor.getCardsToDisplayOnFlashcards();
    }


    @Override
    public void onGetSuccess(QuerySnapshot querySnapshot) {
        Log.e("Presemter", String.valueOf(querySnapshot.size()));
        mView.displayFlashCards(querySnapshot.toObjects(Card.class));
    }

    @Override
    public void onGetFailure(String message) {

    }
}
