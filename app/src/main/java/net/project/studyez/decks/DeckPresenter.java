package net.project.studyez.decks;

import android.app.Activity;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.type.Date;
import com.google.type.DateTime;

public class DeckPresenter implements DeckContract.presenter, DeckContract.onDeckCreationListener, DeckContract.onDeckDeletionListener {

    // to keep reference to view
    private final DeckContract.view mView;
    private final DeckContract.Interactor mInteractor;

    public DeckPresenter(DeckContract.view view){
        mView = view;
        mInteractor = new DeckInteractor(this, this);
    }

    @Override
    public void clickFab(View view) {
        mView.displayCreateDeckPopupWindow();
    }

    @Override
    public void enterDeckName(String name, String dateTime) {
        mInteractor.addNewDeckToFirebase(name, dateTime);
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
        mView.displayDeleteDeckPopupWindow();
    }

    @Override
    public void shortPressOnDeck(Fragment fragment, int id) {
        mView.changeFragment(fragment, id);
    }

    @Override
    public void onCreateSuccess(String message) {
        mView.onDeckCreationSuccess(message);
        mView.hideEmptyDeckMessage();
    }

    @Override
    public void onCreateFailure(String message) {
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
}
