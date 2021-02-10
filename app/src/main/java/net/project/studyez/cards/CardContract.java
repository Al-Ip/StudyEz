package net.project.studyez.cards;

import android.app.Activity;
import android.view.View;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public interface CardContract {

    interface view{
        void createCardDialogConfirm(String deckName, String question, String answer, String dateTimeCreated, boolean isStarred);
        void frontAndBackOfCardText(String question, String answer);
        void displayEmptyCardsMessage();
        void hideEmptyCardsMessage();
        void displayCreateCardPopupWindow();
        void displayDeleteCardPopupWindow();
        void displayEditCardPopupWindow(String question, String answer);
        void deleteCardDialogConfirm();
        void onCardCreationSuccess(String message);
        void onCardCreationFailure(String message);
        void onCardDeletionSuccess(String message);
        void onCardDeletionFailure(String message);
        void onCardEditSuccess(String message);
        void onCardEditFailure(String message);
    }

    interface presenter{
        void clickFab(View view);
        void clickEditImage(View view, String question, String answer);
        void clickRemoveImage(View view);
        FirestoreRecyclerOptions getCardsFromDeck(Activity activity, String deckName);
        void getCardDetails(String deckName, String question, String answer, String dateTimeCreated, boolean isStarred);
        void editCardDetails(String deckName, String question, String answer, String docID);
        void deleteCardFromFirebase(String deckName, String docID);
    }

    interface Interactor{
        void addNewCardToFirebase(String deckName, String question, String answer, String dateCreated, boolean isStarred);
        FirestoreRecyclerOptions getCardsFromFirebase(Activity activity, String deckName);
        void deleteCardFromFirebase(String deckName, String docID);
        void editCardFromFirebase(String deckName, String question, String answer, String docID);
    }

    interface onCardCreationListener{
        void onCreateSuccess(String message);
        void onCreateFailure(String message);
    }

    interface onCardDeletionListener{
        void onDeleteSuccess(String message);
        void onDeleteFailure(String message);
    }

    interface onCardEditListener{
        void onEditSuccess(String message);
        void onEditFailure(String message);
    }

}
