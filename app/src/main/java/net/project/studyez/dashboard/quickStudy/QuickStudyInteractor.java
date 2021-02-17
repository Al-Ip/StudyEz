package net.project.studyez.dashboard.quickStudy;

import android.app.Activity;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import net.project.studyez.decks.Deck;

public class QuickStudyInteractor implements QuickStudyContract.Interactor{

    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    FirebaseUser fUser;
    Deck deck;
    DocumentReference docRef;
    FirestoreRecyclerOptions<Deck> allDecks;
    Query query;

    public QuickStudyInteractor(){
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();
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
}
