package net.project.studyez.registration.pageThree;

import android.view.View;

import androidx.fragment.app.Fragment;

public interface RegContinuedPageThreeContract {

    interface view {
        void onNextAnimatedButtonClick();
        void changeFragment(Fragment fragment, int id);
        void showAnimatedNextButton();
        void hideAnimatedNextButton();
        void setDatePicker();
    }

    interface presenter {
        void doChangeFragment(Fragment fragment, int id);
        void clickAnimatedNextButton(View view);
        void clickDatePicker(View view);

    }
}