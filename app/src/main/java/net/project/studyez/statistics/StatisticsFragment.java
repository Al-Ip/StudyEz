package net.project.studyez.statistics;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import net.project.studyez.R;
import net.project.studyez.databinding.FragmentStatisticsBinding;
import net.project.studyez.main.MainActivity;

import static net.project.studyez.main.MainActivity.slidingRootNav;


public class StatisticsFragment extends Fragment implements StatisticsContract.View {

    private StatisticsPresenter presenter;
    private Toolbar toolbar;
    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private final String[] tabTitles = new String[]{"Time", "Decks", "Cards", "Modes"};
    private final int[] tabIcons = new int[]{R.drawable.time_studied, R.drawable.decks_icon, R.drawable.cards, R.drawable.modes};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentStatisticsBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_statistics, container, false);
        View view = binding.getRoot();
        presenter = new StatisticsPresenter(this);
        binding.setPresenter(presenter);

        toolbar =  view.findViewById(R.id.stats_toolbar);
        initToolbar();

        tabLayout = view.findViewById(R.id.tab_layout);
        viewPager = view.findViewById(R.id.pager);
        viewPager.setAdapter(createCardAdapter());

        return view;
    }

    private ViewPagerAdapter createCardAdapter() {
        return new ViewPagerAdapter(requireActivity());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(tabTitles[position]).setIcon(tabIcons[position])).attach();
    }

    private void initToolbar(){
        toolbar.setTitle(R.string.toolbar_stats);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_menu_24);
        toolbar.setNavigationOnClickListener(v -> { slidingRootNav.openMenu(); });
    }

    @ColorInt
    public int color(@ColorRes int res){
        return ContextCompat.getColor(requireContext(), res);
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
