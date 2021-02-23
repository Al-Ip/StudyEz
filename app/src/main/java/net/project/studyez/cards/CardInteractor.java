package net.project.studyez.cards;

import android.app.Activity;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class CardInteractor implements CardContract.Interactor{

    private static final String TAG = CardInteractor.class.getSimpleName();
    private final CardContract.onCardCreationListener onCardCreationListener;
    private final CardContract.onCardDeletionListener onCardDeletionListener;
    private final CardContract.onCardEditListener onCardEditListener;

    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    FirebaseUser fUser;
    Card card;
    AtomicInteger counter;
    DocumentReference docRef;
    FirestoreRecyclerOptions<Card> allCards;
    Query query;

    public CardInteractor(CardContract.onCardCreationListener onCardCreationListener, CardContract.onCardDeletionListener onCardDeletionListener, CardContract.onCardEditListener onCardEditListener){
        this.onCardCreationListener = onCardCreationListener;
        this.onCardDeletionListener = onCardDeletionListener;
        this.onCardEditListener = onCardEditListener;
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();
    }

    @Override
    public void addNewCardToFirebase(String deckName, String question, String answer, String dateCreated, boolean isStarred) {
        card = new Card(deckName, question, answer, dateCreated, isStarred);
        docRef = fStore
                .collection("Decks")
                .document(fUser.getEmail())
                .collection("myDecks")
                .document(deckName)
                .collection("Cards")
                .document();
        docRef.set(card).addOnCompleteListener(task -> {
            if(!task.isSuccessful()){
                onCardCreationListener.onCreateFailure(task.getException().getMessage());
            }
            else{
                onCardCreationListener.onCreateSuccess("Successfully Created Card!");
            }
        });
    }

    @Override
    public FirestoreRecyclerOptions getCardsFromFirebase(Activity activity, String deckName) {
        query = fStore
                .collection("Decks")
                .document(fUser.getEmail())
                .collection("myDecks")
                .document(deckName)
                .collection("Cards")
                .orderBy("dateTimeCreated", Query.Direction.DESCENDING);
        allCards = new FirestoreRecyclerOptions.Builder<Card>()
                .setQuery(query, Card.class)
                .build();
        return allCards;
    }

    @Override
    public void deleteCardFromFirebase(String deckName, String docID) {
        docRef = fStore
                .collection("Decks")
                .document(fUser.getEmail())
                .collection("myDecks")
                .document(deckName)
                .collection("Cards")
                .document(docID);
        docRef.delete().addOnCompleteListener(task -> {
            if(!task.isComplete()){
                onCardDeletionListener.onDeleteFailure(task.getException().getMessage());
            }
            else{
                onCardDeletionListener.onDeleteSuccess("Successfully Deleted Card!");
            }
        });
    }

    @Override
    public void editCardFromFirebase(String deckName, String question, String answer, String docID) {
        Map<String, Object> map = new HashMap<>();
        map.put("answer", answer);
        map.put("question", question);
        docRef = fStore
                .collection("Decks")
                .document(fUser.getEmail())
                .collection("myDecks")
                .document(deckName)
                .collection("Cards")
                .document(docID);
        docRef.update(map).addOnCompleteListener(task -> {
            if(!task.isComplete()){
                onCardEditListener.onEditFailure(task.getException().getMessage());
            }
            else{
                onCardEditListener.onEditSuccess("Successfully Updated Card!");
            }
        });
    }

    @Override
    public void updateNumberOfCardsFromFirebase(String deckName, int numCards) {
        Map<String, Object> map = new HashMap<>();
        map.put("numCards", numCards);
        docRef = fStore
                .collection("Decks")
                .document(fUser.getEmail())
                .collection("myDecks")
                .document(deckName);
        docRef.update(map);
    }

}