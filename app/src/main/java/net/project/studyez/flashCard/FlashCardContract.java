package net.project.studyez.flashCard;

import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public interface FlashCardContract {

    interface view{
        void changeFragment(Fragment fragment, int id);
        void initFlashCards(List list);
    }

    interface presenter{
        void clickFlashCard();
        void getCardsFromDeck();
        void finishedCards(Fragment fragment, int id);
    }

    interface Interactor{
        void getCardsToDisplayOnFlashcards();
    }

    interface onCardGetListener{
        void onGetSuccess(QuerySnapshot querySnapshot);
        void onGetFailure(String message);
    }
}
