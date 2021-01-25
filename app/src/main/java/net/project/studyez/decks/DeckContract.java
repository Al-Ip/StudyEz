package net.project.studyez.decks;

import android.app.Activity;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public interface DeckContract {

    interface view{
        void displayAllDecks();
        void displayEmptyDeckMessage();
        void hideEmptyDeckMessage();
        void displayCreateDeckPopupWindow();
        void onDeckCreationSuccess(String message);
        void onDeckCreationFailure(String message);
    }

    interface presenter{
        void clickFab(View view);
        void enterDeckName(Activity activity, String name);
        void refreshDecks();
    }

    interface Interactor{
        void addNewDeckToFirebase(Activity activity, String deckName);
    }

    interface onDeckCreationListener{
        void onSuccess(String message);
        void onFailure(String message);
    }
}
