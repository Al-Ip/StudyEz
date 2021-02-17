package net.project.studyez.dashboard;

import android.view.View;

import androidx.fragment.app.Fragment;

public interface DashboardContract {

    interface view{
        void changeFragment(Fragment fragment, int id);
        void prepQuickStudy();
    }

    interface presenter{
        void clickQuickStudy(View view);
        void pressOnQuickStudy(Fragment fragment, int id);
    }
}
