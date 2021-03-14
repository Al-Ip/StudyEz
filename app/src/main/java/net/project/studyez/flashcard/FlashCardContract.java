package net.project.studyez.flashcard;

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
        void getCardsFromDeck(String deckID);
        void finishedCards(Fragment fragment, int id);
    }

    interface Interactor{
        void getCardsToDisplayOnFlashcards(String deckID);
    }

    interface onCardGetListener{
        void onGetSuccess(QuerySnapshot querySnapshot);
        void onGetFailure(String message);
    }
}
