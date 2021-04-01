package net.project.studyez.statistics;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import net.project.studyez.R;
import net.project.studyez.databinding.FragmentStatisticsBinding;

import static net.project.studyez.main.MainActivity.slidingRootNav;


public class StatisticsFragment extends Fragment implements StatisticsContract.View {

    private StatisticsPresenter presenter;
    private Toolbar toolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentStatisticsBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_statistics, container, false);
        View view = binding.getRoot();
        presenter = new StatisticsPresenter(this);
        binding.setPresenter(presenter);

        toolbar =  view.findViewById(R.id.stats_toolbar);

        initToolbar();

        return view;
    }

    private void initToolbar(){
        toolbar.setTitle(R.string.toolbar_stats);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_menu_24);
        toolbar.setNavigationOnClickListener(v -> { slidingRootNav.openMenu(); });
    }

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

    @ColorInt
    public int color(@ColorRes int res){
        return ContextCompat.getColor(requireContext(), res);
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
        //deckAdapter.startListening();
    }
    @Override
    public void onStop() {
        super.onStop();
        transparentStatusBar(getActivity(), false, false);
        //deckAdapter.stopListening();
    }
}
