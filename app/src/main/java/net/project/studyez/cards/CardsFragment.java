package net.project.studyez.cards;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import net.project.studyez.R;
import net.project.studyez.databinding.FragementCardsBinding;

import static net.project.studyez.decks.DecksFragment.deckName;

public class CardsFragment extends Fragment implements CardContract.view {

    private int docID;
    private String deckNameAssociatedWithCards;
    private CardPresenter cardPresenter;
    private FloatingActionButton addCard;
    private RecyclerView cardRecycler;
    private CardAdapter cardAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragementCardsBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragement_cards, container, false);

        View view = binding.getRoot();
        cardPresenter = new CardPresenter(this);
        binding.setPresenter(cardPresenter);

        addCard = view.findViewById(R.id.cardsFAB);
        cardRecycler = view.findViewById(R.id.cardsRecycler);

        setUpRecyclerView(cardPresenter.getCardsFromDeck(getActivity(), getDeckNameFromDecksFragment()));

        return view;
    }

    private void setUpRecyclerView(FirestoreRecyclerOptions<Card> options){
        cardAdapter = new CardAdapter(options, cardPresenter);
        cardRecycler.setHasFixedSize(true);
        cardRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        cardRecycler.setAdapter(cardAdapter);
    }

    public String getDeckNameFromDecksFragment(){
        return deckNameAssociatedWithCards = deckName;
    }

    @Override
    public void displayEmptyCardsMessage() {

    }

    @Override
    public void hideEmptyCardsMessage() {

    }

    @Override
    public void displayCreateCardPopupWindow() {
        NewCardDialog newCardDialog = new NewCardDialog();
        newCardDialog.show(getChildFragmentManager(), "Card Create Dialog");
    }

    @Override
    public void displayDeleteCardPopupWindow() {
        DeleteCardDialog deleteCardDialog = new DeleteCardDialog();
        deleteCardDialog.show(getChildFragmentManager(), "Delete Card Dialog");
    }

    @Override
    public void displayEditCardPopupWindow() {

    }

    @Override
    public void deleteCardDialogConfirm() {
        cardPresenter.deleteCardFromFirebase(getDeckNameFromDecksFragment(), cardAdapter.cardID);
    }

    @Override
    public void onCardCreationSuccess(String message) {
        Toast.makeText(requireActivity().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        cardAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCardCreationFailure(String message) {
        Toast.makeText(requireActivity().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCardDeletionSuccess(String message) {
        Toast.makeText(requireActivity().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        cardAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCardDeletionFailure(String message) {
        Toast.makeText(requireActivity().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void frontAndBackOfCardText(String deckName, String question, String answer, String dateTimeCreated, boolean isStarred) {
        cardPresenter.getCardDetails(deckName, question, answer, dateTimeCreated, isStarred);
    }

    @Override
    public void onStart() {
        super.onStart();
        cardAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        cardAdapter.stopListening();
    }
}
