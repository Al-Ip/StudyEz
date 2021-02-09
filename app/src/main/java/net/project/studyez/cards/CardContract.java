package net.project.studyez.cards;

import android.app.Activity;
import android.view.View;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public interface CardContract {

    interface view{
        void frontAndBackOfCardText(String deckName, String question, String answer, String dateTimeCreated, boolean isStarred);
        void displayEmptyCardsMessage();
        void hideEmptyCardsMessage();
        void displayCreateCardPopupWindow();
        void displayDeleteCardPopupWindow();
        void displayEditCardPopupWindow();
        void deleteCardDialogConfirm();
        void onCardCreationSuccess(String message);
        void onCardCreationFailure(String message);
        void onCardDeletionSuccess(String message);
        void onCardDeletionFailure(String message);
    }

    interface presenter{
        void clickFab(View view);
        void clickEditImage(View view);
        void clickRemoveImage(View view);
        FirestoreRecyclerOptions getCardsFromDeck(Activity activity, String deckName);
        void getCardDetails(String deckName, String question, String answer, String dateTimeCreated, boolean isStarred);
        void deleteCardFromFirebase(String deckName, String docID);
    }

    interface Interactor{
        void addNewCardToFirebase(String deckName, String question, String answer, String dateCreated, boolean isStarred);
        FirestoreRecyclerOptions getCardsFromFirebase(Activity activity, String deckName);
        void deleteCard(String deckName, String docID);
    }

    interface onCardCreationListener{
        void onCreateSuccess(String message);
        void onCreateFailure(String message);
    }

    interface onCardDeletionListener{
        void onDeleteSuccess(String message);
        void onDeleteFailure(String message);
    }

}
