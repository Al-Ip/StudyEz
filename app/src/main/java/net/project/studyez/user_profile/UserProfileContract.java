package net.project.studyez.user_profile;

import androidx.fragment.app.Fragment;

public interface UserProfileContract {

    // implemented by RegisterActivity to provide concrete implementation
    interface View {
        void displayUserInformation(User user, int countDecks);
        void changeFragment(Fragment fragment, int id);
        void updateButtonClick(String message);
        void updateUsernameDialogConfirm(String description);
        void onUpdateOfProfileSuccess(String message);
        void onUpdateOfProfileFailed(String message);
    }

    // implemented by RegistrationPresenter to handle user event
    interface Presenter {
        void doChangeFragment(Fragment fragment, int id);
        void clickChangeProfileImage(String stringUri);
        void clickChangeDescription(String description);
        void clickChangeUsername(String username);
        void getUserDetails();
        void clickUpdateProfileButton(android.view.View view);
    }

    interface Interactor{
        void getUserDetailsFromFirebase();
        void setUserProfileImageToFirebase(String stringUri);
        void updateDescriptionInFirebase(String description);
        void updateUsername(String username);
    }

    interface onGetInfoListener{
        void onGetInfoSuccess(User user, int countDecks);
        void onGetInfoFailure(String message);
    }

    interface onSetInfoListener{
        void onSetInfoSuccess(String message);
        void onSetInfoFailure(String message);
    }

}
