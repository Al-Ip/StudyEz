package net.project.studyez.home;

import android.view.View;

import androidx.fragment.app.Fragment;

public interface HomeContract {

    interface view{
        void changeFragment(Fragment fragment, int id);
        void prepQuickStudy();
    }

    interface presenter{
        void clickQuickStudy(View view);
        void pressOnQuickStudy(Fragment fragment, int id);
    }
}
