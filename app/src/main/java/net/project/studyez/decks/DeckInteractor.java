package net.project.studyez.decks;

import android.app.Activity;
import android.util.Log;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;


public class DeckInteractor implements DeckContract.Interactor{

    private static final String TAG = DeckInteractor.class.getSimpleName();
    private DeckContract.onDeckCreationListener onDeckCreationListener;
    private DeckContract.onDeckDeletionListener onDeckDeletionListener;
    private DeckContract.onDeckUpdateListener onDeckUpdateListener;

    private final FirebaseFirestore fStore;
    private final FirebaseAuth fAuth;
    private final FirebaseUser fUser;
    private Deck deck;
    private Deck updateDeckObject;
    private String previousDeckName;
    private DocumentReference docRef;
    private FirestoreRecyclerOptions<Deck> allDecks;
    private Query query;
    private final Map<String, Object> map = new HashMap<>();

    @Inject
    public DeckInteractor(FirebaseFirestore firebaseFirestore, FirebaseAuth fAuth){
        fStore = firebaseFirestore;
        this.fAuth = fAuth;
        this.fUser = fAuth.getCurrentUser();
    }

    public DeckInteractor(DeckContract.onDeckCreationListener onDeckCreationListener,
                          DeckContract.onDeckDeletionListener onDeckDeletionListener,
                          DeckContract.onDeckUpdateListener onDeckUpdateListener){
        this.onDeckCreationListener = onDeckCreationListener;
        this.onDeckDeletionListener = onDeckDeletionListener;
        this.onDeckUpdateListener = onDeckUpdateListener;
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();
    }

    @Override
    public void addNewDeckToFirebase(String deckName, String dateTime, String creator, int numCards) {
        deck = new Deck(fUser.getUid(), deckName, dateTime,  fUser.getDisplayName(), numCards, "");
        docRef = fStore
                .collection("users")
                .document(fUser.getUid())
                .collection("decks")
                .document(fUser.getDisplayName())
                .collection("myDecks")
                .document();
        docRef.set(deck).addOnCompleteListener(task -> {
            if(!task.isSuccessful()){
                onDeckCreationListener.onDeckCreateFailure(task.getException().getMessage());
            }
            else{
                onDeckCreationListener.onDeckCreateSuccess("Successfully Created " + deck.getDeckName() + " Deck!");
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
    public void updateExistingDeckFromFirebase(String deckID, String newDeckName) {
        // Getting deck to store as object before updating
        updateDeckObject = new Deck();
        previousDeckName = "";
        docRef = fStore
                .collection("users")
                .document(fUser.getUid())
                .collection("decks")
                .document(fUser.getDisplayName())
                .collection("myDecks")
                .document(deckID);
        docRef.get().addOnSuccessListener(documentSnapshot -> {
            if(documentSnapshot.exists()) {
                // Document found, store into POJO
                updateDeckObject = documentSnapshot.toObject(Deck.class);
                previousDeckName = updateDeckObject.getDeckName();
                // Deck name with Session records exist, update with new name
                Map<String, Object> map = new HashMap<>();
                map.put("deckName", newDeckName);
                map.put("previousDeckName", previousDeckName);
                docRef = fStore
                        .collection("users")
                        .document(fUser.getUid())
                        .collection("decks")
                        .document(fUser.getDisplayName())
                        .collection("myDecks")
                        .document(deckID);
                docRef.update(map).addOnCompleteListener(task3 -> {
                    if(!task3.isComplete()){
                        onDeckUpdateListener.onUpdateFailure(task3.getException().getMessage());
                    }
                    else{
                        updateDeckObject.setDeckName(previousDeckName);
                        updateDeckObject.setPreviousDeckName(newDeckName);
                        docRef = fStore
                                .collection("users")
                                .document(fUser.getUid())
                                .collection("studySessions")
                                .document(previousDeckName);
                        docRef.set(updateDeckObject).addOnCompleteListener(task2 -> {
                            if(!task2.isComplete()){
                                onDeckUpdateListener.onUpdateFailure(task2.getException().getMessage());
                            }
                            else{
                                onDeckUpdateListener.onUpdateSuccess("Successfully Updated Deck Name!");
                            }
                        });
                    }
                });
            }
            else {
                // Document does not exist so write it
                // Init list of days as-well as the study session weekly object
                Log.e("TAG", "Document Does Not exist, No Need to update sessions!");
            }
        });
    }

    @Override
    public void deleteDeckFromFirebase(String docID) {
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
