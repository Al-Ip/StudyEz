package net.project.studyez.study_session;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import net.project.studyez.R;
import net.project.studyez.home.HomeFragment;
import net.project.studyez.main.MainActivity;

public class StudySessionFinishedFragment extends Fragment {

    private Toolbar toolbar;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_study_session_finished, container, false);

        toolbar = root.findViewById(R.id.sessionToolbar);
        initToolbar();

        return root;
    }

    private void initToolbar(){
        ((MainActivity)getActivity()).getSupportActionBar().hide();
        toolbar.setTitle(R.string.toolbar_studySessionCompleted);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setTitleTextColor(color(R.color.white));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).getSupportActionBar().show();
                ((MainActivity) requireActivity()).changeFragment(new HomeFragment(), R.id.main_container, false);
            }
        });
    }

    @ColorInt
    public int color(@ColorRes int res){
        return ContextCompat.getColor(getContext(), res);
    }
}
