package net.project.studyez.registration.pageOne;

import androidx.fragment.app.Fragment;

public interface RegContinuedPageOneContract {

    interface view{
        void onNextAnimatedButtonClick();
        void changeFragment(Fragment fragment, int id);
    }

    interface presenter{
        void doChangeFragment(Fragment fragment, int id);
        void clickAnimatedNextButton(android.view.View view);
    }
}
