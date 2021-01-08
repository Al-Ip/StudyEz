package net.project.studyez.registration.main;

import android.app.Activity;
import androidx.fragment.app.Fragment;


import com.google.firebase.auth.FirebaseUser;

public interface RegistrationContract {

    // implemented by RegisterActivity to provide concrete implementation
    interface View {
        void onNextAnimatedButtonClick();
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
        void onRegistrationSuccess(FirebaseUser firebaseUser);
        void onRegistrationFailure(String message);
    }

    // implemented by RegistrationPresenter to handle user event
    interface Presenter {
        void doChangeFragment(Fragment fragment, int id);
        void clickAnimatedNextButton(android.view.View view);
        void toggleAnimatedTextButtonVisibility(String email, String password);
        void addEmailAndPasswordToDatabase(Activity activity, String email, String password);
    }

    interface Interactor{
        void performFirebaseRegistration(Activity activity, String email, String password);
    }

    interface onRegistrationListener{
        void onSuccess(FirebaseUser firebaseUser);
        void onFailure(String message);
    }
}
