package net.project.studyez.registration.pageFour;

import android.view.View;

import androidx.fragment.app.Fragment;

public interface RegContinuedPageFourContract {

    interface view {
        void onNextAnimatedButtonClick();
        void changeFragment(Fragment fragment, int id);
    }

    interface presenter {
        void doChangeFragment(Fragment fragment, int id);
        void clickAnimatedNextButton(View view);
    }
}