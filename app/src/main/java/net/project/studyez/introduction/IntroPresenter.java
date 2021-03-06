package net.project.studyez.introduction;

import android.view.View;

public class IntroPresenter implements IntroContract.Presenter {

    // to keep reference to view
    private final IntroContract.View mView;

    public IntroPresenter(IntroContract.View view){
        mView = view;
    }

    @Override
    public void handleRegisterButtonClick(View view) {
        mView.showRegisterScreen();
    }

    @Override
    public void handleLoginButtonClick(View view) {
        mView.showLoginScreen();
    }
}
