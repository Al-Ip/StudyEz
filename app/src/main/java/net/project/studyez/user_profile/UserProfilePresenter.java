package net.project.studyez.user_profile;

import android.view.View;

import androidx.fragment.app.Fragment;

public class UserProfilePresenter implements UserProfileContract.Presenter, UserProfileContract.onGetInfoListener, UserProfileContract.onSetInfoListener {

    // to keep reference to view
    private final UserProfileContract.View mView;

    // to keep reference to interactor
    private final UserProfileContract.Interactor mInteractor;

    public UserProfilePresenter(UserProfileContract.View view){
        mView = view;
        mInteractor = new UserProfileInteractor(this, this);
    }


    @Override
    public void doChangeFragment(Fragment fragment, int id) {
        mView.changeFragment(fragment, id);
    }

    @Override
    public void clickChangeProfileImage(String stringUri) {
        mInteractor.setUserProfileImageToFirebase(stringUri);
    }

//    @Override
//    public void menuClickFiles() {
//        mView.displayFileSelector();
//    }
//
//    @Override
//    public void menuClickCamera() {
//        mView.displayPhoneCamera();
//    }
//
//    @Override
//    public void menuClickDelete() {
//        mView.removeProfilePicture();
//    }


    @Override
    public void getUserDetails() {
        mInteractor.getUserDetailsFromFirebase();
    }

    @Override
    public void clickUpdateProfileButton(View view) {
        mView.updateButtonClick("Clicked Update Button, Will be implemented soon");
    }


    @Override
    public void onGetInfoSuccess(User user) {
        mView.displayUserInformation(user);
    }

    @Override
    public void onGetInfoFailure(String message) {

    }

    @Override
    public void onSetInfoSuccess(String message) {
        mView.onProfilePictureSetSuccessfully(message);
    }

    @Override
    public void onSetInfoFailure(String message) {
        mView.onProfilePictureSetFailed(message);
    }
}
