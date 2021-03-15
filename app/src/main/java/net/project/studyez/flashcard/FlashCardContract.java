package net.project.studyez.flashcard;

import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalTime;
import java.util.List;

public interface FlashCardContract {

    interface view{
        void changeFragment(Fragment fragment, int id);
        void initFlashCards(List list);
        void displayFailedToWriteMessage(String message);
    }

    interface presenter{
        void clickFlashCard();
        void initStudySession(String studyType, String deckName, int numCards, LocalTime startTime);
        void swipedRight();
        void endStudySession();
        void getCardsFromDeck(String deckID);
        void finishedCards(Fragment fragment, int id);
    }

    interface Interactor{
        void getCardsToDisplayOnFlashcards(String deckID);
        void createNewStudySessionInFirebase(String studyType, String deckName, int numCards, LocalTime startTime);
        void updateEndOfStudySession(int answeredCorrectly , LocalTime endTime);
    }

    interface onCardGetListener{
        void onGetSuccess(QuerySnapshot querySnapshot);
        void onGetFailure(String message);
    }

    interface onStudySessionCompleted{
        void onWriteToFirebaseSuccess();
        void onWriteToFirebaseFail(String message);
    }
}
