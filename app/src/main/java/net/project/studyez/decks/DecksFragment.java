package net.project.studyez.decks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import net.project.studyez.EXTERNAL.ItemClickSupport;
import net.project.studyez.R;
import net.project.studyez.cards.CardsFragment;
import net.project.studyez.databinding.FragmentDecksBinding;
import net.project.studyez.main.MainActivity;

import javax.inject.Inject;

public class DecksFragment extends Fragment implements DeckContract.view{

    public int docID;
    public static String deckName;
    public static String deckID;
    @Inject
    public DeckPresenter deckPresenter;
    private RecyclerView deckRecyclerView;
    private ImageView emptyDeck;

    private DeckAdapter deckAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentDecksBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_decks, container, false);
        View view = binding.getRoot();
        deckPresenter = new DeckPresenter(this);
        binding.setPresenter(deckPresenter);

        emptyDeck = view.findViewById(R.id.emptyDecksImage);
        deckRecyclerView = view.findViewById(R.id.deckRecycler);

        setUpRecyclerView(deckPresenter.getDecks(getActivity()));

        // Single Click support
        ItemClickSupport.addTo(deckRecyclerView).setOnItemClickListener((recyclerView, position, v) -> {
            docID = position;
            deckName = deckAdapter.getSnapshots().getSnapshot(docID).get("deckName").toString();
            deckID = deckAdapter.getSnapshots().getSnapshot(docID).getId();
            deckPresenter.shortPressOnDeck(new CardsFragment(), R.id.main_container);
        });
        // Long press to delete Deck
        ItemClickSupport.addTo(deckRecyclerView).setOnItemLongClickListener((recyclerView, position, v) -> {
            docID = position;
            deckName = deckAdapter.getSnapshots().getSnapshot(docID).get("deckName").toString();
            deckID = deckAdapter.getSnapshots().getSnapshot(docID).getId();
            deckPresenter.longPressOnDeck(deckID, deckName);
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
        deckPresenter.deleteDeck(deckAdapter.getSnapshots().getSnapshot(docID).getId());
    }

    @Override
    public void updateDeckNameDialogConfirm(String deckID, String deckName) {
        deckPresenter.updateDeckName(deckID, deckName);
    }

    @Override
    public void displayUpdateDeckNameDialog(String deckID, String deckName) {
        UpdateDeckNameDialog newFragment = new UpdateDeckNameDialog(deckID, deckName);
        newFragment.show(getChildFragmentManager(), "Update Deck Name Dialog");
    }

    @Override
    public void changeFragment(Fragment fragment, int id) {
        ((MainActivity) getActivity()).changeFragment(fragment, id, true);
    }

    @Override
    public void displayMenu(String deckID, String deckName) {
        MenuDeckDialog menuDeckDialog = new MenuDeckDialog(deckID, deckName);
        menuDeckDialog.show(getChildFragmentManager(), "Deck Menu Dialog");
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
    public void onDeckUpdateSuccess(String message) {
        Toast.makeText(requireActivity().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeckUpdateFailure(String message) {
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