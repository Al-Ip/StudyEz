package net.project.studyez.main;

import net.project.studyez.userProfile.User;

public interface MainContract {

    // implemented by RegisterActivity to provide concrete implementation
    interface View {
        void displayUserInformationDialog();
        void noUsernameFoundDialog(User user);
        void onRegistrationUpdateSuccess(String message);
        void onRegistrationUpdateFailure(String message);
        void onGetUserInfoFailure(String message);
    }

    // implemented by RegistrationPresenter to handle user event
    interface Presenter {
        void getUserDetails();
        void setUsername(String username);
    }

    interface Interactor{
        void getUserDetailsFromFirebase();
        void setUsernameForAccount(String username);
    }

    interface onGetInfoListener{
        void onGetInfoSuccess(User user);
        void onGetInfoFailure(String message);
    }

    interface onRegistrationUpdateListener{
        void onRegUpdateSuccess(String message);
        void onRegUpdateFailure(String message);
    }
}
