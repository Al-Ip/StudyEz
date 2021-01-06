package net.project.studyez.presenter;

import android.util.Log;
import android.view.View;

import net.project.studyez.contracts.RegistrationContract;

public class RegistrationPresenter implements RegistrationContract.Presenter {

    // to keep reference to view
    private final RegistrationContract.View mView;

    public RegistrationPresenter(RegistrationContract.View view){
        mView = view;
    }

    @Override
    public void handleRegisterButtonClick(View view) {
        mView.showNextRegistrationScreen();
    }

    @Override
    public void getEmailAndPasswordValues(String email, String password) {
        Log.d("TESTING", "editEmailTextField: " + email);
        Log.d("TESTING", "editPasswordTextField: " + password);
        if(!email.isEmpty() && !password.isEmpty()){
            addEmailAndPasswordToDatabase(email, password);
        }
        else
            mView.hideAnimatedNextButton();
    }

    @Override
    public void addEmailAndPasswordToDatabase(String email, String password) {

    }


}
