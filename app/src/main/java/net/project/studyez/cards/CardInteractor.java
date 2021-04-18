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

    private CardContract.onCardCreationListener onCardCreationListener;
    private CardContract.onCardDeletionListener onCardDeletionListener;
    private CardContract.onCardEditListener onCardEditListener;

    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    FirebaseUser fUser;
    Card card;
    AtomicInteger counter;
    DocumentReference docRef;
    FirestoreRecyclerOptions<Card> allCards;
    Query query;

    public CardInteractor(){
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();
    }

    public CardInteractor(CardContract.onCardCreationListener onCardCreationListener,
                          CardContract.onCardDeletionListener onCardDeletionListener,
                          CardContract.onCardEditListener onCardEditListener){
        this.onCardCreationListener = onCardCreationListener;
        this.onCardDeletionListener = onCardDeletionListener;
        this.onCardEditListener = onCardEditListener;
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();
    }

    @Override
    public void addNewCardToFirebase(String deckID, String deckName, String question, String answer, String dateCreated, boolean isStarred) {
        card = new Card(deckName, question, answer, dateCreated, isStarred);
        docRef = fStore
                .collection("users")
                .document(fUser.getUid())
                .collection("decks")
                .document(fUser.getDisplayName())
                .collection("myDecks")
                .document(deckID)
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
    public FirestoreRecyclerOptions getCardsFromFirebase(Activity activity, String deckID) {
        query = fStore
                .collection("users")
                .document(fUser.getUid())
                .collection("decks")
                .document(fUser.getDisplayName())
                .collection("myDecks")
                .document(deckID)
                .collection("Cards")
                .orderBy("dateTimeCreated", Query.Direction.DESCENDING);
        allCards = new FirestoreRecyclerOptions.Builder<Card>()
                .setQuery(query, Card.class)
                .build();
        return allCards;
    }

    @Override
    public void deleteCardFromFirebase(String deckID, String docID) {
        docRef = fStore
                .collection("users")
                .document(fUser.getUid())
                .collection("decks")
                .document(fUser.getDisplayName())
                .collection("myDecks")
                .document(deckID)
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
    public void updateCardFromFirebase(String deckID, String question, String answer, String docID) {
        Map<String, Object> map = new HashMap<>();
        map.put("answer", answer);
        map.put("question", question);
        docRef = fStore
                .collection("users")
                .document(fUser.getUid())
                .collection("decks")
                .document(fUser.getDisplayName())
                .collection("myDecks")
                .document(deckID)
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
    public void updateNumberOfCardsFromFirebase(String deckID, int numCards) {
        Map<String, Object> map = new HashMap<>();
        map.put("numCards", numCards);
        docRef = fStore
                .collection("users")
                .document(fUser.getUid())
                .collection("decks")
                .document(fUser.getDisplayName())
                .collection("myDecks")
                .document(deckID);
        docRef.update(map);
    }


}