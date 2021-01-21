package net.project.studyez.decks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import net.project.studyez.R;
import net.project.studyez.databinding.FragmentDecksBinding;

public class DecksFragment extends Fragment implements DeckContract.view{

    private DeckPresenter deckPresenter;
    private FloatingActionButton floatingActionButton;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.decks_fragment, container, false);
//        return root;
        FragmentDecksBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_decks, container, false);
        View view = binding.getRoot();
        deckPresenter = new DeckPresenter(this);
        binding.setPresenter(deckPresenter);

        floatingActionButton = view.findViewById(R.id.DeckFAB);

        return view;

    }

    public void getDeckNameFromDialog(String name){
        deckPresenter.enterDeckName(getActivity(), name);
    }

    @Override
    public void displayAllDecks() {

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