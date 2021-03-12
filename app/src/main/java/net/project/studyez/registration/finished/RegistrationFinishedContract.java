package net.project.studyez.registration.finished;

public interface RegistrationFinishedContract {

    interface view{
        void onNextAnimatedButtonClick();
    }

    interface presenter{
        void clickAnimatedNextButton(android.view.View view);
    }
}
