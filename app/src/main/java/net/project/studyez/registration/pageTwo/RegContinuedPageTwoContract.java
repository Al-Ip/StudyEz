package net.project.studyez.registration.pageTwo;

import android.view.View;

import androidx.fragment.app.Fragment;

public interface RegContinuedPageTwoContract {

    interface view{
        void onNextAnimatedButtonClick();
        void changeFragment(Fragment fragment, int id);
        void focusMaleImage(float opaque, float transparent);
        void focusFemaleImage(float opaque, float transparent);
        void focusOtherImage(float opaque, float transparent);
        void showAnimatedNextButton();
        void hideAnimatedNextButton();
    }

    interface presenter{
        void doChangeFragment(Fragment fragment, int id);
        void clickAnimatedNextButton(android.view.View view);
        void clickMaleImage(View view);
        void clickFemaleImage(View view);
        void clickOtherImage(View view);
    }

}
