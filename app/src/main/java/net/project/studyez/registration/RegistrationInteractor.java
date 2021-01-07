package net.project.studyez.registration;

import android.app.Activity;

import com.google.firebase.auth.FirebaseAuth;

import net.project.studyez.model.User;

public class RegistrationInteractor implements RegistrationContract.Interactor {

    private static final String TAG = RegistrationInteractor.class.getSimpleName();
    private RegistrationContract.onRegistrationListener mOnRegistrationListener;

    public RegistrationInteractor(RegistrationContract.onRegistrationListener onRegistrationListener){
        this.mOnRegistrationListener = onRegistrationListener;
    }

    @Override
    public void performFirebaseRegistration(Activity activity, String email, String password) {
        User user = new User(email, password);
        FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(task -> {
                    if(!task.isSuccessful()){
                        mOnRegistrationListener.onFailure(task.getException().getMessage());
                    }
                    else{
                        mOnRegistrationListener.onSuccess(task.getResult().getUser());
                    }
                });
    }
}
