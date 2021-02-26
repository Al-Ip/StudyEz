package net.project.studyez.decks;

import android.app.Activity;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


public class DeckInteractor implements DeckContract.Interactor{

    private static final String TAG = DeckInteractor.class.getSimpleName();
    private DeckContract.onDeckCreationListener onDeckCreationListener;
    private DeckContract.onDeckDeletionListener onDeckDeletionListener;

    private FirebaseFirestore fStore;
    private FirebaseAuth fAuth;
    private FirebaseUser fUser;
    private Deck deck;
    private DocumentReference docRef;
    private FirestoreRecyclerOptions<Deck> allDecks;
    private Query query;

    public DeckInteractor(){
    }

    public DeckInteractor(FirebaseFirestore firebaseFirestore){
        fStore = firebaseFirestore;
        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();
    }

    public DeckInteractor(DeckContract.onDeckCreationListener onDeckCreationListener, DeckContract.onDeckDeletionListener onDeckDeletionListener){
        this.onDeckCreationListener = onDeckCreationListener;
        this.onDeckDeletionListener = onDeckDeletionListener;
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();
    }

    @Override
    public void addNewDeckToFirebase(String deckName, String dateTime, String creator, int numCards) {
        creator = fUser.getEmail();
        deck = new Deck(deckName, dateTime, creator, numCards);
        docRef = fStore
                .collection("Decks")
                .document(fUser.getEmail())
                .collection("myDecks")
                .document(deck.getName());
        docRef.set(deck).addOnCompleteListener(task -> {
            if(!task.isSuccessful()){
                onDeckCreationListener.onDeckCreateFailure(task.getException().getMessage());
            }
            else{
                onDeckCreationListener.onDeckCreateSuccess("Successfully Created " + deck.getName() + " Deck!");
            }
        });
    }

    @Override
    public FirestoreRecyclerOptions getDecksFromFirebase(Activity activity) {
        query = fStore
                .collection("Decks")
                .document(fUser.getEmail())
                .collection("myDecks")
                .orderBy("dateTimeCreated", Query.Direction.DESCENDING);
        allDecks = new FirestoreRecyclerOptions.Builder<Deck>()
                .setQuery(query, Deck.class)
                .build();
        return allDecks;
    }

    @Override
    public void deleteDeck(String docID) {
        docRef = fStore
                .collection("Decks")
                .document(fUser.getEmail())
                .collection("myDecks")
                .document(docID);
        docRef.delete().addOnCompleteListener(task -> {
            if(!task.isComplete()){
                onDeckDeletionListener.onDeleteFailure(task.getException().getMessage());
            }
            else{
                onDeckDeletionListener.onDeleteSuccess("Successfully Deleted Deck!");
            }
        });
    }
}
