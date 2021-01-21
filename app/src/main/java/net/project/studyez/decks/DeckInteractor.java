package net.project.studyez.decks;

import android.app.Activity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class DeckInteractor implements DeckContract.Interactor{

    private static final String TAG = DeckInteractor.class.getSimpleName();
    private final DeckContract.onDeckCreationListener onDeckCreationListener;

    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    FirebaseUser fUser;

    public DeckInteractor(DeckContract.onDeckCreationListener onDeckCreationListener){
        this.onDeckCreationListener = onDeckCreationListener;
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();
    }

    @Override
    public void addNewDeckToFirebase(Activity activity, String deckName) {
        Deck deck = new Deck(deckName);
        DocumentReference docRef = fStore.collection("Decks").document(fUser.getUid()).collection("myDecks").document();
        docRef.set(deck).addOnCompleteListener(task -> {
            if(!task.isSuccessful()){
                onDeckCreationListener.onFailure(task.getException().getMessage());
            }
            else{
                onDeckCreationListener.onSuccess("Successfully Created " + deck.getName() + " Deck!");
            }
        });
    }
}
