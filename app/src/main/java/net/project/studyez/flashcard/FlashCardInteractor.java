package net.project.studyez.flashcard;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


public class FlashCardInteractor implements FlashCardContract.Interactor {

    private FlashCardContract.onCardGetListener onCardGetListener;

    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    FirebaseUser fUser;
    Query query;

    public FlashCardInteractor(){
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();
    }

    public FlashCardInteractor(FlashCardContract.onCardGetListener onCardGetListener){
        this.onCardGetListener = onCardGetListener;
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
}
