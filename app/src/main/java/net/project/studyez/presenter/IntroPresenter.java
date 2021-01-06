package net.project.studyez.presenter;

import android.view.View;

import net.project.studyez.contracts.IntroContract;

public class IntroPresenter implements IntroContract.Presenter {

    // to keep reference to view
    private IntroContract.View mView;

    public IntroPresenter(IntroContract.View view){
        mView = view;
    }

    @Override
    public void handleRegisterButtonClick(View view) {
        mView.showRegisterScreen();
    }
}
