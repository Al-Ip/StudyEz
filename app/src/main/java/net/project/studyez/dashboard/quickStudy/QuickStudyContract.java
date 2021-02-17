package net.project.studyez.dashboard.quickStudy;

import android.app.Activity;

import androidx.fragment.app.Fragment;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public interface QuickStudyContract {

    interface view{
        void displayNoDeckMessage();
        void hideNoDeckMessage();
        void changeFragment(Fragment fragment, int id);
    }

    interface presenter{
        FirestoreRecyclerOptions getDecks(Activity activity);
        void longPressOnDeck();
        void showNoDeckMessage();
        void hideNoDeckMessage();
    }

    interface Interactor{
        FirestoreRecyclerOptions getDecksFromFirebase(Activity activity);
    }
}
