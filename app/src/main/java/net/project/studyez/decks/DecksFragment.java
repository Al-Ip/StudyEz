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
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.type.DateTime;

import net.project.studyez.ItemClickSupport;
import net.project.studyez.R;
import net.project.studyez.cards.CardsFragment;
import net.project.studyez.databinding.FragmentDecksBinding;
import net.project.studyez.registration.main.RegisterActivity;
import net.project.studyez.registration.pageThree.RegContinuedPageThreeFragment;
import net.project.studyez.view.MainActivity;

public class DecksFragment extends Fragment implements DeckContract.view{

    public String docID;

    private DeckPresenter deckPresenter;
    private RecyclerView deckRecyclerView;
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

        deckAdapter = new FirestoreRecyclerAdapter<Deck, DeckViewHolder>(deckPresenter.getDecks(getActivity())) {
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
                docID = deckAdapter.getSnapshots().getSnapshot(i).getId();
            }
        };
        deckRecyclerView.setLayoutManager(new LinearLayoutManager(container.getContext(), LinearLayoutManager.VERTICAL, false));
        deckRecyclerView.setAdapter(deckAdapter);

        // Single Click support
        ItemClickSupport.addTo(deckRecyclerView).setOnItemClickListener((recyclerView, position, v) -> {
            //Toast.makeText(getContext(), "Tapped on item in recycler list", Toast.LENGTH_SHORT).show();
            deckPresenter.shortPressOnDeck(new CardsFragment(), R.id.main_container);
        });
        // Long press to delete Deck
        ItemClickSupport.addTo(deckRecyclerView).setOnItemLongClickListener((recyclerView, position, v) -> {
            //Toast.makeText(getContext(), "LONG press on item in recycler list", Toast.LENGTH_SHORT).show();
            deckPresenter.longPressOnDeck();
            return true;
        });

        return view;
    }

    @Override
    public void getDeckNameFromDialog(String name, String dateTime){
        deckPresenter.enterDeckName(name, dateTime);
    }

    @Override
    public void deleteDeckDialogConfirm(){
        deckPresenter.deleteDeckFromFirebase(docID);
    }

    @Override
    public void changeFragment(Fragment fragment, int id) {
        ((MainActivity) getActivity()).changeFragment(fragment, id);
//        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
//        ft.addToBackStack(null);//add the transaction to the back stack so the user can navigate back
//        ft.replace(id, fragment, "new").commit();
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
        newFragment.show(getChildFragmentManager(), "Deck Name Dialog");
    }

    @Override
    public void displayDeleteDeckPopupWindow() {
        DeleteDeckDialog newFragment = new DeleteDeckDialog();
        newFragment.show(getChildFragmentManager(), "Delete Deck Dialog");
    }

    @Override
    public void onDeckCreationSuccess(String message) {
        Toast.makeText(requireActivity().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeckCreationFailure(String message) {
        Toast.makeText(requireActivity().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeckDeletionSuccess(String message) {
        Toast.makeText(requireActivity().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        deckAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDeckDeletionFailure(String message) {
        Toast.makeText(requireActivity().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart() {
        super.onStart();
        deckAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        deckAdapter.stopListening();
    }

}