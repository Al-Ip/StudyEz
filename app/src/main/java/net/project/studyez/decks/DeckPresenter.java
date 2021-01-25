package net.project.studyez.decks;

import android.app.Activity;
import android.view.View;

import com.google.firebase.firestore.FirebaseFirestore;

public class DeckPresenter implements DeckContract.presenter, DeckContract.onDeckCreationListener {

    // to keep reference to view
    private final DeckContract.view mView;
    private final DeckContract.Interactor mInteractor;

    public DeckPresenter(DeckContract.view view){
        mView = view;
        mInteractor = new DeckInteractor(this);
    }

    @Override
    public void clickFab(View view) {
        mView.displayCreateDeckPopupWindow();
    }

    @Override
    public void enterDeckName(Activity activity, String name) {
        mInteractor.addNewDeckToFirebase(activity, name);
    }

    @Override
    public void refreshDecks() {
    }

    @Override
    public void onSuccess(String message) {
        mView.onDeckCreationSuccess(message);
        mView.hideEmptyDeckMessage();
    }

    @Override
    public void onFailure(String message) {
        mView.onDeckCreationFailure(message);
    }
}
