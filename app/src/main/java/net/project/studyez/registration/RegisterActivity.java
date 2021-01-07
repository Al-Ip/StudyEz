package net.project.studyez.registration;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseUser;

import net.project.studyez.R;
import net.project.studyez.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity implements RegistrationContract.View {

    private TextInputEditText email, password;
    private ImageView nextButton, blackFadeImage;
    private ProgressBar progressBar;
    private RegistrationPresenter presenter;
    private Fragment newFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //setContentView(R.layout.activity_register);

        ActivityRegisterBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        presenter = new RegistrationPresenter(this);
        binding.setPresenter(presenter);

        email = findViewById(R.id.editTextEmail);
        password = findViewById(R.id.editTextPassword);
        nextButton = findViewById(R.id.registrationPageNextButton);
        blackFadeImage = findViewById(R.id.blackFadeImage);
        progressBar = findViewById(R.id.registerPage1ProgressBar);

        email.addTextChangedListener(textWatcher);
        password.addTextChangedListener(textWatcher);

    }

    // Makes button appear or disappear depending on whether or not user has entered values in both fields
    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            presenter.toggleAnimatedTextButtonVisibility(getEmailText(), getPasswordText());
        }
        @Override
        public void afterTextChanged(Editable s) {
            showEmailError();
        }
    };

    @Override
    public void onNextAnimatedButtonClick() {
        presenter.addEmailAndPasswordToDatabase(this, getEmailText(), getPasswordText());
    }

    @Override
    public void showAnimatedNextButton() {
        nextButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideAnimatedNextButton() {
        nextButton.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showBlackFadeIn() {
        blackFadeImage.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideBlackFadeIn() {
        blackFadeImage.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public String getEmailText() {
        return this.email.getText().toString();
    }

    @Override
    public String getPasswordText() {
        return this.password.getText().toString();
    }

    @Override
    public void showEmailError() {
        if(!TextUtils.isEmpty(email.getText().toString()) && !android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches())
            email.setError("You need to enter a correct email");
    }

    @Override
    public void showPasswordError() {
    }

    @Override
    public void changeFragment(Fragment fragment, int id) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(id, fragment, "new").commit();
    }

    @Override
    public void onRegistrationSuccess(FirebaseUser firebaseUser) {
        Toast.makeText(getApplicationContext(), "Successfully Registered" , Toast.LENGTH_SHORT).show();
        presenter.setFragment(new RegistrationContinuedPageOne(), R.id.registerLayout);
    }

    @Override
    public void onRegistrationFailure(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

}