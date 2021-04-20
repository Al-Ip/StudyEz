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

    @Override
    public void clickChangeDescription(String description) {
        mInteractor.updateDescriptionInFirebase(description);
    }

    @Override
    public void clickChangeUsername(String username) {

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
    public void onGetInfoSuccess(User user, int countDecks) {
        mView.displayUserInformation(user, countDecks);
    }

    @Override
    public void onGetInfoFailure(String message) {

    }

    @Override
    public void onSetInfoSuccess(String message) {
        mView.onUpdateOfProfileSuccess(message);
    }

    @Override
    public void onSetInfoFailure(String message) {
        mView.onUpdateOfProfileFailed(message);
    }
}
