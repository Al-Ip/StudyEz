package net.project.studyez.login;

import android.app.Activity;
import android.view.View;

import androidx.fragment.app.Fragment;

public class LoginPresenter implements LoginContract.Presenter, LoginContract.onLoginListener{


    // to keep reference to view
    private final LoginContract.view mView;

    // to keep reference to interactor
    private final LoginContract.Interactor mInteractor;

    public LoginPresenter(LoginContract.view view){
        mView = view;
        mInteractor = new LoginInteractor(this);
    }

    @Override
    public void doChangeFragment(Fragment fragment, int id) {
        mView.changeFragment(fragment, id);
    }

    @Override
    public void clickRegisterText(View view) {
        mView.displayRegisterActivity();
    }

    @Override
    public void clickAnimatedNextButton(View view) {
        mView.onNextAnimatedButtonClick();
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
    public void sendEmailAndPasswordData(Activity activity, String email, String password) {
        mView.hideAnimatedNextButton();
        mView.showBlackFadeIn();
        mView.showProgressBar();
        mInteractor.performUserLogin(email, password);
    }

    @Override
    public void onLoginSuccess(String message) {
        mView.loginSuccessMessage(message);
        mView.hideBlackFadeIn();
        mView.hideProgressBar();
    }

    @Override
    public void onLoginFailure(String message) {
        mView.loginFailureMessage(message);
        mView.hideBlackFadeIn();
        mView.hideProgressBar();
    }
}
