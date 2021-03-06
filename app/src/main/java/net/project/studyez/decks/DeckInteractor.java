package net.project.studyez.decks;

import android.app.Activity;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import javax.inject.Inject;


public class DeckInteractor implements DeckContract.Interactor{

    private static final String TAG = DeckInteractor.class.getSimpleName();
    private DeckContract.onDeckCreationListener onDeckCreationListener;
    private DeckContract.onDeckDeletionListener onDeckDeletionListener;

    private final FirebaseFirestore fStore;
    private final FirebaseAuth fAuth;
    private final FirebaseUser fUser;
    private Deck deck;
    private DocumentReference docRef;
    private FirestoreRecyclerOptions<Deck> allDecks;
    private Query query;

    @Inject
    public DeckInteractor(FirebaseFirestore firebaseFirestore, FirebaseAuth fAuth){
        fStore = firebaseFirestore;
        this.fAuth = fAuth;
        this.fUser = fAuth.getCurrentUser();
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
        deck = new Deck(deckName, dateTime,  fUser.getDisplayName(), numCards);
        docRef = fStore
                .collection("users")
                .document(fUser.getUid())
                .collection("decks")
                .document(fUser.getDisplayName())
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
                .collection("users")
                .document(fUser.getUid())
                .collection("decks")
                .document(fUser.getDisplayName())
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
                .collection("users")
                .document(fUser.getUid())
                .collection("decks")
                .document(fUser.getDisplayName())
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
