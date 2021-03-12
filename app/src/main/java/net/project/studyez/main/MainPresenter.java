package net.project.studyez.main;

import net.project.studyez.user_profile.User;

public class MainPresenter implements MainContract.Presenter, MainContract.onGetInfoListener, MainContract.onRegistrationUpdateListener {

    // to keep reference to view
    private MainContract.View mView;

    // to keep reference to interactor
    private MainContract.Interactor mInteractor;

    public MainPresenter(){
    }

    public MainPresenter(MainContract.View view){
        mView = view;
        mInteractor = new MainInteractor(this, this);
    }

    @Override
    public void getUserDetails() {
        mInteractor.getUserDetailsFromFirebase();
    }

    @Override
    public void setUsername(String username) {
        mInteractor.setUsernameForAccount(username);
    }

    @Override
    public void onGetInfoSuccess(User user) {
        if(user.getUsername() == null)
            mView.noUsernameFoundDialog(user);
        else
            mView.displayUserInformationDialog();
    }

    @Override
    public void onGetInfoFailure(String message) {
        mView.onGetUserInfoFailure(message);
    }

    @Override
    public void onRegUpdateSuccess(String message) {
        mView.onRegistrationUpdateSuccess(message);
    }

    @Override
    public void onRegUpdateFailure(String message) {
        mView.onRegistrationUpdateFailure(message);
    }
}
