package net.project.studyez.decks;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.type.Date;
import com.google.type.DateTime;

public class DeckInteractor implements DeckContract.Interactor{

    private static final String TAG = DeckInteractor.class.getSimpleName();
    private final DeckContract.onDeckCreationListener onDeckCreationListener;
    private final DeckContract.onDeckDeletionListener onDeckDeletionListener;

    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    FirebaseUser fUser;
    public static Deck deck;
    DocumentReference docRef;
    FirestoreRecyclerOptions<Deck> allDecks;
    Query query;

    public DeckInteractor(DeckContract.onDeckCreationListener onDeckCreationListener, DeckContract.onDeckDeletionListener onDeckDeletionListener){
        this.onDeckCreationListener = onDeckCreationListener;
        this.onDeckDeletionListener = onDeckDeletionListener;
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();
    }

    @Override
    public void addNewDeckToFirebase(String deckName, String dateTime) {
        deck = new Deck(deckName, dateTime);
        docRef = fStore
                .collection("Decks")
                .document(fUser.getEmail())
                .collection("myDecks")
                .document(deck.getName());
        docRef.set(deck).addOnCompleteListener(task -> {
            if(!task.isSuccessful()){
                onDeckCreationListener.onCreateFailure(task.getException().getMessage());
            }
            else{
                onDeckCreationListener.onCreateSuccess("Successfully Created " + deck.getName() + " Deck!");
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
