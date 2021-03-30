package net.project.studyez.flashcard;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import net.project.studyez.home.quickStudy.QuickStudySession;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;


public class FlashCardInteractor implements FlashCardContract.Interactor {

    private FlashCardContract.onCardGetListener onCardGetListener;
    private FlashCardContract.onStudySessionCompleted onStudySessionCompleted;

    private final FirebaseFirestore fStore;
    private final FirebaseAuth fAuth;
    private final FirebaseUser fUser;
    private DocumentReference docRef;
    private Query query;
    private QuickStudySession quickStudySession;
    public static String quickStudySessionId;
    public static String deckName;
    private final Map<String, Object> map = new HashMap<>();

    public FlashCardInteractor(){
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();
    }

    public FlashCardInteractor(FlashCardContract.onCardGetListener onCardGetListener, FlashCardContract.onStudySessionCompleted onStudySessionCompleted){
        this.onCardGetListener = onCardGetListener;
        this.onStudySessionCompleted = onStudySessionCompleted;
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();
    }

    @Override
    public void getCardsToDisplayOnFlashcards(String deckID) {
        query = fStore
                .collection("users")
                .document(fUser.getUid())
                .collection("decks")
                .document(fUser.getDisplayName())
                .collection("myDecks")
                .document(deckID)
                .collection("Cards")
                .orderBy("dateTimeCreated", Query.Direction.DESCENDING);
        query.get().addOnCompleteListener(task -> {
            if(task.isComplete()){
                onCardGetListener.onGetSuccess(task.getResult());
            }
        });
    }

    @Override
    public void createNewStudySessionInFirebase(String studyType, String deckName, int numCards, LocalTime startTime) {
        quickStudySession = new QuickStudySession(studyType, deckName, numCards, startTime.toString());

        FlashCardInteractor.deckName = deckName;

        quickStudySessionId = fStore
                .collection("users")
                .document(fUser.getUid())
                .collection("studySessions")
                .document(deckName)
                .collection(quickStudySession.getSessionMode())
                .document()
                .getId();

        docRef = fStore
                .collection("users")
                .document(fUser.getUid())
                .collection("studySessions")
                .document(deckName)
                .collection(studyType)
                .document(quickStudySessionId);
        docRef.set(quickStudySession).addOnCompleteListener(task -> {
            if(!task.isComplete()){
                onStudySessionCompleted.onWriteToFirebaseFail(task.getException().getMessage());
            }
            else{
                onStudySessionCompleted.onWriteToFirebaseSuccess();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void updateEndOfStudySession(int answeredCorrectly, LocalTime endTime) {
        long secondsBetween = ChronoUnit.SECONDS.between(LocalTime.parse(quickStudySession.getSessionStartTime()), LocalTime.parse(endTime.toString()));

        map.put("secondsToFinish", secondsBetween);
        map.put("answeredCorrectly", answeredCorrectly);
        map.put("sessionEndTime", endTime.toString());
        docRef = fStore
                .collection("users")
                .document(fUser.getUid())
                .collection("studySessions")
                .document(deckName)
                .collection(quickStudySession.getSessionMode())
                .document(quickStudySessionId);

        docRef.update(map).addOnCompleteListener(task -> {
            if(!task.isComplete()){
                onStudySessionCompleted.onWriteToFirebaseFail(task.getException().getMessage());
            }
            else{
                onStudySessionCompleted.onWriteToFirebaseSuccess();
            }
        });
    }
}
