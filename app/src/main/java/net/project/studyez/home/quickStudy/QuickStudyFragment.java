package net.project.studyez.home.quickStudy;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import net.project.studyez.EXTERNAL.ItemClickSupport;
import net.project.studyez.R;
import net.project.studyez.cards.Card;
import net.project.studyez.databinding.FragmentQuickStudyBinding;
import net.project.studyez.decks.Deck;
import net.project.studyez.decks.DeckAdapter;
import net.project.studyez.flashcard.FlashcardFragment;
import net.project.studyez.main.MainActivity;

import java.util.ArrayList;

public class QuickStudyFragment extends Fragment implements QuickStudyContract.view {

    public int docID;
    public int cardNum;
    public static String deckID;
    public static String deckName;

    private RecyclerView deckRecyclerView;
    private Toolbar toolbar;
    private TextView emptyDeckText;
    private QuickStudyPresenter presenter;
    private DeckAdapter deckAdapter;

    private ArrayList<Card> cards;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Changing the theme before initialization
        getContext().setTheme(R.style.QuickStudy_Theme_App);

        FragmentQuickStudyBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_quick_study, container, false);
        View view = binding.getRoot();
        presenter = new QuickStudyPresenter(this);
        binding.setPresenter(presenter);

        deckRecyclerView = view.findViewById(R.id.deckRecyclerView);
        toolbar =  view.findViewById(R.id.quick_study_toolbar);
        emptyDeckText = view.findViewById(R.id.emptyDecksText);

        initToolbar();
        setUpRecyclerView(presenter.getDecks(getActivity()));
        // Single Click support
        ItemClickSupport.addTo(deckRecyclerView).setOnItemClickListener((recyclerView, position, v) -> {
            docID = position;
            deckName = deckAdapter.getSnapshots().getSnapshot(docID).get("name").toString();
            deckID = deckAdapter.getSnapshots().getSnapshot(docID).getId();
            cardNum = Integer.parseInt(deckAdapter.getSnapshots().getSnapshot(docID).get("numCards").toString());
            presenter.shortPressOnDeck(new FlashcardFragment(), R.id.main_container, cardNum);
        });

        return view;
    }

    private void setUpRecyclerView(FirestoreRecyclerOptions<Deck> options){
        deckAdapter = new DeckAdapter(options){
            @Override
            public void onDataChanged() {
                super.onDataChanged();
                if(deckAdapter.getItemCount() == 0)
                    presenter.showNoDeckMessage();
                else
                    presenter.hideNoDeckMessage();
            }
        };
        deckRecyclerView.setHasFixedSize(true);
        deckRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        deckRecyclerView.setAdapter(deckAdapter);
    }

    private void initToolbar(){
        toolbar.setTitle(R.string.toolbar_quickStudy);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24_white);
        toolbar.setNavigationOnClickListener(v -> getActivity().onBackPressed());
    }

    //if i want to show transparent status bar but navigation bar should be same then i use true in first parameters of method and for clearing that flag just use false.
    // Same way i am using true for complete fullscreen and false for clearing fullscreen flags
    public void transparentStatusBar(Activity activity, boolean isTransparent, boolean fullscreen) {
        int defaultStatusBarColor = color(R.color.default_blue);
        if (isTransparent){
            activity.setTheme(R.style.QuickStudy_Theme_App);
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            ((AppCompatActivity) requireActivity()).getSupportActionBar().hide();
            defaultStatusBarColor = activity.getWindow().getStatusBarColor();
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // FOR TRANSPARENT NAVIGATION BAR
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        }else {
            if (fullscreen){
                View decorView = activity.getWindow().getDecorView();
                int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN;
                decorView.setSystemUiVisibility(uiOptions);
            }else {
                ((AppCompatActivity) requireActivity()).getSupportActionBar().show();
                activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
                activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                activity.getWindow().setStatusBarColor(defaultStatusBarColor);
                activity.setTheme(R.style.Theme_StudyEz_NoActionBar);
            }
        }
    }

    // created a method which clears desired flags at onStop and add flags at onResume
    @Override
    public void onResume() {
        super.onResume();
        transparentStatusBar(getActivity(), true, false);
    }
    @Override
    public void onStart() {
        super.onStart();
        deckAdapter.startListening();
    }
    @Override
    public void onStop() {
        super.onStop();
        transparentStatusBar(getActivity(), false, false);
        deckAdapter.stopListening();
    }

    @ColorInt
    public int color(@ColorRes int res){
        return ContextCompat.getColor(getContext(), res);
    }

    @Override
    public void displayNoDeckMessage() {
        emptyDeckText.setVisibility(View.VISIBLE);
    }

    @Override
    public void noCardsInDeckMessage() {
        Toast.makeText(getContext(), "No Cards In Deck, Try adding some before studying", Toast.LENGTH_LONG).show();
    }

    @Override
    public void hideNoDeckMessage() {
        emptyDeckText.setVisibility(View.GONE);
    }

    @Override
    public void changeFragment(Fragment fragment, int id) {
        ((MainActivity) getActivity()).changeFragment(fragment, id, true);
    }
}
