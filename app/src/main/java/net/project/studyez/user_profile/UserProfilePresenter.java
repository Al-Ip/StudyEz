package net.project.studyez.user_profile;

import android.view.View;

import androidx.fragment.app.Fragment;

public class UserProfilePresenter implements UserProfileContract.Presenter, UserProfileContract.onGetInfoListener {

    // to keep reference to view
    private final UserProfileContract.View mView;

    // to keep reference to interactor
    private final UserProfileContract.Interactor mInteractor;

    public UserProfilePresenter(UserProfileContract.View view){
        mView = view;
        mInteractor = new UserProfileInteractor(this);
    }


    @Override
    public void doChangeFragment(Fragment fragment, int id) {
        mView.changeFragment(fragment, id);
    }

    @Override
    public void menuClickFiles() {
        mView.displayFileSelector();
    }

    @Override
    public void menuClickCamera() {
        mView.displayPhoneCamera();
    }

    @Override
    public void menuClickDelete() {
        mView.removeProfilePicture();
    }


    @Override
    public void getUserDetails() {
        mInteractor.getUserDetailsFromFirebase();
    }

    @Override
    public void clickUpdateProfileButton(View view) {
        mView.updateButtonClick("Clicked Update Button, Will be implemented soon");
    }

    @Override
    public void clickProfilePicture(View view) {
        mView.displayProfilePictureMenu();
    }

    @Override
    public void onGetInfoSuccess(User user) {
        mView.displayUserInformation(user);
    }

    @Override
    public void onGetInfoFailure(String message) {

    }
}
