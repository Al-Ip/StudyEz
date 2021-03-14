package net.project.studyez.registration;

import android.app.Activity;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseUser;

public interface RegistrationContract {

    // implemented by RegisterActivity to provide concrete implementation
    interface View {
        void onNextAnimatedButtonClick();
        void displayLoginActivity();
        void showAnimatedNextButton();
        void hideAnimatedNextButton();
        void showBlackFadeIn();
        void hideBlackFadeIn();
        void showProgressBar();
        void hideProgressBar();
        String getUsernameText();
        void showUsernameError();
        String getEmailText();
        String getPasswordText();
        void showEmailError();
        void showPasswordError();
        void changeFragment(Fragment fragment, int id);
        void onRegistrationSuccess(FirebaseUser firebaseUser);
        void onRegistrationFailure(String message);
        void onRegistrationUpdateSuccess(String message);
        void onRegistrationUpdateFailure(String message);
    }

    // implemented by RegistrationPresenter to handle user event
    interface Presenter {
        void doChangeFragment(Fragment fragment, int id);
        void clickAnimatedNextButton(android.view.View view);
        void toggleAnimatedTextButtonVisibilityForRegPageOne(String email, String password);
        void toggleAnimatedTextButtonVisibilityForRegPageTwo(String username);
        void addEmailAndPasswordToDatabase(Activity activity, String email, String password);
        void clickRegisterButton(android.view.View view);
        void clickLoginText(android.view.View view);
        void addUsernameToDatabase(Activity activity, String username);
    }

    interface Interactor{
        void performFirebaseRegistration(Activity activity, String email, String password);
        void storeUserDetailsInFirebase(Activity activity, String username);
    }

    interface onRegistrationListener{
        void onRegSuccess(FirebaseUser firebaseUser);
        void onRegFailure(String message);
    }

    interface onRegistrationUpdateListener{
        void onRegUpdateSuccess(String message);
        void onRegUpdateFailure(String message);
    }
}
