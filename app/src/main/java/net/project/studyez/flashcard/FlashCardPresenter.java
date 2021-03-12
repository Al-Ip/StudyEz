package net.project.studyez.flashcard;

import androidx.fragment.app.Fragment;

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
    public void finishedCards(Fragment fragment, int id) {
        mView.changeFragment(fragment, id);
    }


    @Override
    public void onGetSuccess(QuerySnapshot querySnapshot) {
        mView.initFlashCards(querySnapshot.toObjects(Card.class));
    }

    @Override
    public void onGetFailure(String message) {

    }
}
