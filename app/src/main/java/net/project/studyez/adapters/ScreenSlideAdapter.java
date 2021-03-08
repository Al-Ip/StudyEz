package net.project.studyez.adapters;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;

import net.project.studyez.introduction.IntroductionFragment1;
import net.project.studyez.introduction.IntroductionFragment2;
import net.project.studyez.introduction.IntroductionFragment3;
import net.project.studyez.introduction.IntroductionFragment4;

import org.jetbrains.annotations.NotNull;


public class ScreenSlideAdapter extends FragmentStatePagerAdapter {

    // This variable is used to tell how many fragments exist so that the slider appears
    private static final int NUM_FRAGMENTS = 4;
    private FragmentTransaction cleanupTransaction;
    private final FragmentManager fragmentManager;
    private final Fragment[] subFragments = new Fragment[NUM_FRAGMENTS];

    public ScreenSlideAdapter(@NonNull FragmentManager fm) {
        super(fm);
        this.fragmentManager = fm;
    }

    @NotNull
    @Override
    public Fragment getItem(int position) {
        return getSubFragmentAtPosition(position);
    }

    private Fragment getSubFragmentAtPosition(int position){
        switch (position){
            case 0:
                return new IntroductionFragment1();
            case 1:
                return new IntroductionFragment2();
            case 2:
                return new IntroductionFragment3();
            case 3:
                return new IntroductionFragment4();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return NUM_FRAGMENTS;
    }

    //The next three methods are needed to remove fragments no longer used from the fragment manager
    @Override
    public void startUpdate(ViewGroup container) {
        super.startUpdate(container);
        cleanupTransaction = fragmentManager.beginTransaction();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        cleanupTransaction.remove((Fragment) object);
    }

    @Override
    public void finishUpdate(ViewGroup container) {
        super.finishUpdate(container);
        cleanupTransaction.commit();
    }
}