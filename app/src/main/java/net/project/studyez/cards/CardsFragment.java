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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import net.project.studyez.R;
import net.project.studyez.databinding.FragementCardsBinding;

import java.util.ArrayList;

public class CardsFragment extends Fragment implements CardContract.view {

    private ArrayList<Card> cards;
    private CardPresenter cardPresenter;
    private FloatingActionButton addCard;
    private RecyclerView cardRecycler;
    private CardCreatorAdapter cardCreatorAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragementCardsBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragement_cards, container, false);
        View view = binding.getRoot();
        cardPresenter = new CardPresenter(this);
        binding.setPresenter(cardPresenter);

        cards = new ArrayList<>();

        addCard = view.findViewById(R.id.cardsFAB);
        cardRecycler = view.findViewById(R.id.cardsRecycler);
        cardRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        cardCreatorAdapter = new CardCreatorAdapter(cards);
        cardRecycler.setAdapter(cardCreatorAdapter);

        return view;
    }


    @Override
    public void displayEmptyCardsMessage() {

    }

    @Override
    public void hideEmptyCardsMessage() {

    }

    @Override
    public void displayCreateCardPopupWindow() {
        Card card = new Card("", "");
        cardCreatorAdapter.showCardDialog(getView(), card);
        cardRecycler.scrollToPosition(cardCreatorAdapter.getItemCount() - 1);
        Toast.makeText(getContext(), "Clicked Add Card", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void displayDeleteCardPopupWindow() {

    }
}
