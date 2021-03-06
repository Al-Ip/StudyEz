package net.project.studyez.login;

import android.app.Activity;

import androidx.fragment.app.Fragment;

public interface LoginContract {

    // implemented by RegisterActivity to provide concrete implementation
    interface view {
        void onNextAnimatedButtonClick();
        void displayRegisterActivity();
        void showAnimatedNextButton();
        void hideAnimatedNextButton();
        void showBlackFadeIn();
        void hideBlackFadeIn();
        void showProgressBar();
        void hideProgressBar();
        String getEmailText();
        String getPasswordText();
        void showEmailError();
        void showPasswordError();
        void changeFragment(Fragment fragment, int id);
        void loginSuccessMessage(String message);
        void loginFailureMessage(String message);
    }

    // implemented by RegistrationPresenter to handle user event
    interface Presenter {
        void doChangeFragment(Fragment fragment, int id);
        void clickRegisterText(android.view.View view);
        void clickAnimatedNextButton(android.view.View view);
        void toggleAnimatedTextButtonVisibility(String email, String password);
        void sendEmailAndPasswordData(Activity activity, String email, String password);
    }

    interface Interactor{
        void performUserLogin(String email, String password);
    }

    interface onLoginListener{
        void onLoginSuccess(String message);
        void onLoginFailure(String message);
    }
}
