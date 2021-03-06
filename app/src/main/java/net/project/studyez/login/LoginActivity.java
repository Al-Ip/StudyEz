package net.project.studyez.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.textfield.TextInputEditText;

import net.project.studyez.MainActivity;
import net.project.studyez.R;
import net.project.studyez.databinding.ActivityLoginBinding;
import net.project.studyez.registration.RegisterActivity;

public class LoginActivity extends AppCompatActivity implements LoginContract.view {

    private TextInputEditText email, password;
    private ImageView nextButton, blackFadeImage;
    private ProgressBar progressBar;
    private LoginPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //setContentView(R.layout.activity_register);

        ActivityLoginBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        presenter = new LoginPresenter(this);
        binding.setPresenter(presenter);

        email = findViewById(R.id.loginEditTextEmail);
        password = findViewById(R.id.loginEditTextPassword);
        nextButton = findViewById(R.id.loginPageNextButton);
        blackFadeImage = findViewById(R.id.loginBlackFadeImage);
        progressBar = findViewById(R.id.loginProgressBar);

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
        presenter.sendEmailAndPasswordData(this, getEmailText(), getPasswordText());
    }

    @Override
    public void displayRegisterActivity() {
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    public void showAnimatedNextButton() {
        nextButton.setVisibility(android.view.View.VISIBLE);
    }

    @Override
    public void hideAnimatedNextButton() {
        nextButton.setVisibility(android.view.View.INVISIBLE);
    }

    @Override
    public void showBlackFadeIn() {
        blackFadeImage.setVisibility(android.view.View.VISIBLE);
    }

    @Override
    public void hideBlackFadeIn() {
        blackFadeImage.setVisibility(android.view.View.INVISIBLE);
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(android.view.View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(android.view.View.INVISIBLE);
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
        if(!TextUtils.isEmpty(password.getText().toString()) && password.getText().toString().length() < 6)
            password.setError("Your password must be at least 6 characters");
    }

    @Override
    public void changeFragment(Fragment fragment, int id) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(id, fragment, "new").commit();
    }

    @Override
    public void loginSuccessMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void loginFailureMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }
}
