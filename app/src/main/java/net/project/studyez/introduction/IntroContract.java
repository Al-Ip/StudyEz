package net.project.studyez.introduction;

public interface IntroContract {

    // implemented by IntroductionFragment4 to provide concrete implementation
    interface View {
        void showRegisterScreen();
        void showLoginScreen();
    }

    // implemented by IntroPresenter to receive response from asynchronous processes
    interface Presenter {
        void handleRegisterButtonClick(android.view.View view);
        void handleLoginButtonClick(android.view.View view);
    }
}
