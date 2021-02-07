package net.project.studyez.cards;

import android.app.Activity;
import android.view.View;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public interface CardContract {

    interface view{
        void displayEmptyCardsMessage();
        void hideEmptyCardsMessage();
        void displayCreateCardPopupWindow();
        void displayDeleteCardPopupWindow();
    }

    interface presenter{
        void clickFab(View view);
    }
}
