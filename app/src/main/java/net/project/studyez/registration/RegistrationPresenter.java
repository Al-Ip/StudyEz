package net.project.studyez.registration;

import android.app.Activity;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseUser;

public class RegistrationPresenter implements RegistrationContract.Presenter,
        RegistrationContract.onRegistrationListener,
        RegistrationContract.onRegistrationUpdateListener{


    // to keep reference to view
    private final RegistrationContract.View mView;

    // to keep reference to interactor
    private final RegistrationContract.Interactor mInteractor;

    public RegistrationPresenter(RegistrationContract.View view){
        mView = view;
        mInteractor = new RegistrationInteractor(this,this);
    }

    @Override
    public void doChangeFragment(Fragment fragment, int id) {
        mView.changeFragment(fragment, id);
    }


    @Override
    public void clickAnimatedNextButton(android.view.View view) {
        mView.onNextAnimatedButtonClick();
    }


    @Override
    public void toggleAnimatedTextButtonVisibilityForRegPageOne(String email, String password) {
        if(!email.isEmpty() && !password.isEmpty()){
            mView.showAnimatedNextButton();
        }
        else
            mView.hideAnimatedNextButton();
    }

    @Override
    public void toggleAnimatedTextButtonVisibilityForRegPageTwo(String username) {
        if(!username.isEmpty()){
            mView.showAnimatedNextButton();
        }
        else
            mView.hideAnimatedNextButton();
    }

    @Override
    public void addEmailAndPasswordToDatabase(Activity activity, String email, String password) {
        mView.hideAnimatedNextButton();
        mView.showBlackFadeIn();
        mView.showProgressBar();
        mInteractor.performFirebaseRegistration(activity, email, password);
    }

    @Override
    public void clickRegisterButton(View view) {
        mView.displayLoginActivity();
    }

    @Override
    public void clickLoginText(View view) {
        mView.displayLoginActivity();
    }

    @Override
    public void addUsernameToDatabase(Activity activity, String username) {
        mView.hideAnimatedNextButton();
        mView.showBlackFadeIn();
        mView.showProgressBar();
        mInteractor.storeUserDetailsInFirebase(activity, username);
    }

    @Override
    public void onRegSuccess(FirebaseUser firebaseUser) {
        mView.onRegistrationSuccess(firebaseUser);
    }

    @Override
    public void onRegUpdateSuccess(String message) {
        mView.onRegistrationUpdateSuccess(message);
    }

    @Override
    public void onRegUpdateFailure(String message) {
        mView.onRegistrationUpdateFailure(message);
        mView.hideBlackFadeIn();
        mView.hideProgressBar();
    }

    @Override
    public void onRegFailure(String message) {
        mView.onRegistrationFailure(message);
        mView.hideBlackFadeIn();
        mView.hideProgressBar();
    }

}
