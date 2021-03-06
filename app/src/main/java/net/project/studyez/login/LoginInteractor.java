package net.project.studyez.login;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginInteractor implements LoginContract.Interactor {

    private LoginContract.onLoginListener onLoginListener;
    private final FirebaseFirestore fStore;
    private final FirebaseAuth fAuth;
    private final FirebaseUser fUser;

    public LoginInteractor(){
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();
    }

    public LoginInteractor(LoginContract.onLoginListener onLoginListener){
        this.onLoginListener = onLoginListener;
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();
    }

    @Override
    public void performUserLogin(String email, String password) {
        fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if(task.isSuccessful()) {
                onLoginListener.onLoginSuccess("Successfully Logged in!");
            }else {
                onLoginListener.onLoginFailure(task.getException().toString());
            }
        });
    }
}
