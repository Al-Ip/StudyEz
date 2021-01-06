package net.project.studyez.contracts;

public interface IntroContract {

    // implemented by IntroductionFragment4 to provide concrete implementation
    interface View {
        void showRegisterScreen();
    }

    // implemented by IntroPresenter to receive response from asynchronous processes
    interface Presenter {
        void handleRegisterButtonClick(android.view.View view);
    }
}
