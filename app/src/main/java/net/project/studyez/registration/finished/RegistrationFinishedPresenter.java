package net.project.studyez.registration.finished;

import android.view.View;

public class RegistrationFinishedPresenter implements RegistrationFinishedContract.presenter{

    // to keep reference to view
    private final RegistrationFinishedContract.view mView;

    public RegistrationFinishedPresenter(RegistrationFinishedContract.view view){
        mView = view;
    }


    @Override
    public void clickAnimatedNextButton(View view) {
        mView.onNextAnimatedButtonClick();
    }
}
