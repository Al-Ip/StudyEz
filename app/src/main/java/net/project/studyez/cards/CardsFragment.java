package net.project.studyez.cards;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import net.project.studyez.R;
import net.project.studyez.databinding.FragementCardBinding;
import net.project.studyez.main.MainActivity;

import static net.project.studyez.decks.DecksFragment.deckName;

public class CardsFragment extends Fragment implements CardContract.view {

    private int docID;
    private String deckNameAssociatedWithCards;
    private CardPresenter cardPresenter;
    private FloatingActionButton addCard;
    private RecyclerView cardRecycler;
    private CardAdapter cardAdapter;
    private Toolbar toolbar;
    private TextView emptyCardText;
    private ImageView emptyCardArrow;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragementCardBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragement_card, container, false);

        View view = binding.getRoot();
        cardPresenter = new CardPresenter(this);
        binding.setPresenter(cardPresenter);

        emptyCardText= view.findViewById(R.id.empty_cards_text);
        emptyCardArrow = view.findViewById(R.id.empty_cards_arrow);
        toolbar = view.findViewById(R.id.cardToolbar);
        addCard = view.findViewById(R.id.cardsFAB);
        cardRecycler = view.findViewById(R.id.cardsRecycler);

        initToolbar();
        setUpRecyclerView(cardPresenter.getCardsFromDeck(getActivity(), getDeckNameFromDecksFragment()));

        return view;
    }

    private void initToolbar(){
        toolbar.setTitle(getDeckNameFromDecksFragment());
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setTitleTextColor(color(R.color.white));
        toolbar.setNavigationOnClickListener(v -> getActivity().onBackPressed());
    }

    @ColorInt
    public int color(@ColorRes int res){
        return ContextCompat.getColor(getContext(), res);
    }

    private void setUpRecyclerView(FirestoreRecyclerOptions<Card> options){
        cardAdapter = new CardAdapter(options, cardPresenter){
            @Override
            public void onDataChanged() {
                super.onDataChanged();
                if(cardAdapter.getItemCount() == 0)
                    cardPresenter.showEmptyCardsMessage();
                else{
                    cardPresenter.hideEmptyCardsMessage();
                    cardPresenter.getNumberOfCards(getDeckNameFromDecksFragment(), cardAdapter.getItemCount());
                }
            }
        };
        cardRecycler.setHasFixedSize(true);
        cardRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        cardRecycler.setAdapter(cardAdapter);
    }

    public String getDeckNameFromDecksFragment(){
        return deckNameAssociatedWithCards = deckName;
    }

    @Override
    public void createCardDialogConfirm(String deckName, String question, String answer, String dateTimeCreated, boolean isStarred) {
        cardPresenter.getCardDetails(deckName, question, answer, dateTimeCreated, isStarred);
    }

    @Override
    public void changeFragment(Fragment fragment, int id) {
        ((MainActivity) getActivity()).changeFragment(fragment, id, true);
    }

    @Override
    public void frontAndBackOfCardText(String question, String answer) {
        cardPresenter.editCardDetails(getDeckNameFromDecksFragment(), question, answer, cardAdapter.cardID);
    }

    @Override
    public void displayEmptyCardsMessage() {
        emptyCardText.setVisibility(View.VISIBLE);
        emptyCardArrow.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideEmptyCardsMessage() {
        emptyCardText.setVisibility(View.GONE);
        emptyCardArrow.setVisibility(View.GONE);
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
    public void displayEditCardPopupWindow(String question, String answer) {
        EditCardDialog editCardDialog = new EditCardDialog(question, answer);
        editCardDialog.show(getChildFragmentManager(), "Edit Card Dialog");
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
    public void onCardEditSuccess(String message) {
        Toast.makeText(requireActivity().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCardEditFailure(String message) {
        Toast.makeText(requireActivity().getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart() {
        super.onStart();
        cardAdapter.startListening();
        ((MainActivity)getActivity()).getSupportActionBar().hide();
    }

    @Override
    public void onStop() {
        super.onStop();
        cardAdapter.stopListening();
        ((MainActivity)getActivity()).getSupportActionBar().show();
    }
}
