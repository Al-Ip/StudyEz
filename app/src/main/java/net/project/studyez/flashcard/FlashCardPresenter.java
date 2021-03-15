package net.project.studyez.flashcard;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.QuerySnapshot;

import net.project.studyez.cards.Card;

import java.time.LocalTime;

public class FlashCardPresenter implements FlashCardContract.presenter, FlashCardContract.onCardGetListener,
        FlashCardContract.onStudySessionCompleted {

    private final FlashCardContract.Interactor mInteractor;
    private final FlashCardContract.view mView;
    private int correctAnswer;

    public FlashCardPresenter(FlashCardContract.view view){
        this.mView = view;
        this.mInteractor = new FlashCardInteractor(this, this);
    }

    @Override
    public void clickFlashCard() {

    }

    @Override
    public void initStudySession(String studyType, String deckName, int numCards, LocalTime startTime) {
        mInteractor.createNewStudySessionInFirebase(studyType, deckName, numCards, startTime);
    }

    @Override
    public void swipedRight() {
        ++correctAnswer;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void endStudySession() {
        LocalTime endTime = LocalTime.now();
        mInteractor.updateEndOfStudySession(correctAnswer, LocalTime.parse(endTime.toString()));
        correctAnswer = 0;
    }

    @Override
    public void getCardsFromDeck(String deckID) {
        mInteractor.getCardsToDisplayOnFlashcards(deckID);
    }

    @Override
    public void finishedCards(Fragment fragment, int id) {
        mView.changeFragment(fragment, id);
    }


    @Override
    public void onGetSuccess(QuerySnapshot querySnapshot) {
        mView.initFlashCards(querySnapshot.toObjects(Card.class));
    }

    @Override
    public void onGetFailure(String message) {

    }

    @Override
    public void onWriteToFirebaseSuccess() {

    }

    @Override
    public void onWriteToFirebaseFail(String message) {
        mView.displayFailedToWriteMessage(message);
    }
}
