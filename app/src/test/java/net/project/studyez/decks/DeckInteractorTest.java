package net.project.studyez.decks;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;

import java.util.Calendar;

import kotlin.jvm.JvmField;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DeckInteractorTest {

    @JvmField
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    FirebaseFirestore mockFirestore = Mockito.mock(FirebaseFirestore.class);
    FirebaseAuth mockFirebaseAuth = Mockito.mock(FirebaseAuth.class);
    DocumentReference docRef = Mockito.mock(DocumentReference.class);
    DocumentSnapshot documentSnapshot = Mockito.mock(DocumentSnapshot.class);
    Deck deck = Mockito.mock(Deck.class);

    @Mock
    DeckInteractor deckInteractor = Mockito.mock(DeckInteractor.class);

    @Mock
    private DeckContract.onDeckCreationListener deckCreationListener;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        deckInteractor = new DeckInteractor(mockFirestore, mockFirebaseAuth);
    }

    @Test
    public void add_new_deck_to_dummy_firebase(){
        String datetime = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        String deckName = "test";
        String creator = "alexTest";
        String creatorID = "UkzIFXj3UidlEvBeyVxGMdlsF6a2";
        int numCards = 0;

        deck = new Deck(creatorID, deckName, datetime,  creator, numCards);

        docRef = mockFirestore
                .collection("testDB")
                .document("unitTest@gmail.com")
                .collection("MyTestDecks")
                .document("AddTestDeck");
        docRef.set(deck).addOnCompleteListener(task -> verify(documentSnapshot).contains("name"));

//        documentSnapshot = mockFirestore.collection("testDB")
//                .get()
//                .getResult()
//                .getDocuments()
//                .get(0)
//                .getDocumentReference("creator")
//                .get()
//                .getResult();
//
//        verify(documentSnapshot).contains("name");
    }
}