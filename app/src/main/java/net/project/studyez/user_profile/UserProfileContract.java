package net.project.studyez.user_profile;

import androidx.fragment.app.Fragment;

public interface UserProfileContract {

    // implemented by RegisterActivity to provide concrete implementation
    interface View {
        void displayUserInformation(User user);
        void changeFragment(Fragment fragment, int id);
        void updateButtonClick(String message);
    }

    // implemented by RegistrationPresenter to handle user event
    interface Presenter {
        void doChangeFragment(Fragment fragment, int id);
        void getUserDetails();
        void clickUpdateProfileButton(android.view.View view);
    }

    interface Interactor{
        void getUserDetailsFromFirebase();
    }

    interface onGetInfoListener{
        void onGetInfoSuccess(User user);
        void onGetInfoFailure(String message);
    }

}
