package net.project.studyez.dashboard.quickStudy;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

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

import net.project.studyez.R;
import net.project.studyez.databinding.FragmentQuickStudyBinding;
import net.project.studyez.decks.Deck;
import net.project.studyez.decks.DeckAdapter;

public class QuickStudyFragment extends Fragment implements QuickStudyContract.view {

    private RecyclerView deckRecyclerView;
    private Toolbar toolbar;
    private TextView emptyDeckText;
    private QuickStudyPresenter presenter;
    private DeckAdapter deckAdapter;

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
    public void hideNoDeckMessage() {
        emptyDeckText.setVisibility(View.GONE);
    }

    @Override
    public void changeFragment(Fragment fragment, int id) {

    }
}