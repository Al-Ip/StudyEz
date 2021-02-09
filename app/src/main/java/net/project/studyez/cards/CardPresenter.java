package net.project.studyez.cards;

import android.app.Activity;
import android.view.View;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;


public class CardPresenter implements CardContract.presenter, CardContract.onCardCreationListener, CardContract.onCardDeletionListener {

    // to keep reference to view
    private final CardContract.view mView;
    private final CardContract.Interactor mInteractor;

    public CardPresenter(CardContract.view view){
        mView = view;
        mInteractor = new CardInteractor(this,this);
    }

    @Override
    public void clickFab(View view) {
        mView.displayCreateCardPopupWindow();
    }


    @Override
    public void clickEditImage(View view) {
        mView.displayEditCardPopupWindow();
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
    public void deleteCardFromFirebase(String docID) {
        mInteractor.deleteCard(docID);
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
}
