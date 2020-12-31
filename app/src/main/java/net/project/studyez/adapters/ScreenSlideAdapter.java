package net.project.studyez.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import net.project.studyez.view.IntroductionFragment1;
import net.project.studyez.view.IntroductionFragment2;
import net.project.studyez.view.IntroductionFragment3;


public class ScreenSlideAdapter extends FragmentStatePagerAdapter {

    private static final int NUM_FRAGMENTS = 3;

    public ScreenSlideAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new IntroductionFragment1();
            case 1:
                return new IntroductionFragment2();
            case 2:
                return new IntroductionFragment3();
        }
        return null;
    }

    @Override
    public int getCount() {
        return NUM_FRAGMENTS;
    }
}