package net.project.studyez;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.WindowManager;

import net.project.studyez.view.IntroductionFragment1;
import net.project.studyez.view.IntroductionFragment2;
import net.project.studyez.view.IntroductionFragment3;

public class IntroductionActivity extends AppCompatActivity {

    private static final int NUM_FRAGS = 3;
    private ViewPager viewPager;
    private ScreenSlideAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_introduction);

        viewPager = findViewById(R.id.pager);
        pagerAdapter = new ScreenSlideAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
    }

    private class ScreenSlideAdapter extends FragmentStatePagerAdapter{

        public ScreenSlideAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    IntroductionFragment1 tab1 = new IntroductionFragment1();
                    return tab1;
                case 1:
                    IntroductionFragment2 tab2 = new IntroductionFragment2();
                    return tab2;
                case 2:
                    IntroductionFragment3 tab3 = new IntroductionFragment3();
                    return tab3;
            }
            return null;
        }

        @Override
        public int getCount() {
            return NUM_FRAGS;
        }
    }
}