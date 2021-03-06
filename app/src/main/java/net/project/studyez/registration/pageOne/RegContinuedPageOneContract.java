package net.project.studyez.registration.pageOne;

public interface RegContinuedPageOneContract {

    interface view{
        void onNextAnimatedButtonClick();
    }

    interface presenter{
        void clickAnimatedNextButton(android.view.View view);
    }
}
