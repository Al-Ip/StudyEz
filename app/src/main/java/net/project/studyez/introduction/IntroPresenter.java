package net.project.studyez.introduction;

import android.view.View;

import net.project.studyez.introduction.IntroContract;

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
