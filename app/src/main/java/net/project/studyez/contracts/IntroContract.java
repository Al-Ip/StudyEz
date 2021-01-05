package net.project.studyez.contracts;

public interface IntroContract {

    interface View {
        void showRegisterScreen();
    }

    interface Presenter {
        void handleRegisterButtonClick(android.view.View view);
    }
}
