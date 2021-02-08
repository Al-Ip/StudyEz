package net.project.studyez.cards;

import android.app.Activity;
import android.util.Log;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import net.project.studyez.decks.Deck;

import java.util.ArrayList;
import java.util.List;

import static net.project.studyez.decks.DeckInteractor.deck;

public class CardInteractor implements CardContract.Interactor{

    private static final String TAG = CardInteractor.class.getSimpleName();
    private final CardContract.onCardCreationListener onCardCreationListener;
    private final CardContract.onCardDeletionListener onCardDeletionListener;

    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    FirebaseUser fUser;
    Card card;
    DocumentReference docRef;
    FirestoreRecyclerOptions<Card> allCards;
    Query query;

    public CardInteractor(CardContract.onCardCreationListener onCardCreationListener, CardContract.onCardDeletionListener onCardDeletionListener){
        this.onCardCreationListener = onCardCreationListener;
        this.onCardDeletionListener = onCardDeletionListener;
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
    public void deleteCard(String docID) {
        docRef = fStore
                .collection("Decks")
                .document(fUser.getEmail())
                .collection("myDecks")
                .document("11111")
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
}