package net.project.studyez.cards;

import android.app.Activity;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;


public class CardPresenter implements CardContract.presenter, CardContract.onCardCreationListener, CardContract.onCardDeletionListener, CardContract.onCardEditListener{

    // to keep reference to view
    private final CardContract.view mView;
    private final CardContract.Interactor mInteractor;

    public CardPresenter(CardContract.view view, CardContract.Interactor cardInteractor){
        mView = view;
        mInteractor = cardInteractor;
    }

    public CardPresenter(CardContract.view view){
        mView = view;
        mInteractor = new CardInteractor(this, this, this);
    }

    @Override
    public void clickFab(View view) {
        mView.displayCreateCardPopupWindow();
    }

    @Override
    public void toolbarBackArrowPress(Fragment fragment, int id) {
        mView.changeFragment(fragment, id);
    }


    @Override
    public void clickEditImage(View view, String question, String answer) {
        mView.displayEditCardPopupWindow(question, answer);
    }

    @Override
    public void getNumberOfCards(String deckName, int numCards) {
        mInteractor.updateNumberOfCardsFromFirebase(deckName, numCards);
    }

    @Override
    public void clickRemoveImage(View view) {
        mView.displayDeleteCardPopupWindow();
    }

    @Override
    public FirestoreRecyclerOptions getCardsFromDeck(Activity activity, String deckName) {
        return mInteractor.getCardsFromFirebase(activity, deckName);
    }

    @Override
    public void getCardDetails(String deckName, String question, String answer, String dateTimeCreated, boolean isStarred) {
        mInteractor.addNewCardToFirebase(deckName, question, answer, dateTimeCreated, isStarred);
    }

    @Override
    public void editCardDetails(String deckName, String question, String answer, String docID) {
        mInteractor.editCardFromFirebase(deckName, question, answer, docID);
    }

    @Override
    public void deleteCardFromFirebase(String deckName, String docID) {
        mInteractor.deleteCardFromFirebase(deckName, docID);
    }

    @Override
    public void showEmptyCardsMessage() {
        mView.displayEmptyCardsMessage();
    }

    @Override
    public void hideEmptyCardsMessage() {
        mView.hideEmptyCardsMessage();
    }

    @Override
    public void onCreateSuccess(String message) {
        mView.onCardCreationSuccess(message);
    }

    @Override
    public void onCreateFailure(String message) {
        mView.onCardCreationFailure(message);
    }

    @Override
    public void onDeleteSuccess(String message) {
        mView.onCardDeletionSuccess(message);
    }

    @Override
    public void onDeleteFailure(String message) {
        mView.onCardDeletionFailure(message);
    }

    @Override
    public void onEditSuccess(String message) {
        mView.onCardEditSuccess(message);
    }

    @Override
    public void onEditFailure(String message) {
        onEditFailure(message);
    }

}
