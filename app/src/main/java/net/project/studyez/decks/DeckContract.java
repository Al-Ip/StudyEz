package net.project.studyez.decks;

import android.app.Activity;
import android.view.View;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.type.Date;
import com.google.type.DateTime;

public interface DeckContract {

    interface view{
        void displayEmptyDeckMessage();
        void hideEmptyDeckMessage();
        void displayCreateDeckPopupWindow();
        void displayDeleteDeckPopupWindow();
        void onDeckCreationSuccess(String message);
        void onDeckCreationFailure(String message);
        void onDeckDeletionSuccess(String message);
        void onDeckDeletionFailure(String message);
        void getDeckNameFromDialog(String name, String dateTime);
        void deleteDeckDialogConfirm();
    }

    interface presenter{
        void clickFab(View view);
        void enterDeckName(String name, String dateTime);
        FirestoreRecyclerOptions getDecks(Activity activity);
        void deleteDeckFromFirebase(String docID);
        void longPressOnDeck();
        void shortPressOnDeck();
    }

    interface Interactor{
        void addNewDeckToFirebase(String deckName, String dateTime);
        FirestoreRecyclerOptions getDecksFromFirebase(Activity activity);
        void deleteDeck(String docID);
    }

    interface onDeckCreationListener{
        void onCreateSuccess(String message);
        void onCreateFailure(String message);
    }

    interface onDeckDeletionListener{
        void onDeleteSuccess(String message);
        void onDeleteFailure(String message);
    }
}
