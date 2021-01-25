package net.project.studyez.decks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import net.project.studyez.R;
import net.project.studyez.databinding.FragmentDecksBinding;

public class DecksFragment extends Fragment implements DeckContract.view{

    public static final long VIBRATE_TIME = 70;
    public static final int PINK_RED = 255;
    public static final int PINK_GREEN = 105;
    public static final int PINK_BLUE = 180;
    public static final String USERS = "users";
    public static final String DECKS = "myDecks";
    public static final String FAVORITES = "favorites";

    private DeckPresenter deckPresenter;
    private RecyclerView deckRecyclerView;
    private DatabaseReference rootRef;
    private DatabaseReference deckRef;
    private DatabaseReference userRef;
    private ImageView emptyDeck;

    private FirebaseFirestore fStore;
    private FirebaseUser user;
    private FirebaseAuth fAuth;
    private FirestoreRecyclerAdapter<Deck, DeckViewHolder> deckAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.decks_fragment, container, false);
//        return root;

        //Stuff to initialize
        FragmentDecksBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_decks, container, false);
        View view = binding.getRoot();
        deckPresenter = new DeckPresenter(this);
        binding.setPresenter(deckPresenter);

        emptyDeck = view.findViewById(R.id.emptyDecksImage);
        deckRecyclerView = view.findViewById(R.id.deckRecycler);
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();

        Query query = fStore.collection("Decks").document(user.getUid()).collection("myDecks").orderBy("name", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<Deck> allDecks = new FirestoreRecyclerOptions.Builder<Deck>()
                .setQuery(query, Deck.class)
                .build();

        deckAdapter = new FirestoreRecyclerAdapter<Deck, DeckViewHolder>(allDecks) {
            @NonNull
            @Override
            public DeckViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.deck_list, parent, false);
                return new DeckViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull DeckViewHolder deckViewHolder, int i, @NonNull Deck deck) {
                deckViewHolder.deckName.setText(deck.getName());
                deckViewHolder.creatorText.setText(deck.getCreator());
            }
        };
        deckRecyclerView.setLayoutManager(new LinearLayoutManager(container.getContext(), LinearLayoutManager.VERTICAL, false));
        deckRecyclerView.setAdapter(deckAdapter);

        if(!deckAdapter.getSnapshots().isEmpty())
            emptyDeck.setVisibility(View.VISIBLE);
        else
            emptyDeck.setVisibility(View.GONE);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        deckAdapter.startListening();
    }

    public void getDeckNameFromDialog(String name){
        deckPresenter.enterDeckName(getActivity(), name);
    }

    @Override
    public void displayAllDecks() {

    }

    @Override
    public void displayEmptyDeckMessage() {
        emptyDeck.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEmptyDeckMessage() {
        emptyDeck.setVisibility(View.GONE);
    }

    @Override
    public void displayCreateDeckPopupWindow() {
        NewDeckDialog newFragment = new NewDeckDialog();
        newFragment.show(getChildFragmentManager(), "Deck name Dialog");
    }

    @Override
    public void onDeckCreationSuccess(String message) {
        Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        deckPresenter.refreshDecks();
    }

    @Override
    public void onDeckCreationFailure(String message) {
        Toast.makeText(getActivity().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}