package net.project.studyez.decks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import net.project.studyez.ItemClickSupport;
import net.project.studyez.MainActivity;
import net.project.studyez.R;
import net.project.studyez.cards.CardsFragment;
import net.project.studyez.databinding.FragmentDecksBinding;

public class DecksFragment extends Fragment implements DeckContract.view{

    public int docID;
    public static String deckName;

    private DeckPresenter deckPresenter;

    private RecyclerView deckRecyclerView;
    private ImageView emptyDeck;
    private Toolbar toolbar;

    private DeckAdapter deckAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentDecksBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_decks, container, false);
        View view = binding.getRoot();
        deckPresenter = new DeckPresenter(this);
        binding.setPresenter(deckPresenter);

        toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.toolbar_decks);
        emptyDeck = view.findViewById(R.id.emptyDecksImage);
        deckRecyclerView = view.findViewById(R.id.deckRecycler);

        setUpRecyclerView(deckPresenter.getDecks(getActivity()));

        // Single Click support
        ItemClickSupport.addTo(deckRecyclerView).setOnItemClickListener((recyclerView, position, v) -> {
            docID = position;
            deckName = deckAdapter.getSnapshots().getSnapshot(docID).getId();
            deckPresenter.shortPressOnDeck(new CardsFragment(), R.id.main_container);
        });
        // Long press to delete Deck
        ItemClickSupport.addTo(deckRecyclerView).setOnItemLongClickListener((recyclerView, position, v) -> {
            docID = position;
            deckPresenter.longPressOnDeck();
            return true;
        });

        return view;
    }

    private void setUpRecyclerView(FirestoreRecyclerOptions<Deck> options){
        deckAdapter = new DeckAdapter(options){
            @Override
            public void onDataChanged() {
                super.onDataChanged();
                if(deckAdapter.getItemCount() == 0)
                    deckPresenter.showEmptyDeckMessage();
                else
                    deckPresenter.hideEmptyDeckMessage();
            }
        };
        deckRecyclerView.setHasFixedSize(true);
        deckRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        deckRecyclerView.setAdapter(deckAdapter);
    }

    @Override
    public void getDetailsFromDeckDialog(String deckName, String dateTime, String creator, int numCards){
        deckPresenter.creatingNewDeck(deckName, dateTime, creator, numCards);
    }

    @Override
    public void deleteDeckDialogConfirm(){
        deckPresenter.deleteDeckFromFirebase(deckAdapter.getSnapshots().getSnapshot(docID).getId());
    }

    @Override
    public void changeFragment(Fragment fragment, int id) {
        ((MainActivity) getActivity()).changeFragment(fragment, id);
    }

    @Override
    public void displayNumOfCardsInDeck(int numCards) {
        Toast.makeText(getContext(), "In Deck Now... " + numCards + " Total cards in this deck!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayEmptyDeckMessage() {
        emptyDeck.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEmptyDeckMessage() {
        emptyDeck.setVisibility(View.INVISIBLE);
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
        deckAdapter.notifyDataSetChanged();
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