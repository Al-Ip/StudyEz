package net.project.studyez.registration;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseUser;

public class RegistrationPresenter implements RegistrationContract.Presenter, RegistrationContract.onRegistrationListener {

    // to keep reference to view
    private final RegistrationContract.View mView;

    // to keep reference to interactor
    private final RegistrationContract.Interactor mInteractor;

    public RegistrationPresenter(RegistrationContract.View view){
        mView = view;
        mInteractor = new RegistrationInteractor(this);
    }

    @Override
    public void clickAnimatedNextButton(View view) {
        mView.showNextRegistrationScreen();
    }

    @Override
    public void toggleAnimatedTextButtonVisibility(String email, String password) {
        if(!email.isEmpty() && !password.isEmpty()){
            mView.showAnimatedNextButton();
        }
        else
            mView.hideAnimatedNextButton();
    }

    @Override
    public void addEmailAndPasswordToDatabase(Activity activity, String email, String password) {
        Log.d("TESTING", "editEmailTextField: " + email);
        Log.d("TESTING", "editPasswordTextField: " + password);
        mView.hideAnimatedNextButton();
        mView.showBlackFadeIn();
        mView.showProgressBar();
        mInteractor.performFirebaseRegistration(activity, email, password);
    }

    @Override
    public void onSuccess(FirebaseUser firebaseUser) {
        mView.onRegistrationSuccess(firebaseUser);

    }

    @Override
    public void onFailure(String message) {
        mView.onRegistrationFailure(message);
        mView.hideBlackFadeIn();
        mView.hideProgressBar();
    }
}
