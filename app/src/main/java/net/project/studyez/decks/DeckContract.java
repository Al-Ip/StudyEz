package net.project.studyez.decks;

import android.app.Activity;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import dagger.Component;

@Component
public interface DeckContract {

    interface view{
        void displayMenu(String deckID, String deckName);
        void displayEmptyDeckMessage();
        void hideEmptyDeckMessage();
        void displayCreateDeckPopupWindow();
        void onDeckCreationSuccess(String message);
        void onDeckCreationFailure(String message);
        void onDeckDeletionSuccess(String message);
        void onDeckDeletionFailure(String message);
        void onDeckUpdateSuccess(String message);
        void onDeckUpdateFailure(String message);
        void getDetailsFromDeckDialog(String deckName, String dateTime, String creator, int numCards);
        void deleteDeckDialogConfirm();
        void updateDeckNameDialogConfirm(String deckID, String deckName);
        void displayUpdateDeckNameDialog(String deckID, String deckName);
        void changeFragment(Fragment fragment, int id);
    }

    interface presenter{
        void clickFab(View view);
        void creatingNewDeck(String deckName, String dateTime, String creator, int numCards);
        FirestoreRecyclerOptions getDecks(Activity activity);
        void updateDeckName(String deckID, String deckName);
        void deleteDeck(String docID);
        void longPressOnDeck(String deckID, String deckName);
        void shortPressOnDeck(Fragment fragment, int id);
        void showEmptyDeckMessage();
        void hideEmptyDeckMessage();
    }


    interface Interactor{
        void addNewDeckToFirebase(String deckName, String dateTime, String creator, int numCards);
        FirestoreRecyclerOptions getDecksFromFirebase(Activity activity);
        void updateExistingDeckFromFirebase(String deckID, String deckName);
        void deleteDeckFromFirebase(String docID);
    }

    interface onDeckCreationListener{
        void onDeckCreateSuccess(String message);
        void onDeckCreateFailure(String message);
    }

    interface onDeckDeletionListener{
        void onDeleteSuccess(String message);
        void onDeleteFailure(String message);
    }

    interface onDeckGetListener{
        void onGetSuccess(String message);
        void onGetFailure(String message);
    }

    interface onDeckUpdateListener{
        void onUpdateSuccess(String message);
        void onUpdateFailure(String message);
    }
}
