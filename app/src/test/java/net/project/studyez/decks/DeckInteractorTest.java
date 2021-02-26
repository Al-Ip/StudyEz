package net.project.studyez.decks;

import com.google.firebase.firestore.FirebaseFirestore;

import org.junit.Test;
import org.mockito.Mockito;

public class DeckInteractorTest {

    FirebaseFirestore mockFirestore = Mockito.mock(FirebaseFirestore.class);

    @Test
    public void addNewDecKToFirebase(){
//        DocumentReference docRef;
//        String datetime = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
//
//        docRef = (DocumentReference) Mockito.when(mockFirestore
//                .collection("testDB")
//                .document("unitTest@gmail.com")
//                .collection("MyTestDecks")
//                .document("AddTestDeck"));
//
//        DeckInteractor interactor = new DeckInteractor(mockFirestore);
//        interactor.addNewDeckToFirebase("test", datetime, "alex", 0);
//        assertTrue("Added", docRef.set(interactor.deck).isSuccessful());
    }
}