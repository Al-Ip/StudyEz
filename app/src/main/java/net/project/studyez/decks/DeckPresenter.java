package net.project.studyez.decks;

import android.app.Activity;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import javax.inject.Inject;

public class DeckPresenter implements DeckContract.presenter, DeckContract.onDeckCreationListener,
        DeckContract.onDeckDeletionListener, DeckContract.onDeckGetListener {

    // to keep reference to view
    private final DeckContract.view mView;
    private final DeckContract.Interactor mInteractor;

    public DeckPresenter(DeckContract.view view, DeckContract.Interactor interactor){
        mView = view;
        mInteractor = interactor;
    }

    @Inject
    public DeckPresenter(DeckContract.view view){
        mView = view;
        mInteractor = new DeckInteractor(this, this, this);
    }

    @Override
    public void clickFab(View view) {
        mView.displayCreateDeckPopupWindow();
    }





    @Override
    public void creatingNewDeck(String deckName, String dateTime, String creator, int numCards) {
        mInteractor.addNewDeckToFirebase(deckName, dateTime, creator, numCards);
    }

    @Override
    public FirestoreRecyclerOptions getDecks(Activity activity) {
        return mInteractor.getDecksFromFirebase(activity);
    }

    @Override
    public void deleteDeckFromFirebase(String docID) {
        mInteractor.deleteDeck(docID);
    }

    @Override
    public void longPressOnDeck() {
        mView.displayMenu();
    }

    @Override
    public void shortPressOnDeck(Fragment fragment, int id) {
        mView.changeFragment(fragment, id);
    }

    @Override
    public void showEmptyDeckMessage() {
        mView.displayEmptyDeckMessage();
    }

    @Override
    public void hideEmptyDeckMessage() {
        mView.hideEmptyDeckMessage();
    }

    @Override
    public void onDeckCreateSuccess(String message) {
        mView.onDeckCreationSuccess(message);
    }

    @Override
    public void onDeckCreateFailure(String message) {
        mView.onDeckCreationFailure(message);
    }

    @Override
    public void onDeleteSuccess(String message) {
        mView.onDeckDeletionSuccess(message);
    }

    @Override
    public void onDeleteFailure(String message) {
        mView.onDeckDeletionFailure(message);
    }

    @Override
    public void onGetSuccess(String message) {

    }

    @Override
    public void onGetFailure(String message) {

    }
}
