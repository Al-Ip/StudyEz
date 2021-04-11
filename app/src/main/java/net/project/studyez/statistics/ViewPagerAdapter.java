package net.project.studyez.statistics;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import net.project.studyez.statistics.graphs.CardsGraphFragment;
import net.project.studyez.statistics.graphs.DecksGraphFragment;
import net.project.studyez.statistics.graphs.EzPointsGraphFragment;
import net.project.studyez.statistics.graphs.StudyModesGraphFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {

    private static final int CARD_ITEM_SIZE = 4;

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new DecksGraphFragment();
            case 1:
                return new CardsGraphFragment();
            case 2:
                return new StudyModesGraphFragment();
            case 3:
                return new EzPointsGraphFragment();
        }
        return new DecksGraphFragment();
    }

    @Override
    public int getItemCount() {
        return CARD_ITEM_SIZE;
    }
}