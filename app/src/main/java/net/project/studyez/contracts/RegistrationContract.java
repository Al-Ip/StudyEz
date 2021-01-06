package net.project.studyez.contracts;

public interface RegistrationContract {

    // implemented by RegisterActivity to provide concrete implementation
    interface View {
        void showNextRegistrationScreen();
        void showAnimatedNextButton();
        void hideAnimatedNextButton();
        String getEmailText();
        String getPasswordText();
        void showEmailError();
        void showPasswordError();
    }

    // implemented by RegistrationPresenter to handle user event
    interface Presenter {
        void handleRegisterButtonClick(android.view.View view);
        void getEmailAndPasswordValues(String email, String password);
        void addEmailAndPasswordToDatabase(String email, String password);
    }
}
