package net.project.studyez.study_session;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import net.project.studyez.R;
import net.project.studyez.home.quickStudy.QuickStudyFragment;
import net.project.studyez.main.MainActivity;

public class StudySessionFinishedFragment extends Fragment implements StudySessionContract.view{

    private Toolbar toolbar;
    private TextView deckName, numCards, ansCorrect, timeToFinish;
    private StudySessionPresenter presenter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_study_session_finished, container, false);

        presenter = new StudySessionPresenter(this);

        toolbar = root.findViewById(R.id.sessionToolbar);
        deckName = root.findViewById(R.id.deckNameTextField);
        numCards = root.findViewById(R.id.numCardsTextField);
        ansCorrect = root.findViewById(R.id.ansCorrectTextField);
        timeToFinish = root.findViewById(R.id.timeFinishedTextField);

        initToolbar();
        presenter.getStudySessionStatistics();

        return root;
    }

    private void initToolbar(){
        toolbar.setTitle(R.string.toolbar_studySessionCompleted);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setTitleTextColor(color(R.color.white));
        toolbar.setNavigationOnClickListener(v -> ((MainActivity) requireActivity()).changeFragment(new QuickStudyFragment(), R.id.main_container, false));
    }

    @ColorInt
    public int color(@ColorRes int res){
        return ContextCompat.getColor(getContext(), res);
    }

    @Override
    public void displayOnGetStatsSuccess(String deckName, int numCards, int ansCorrect, String timeToFinish) {
        this.deckName.setText(deckName);
        this.numCards.setText(String.valueOf(numCards));
        this.ansCorrect.setText(String.valueOf(ansCorrect));
        this.timeToFinish.setText(timeToFinish);
    }

    @Override
    public void displayOnGetStatsFailure(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart() {
        super.onStart();
        ((MainActivity)getActivity()).getSupportActionBar().hide();
    }
    @Override
    public void onStop() {
        super.onStop();
        ((MainActivity)getActivity()).getSupportActionBar().show();
    }
}
