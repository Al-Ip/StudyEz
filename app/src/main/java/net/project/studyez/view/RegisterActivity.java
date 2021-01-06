package net.project.studyez.view;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.google.android.material.textfield.TextInputEditText;

import net.project.studyez.R;
import net.project.studyez.contracts.RegistrationContract;

import net.project.studyez.databinding.ActivityRegisterBinding;
import net.project.studyez.presenter.RegistrationPresenter;

public class RegisterActivity extends AppCompatActivity implements RegistrationContract.View {

    private TextInputEditText email, password;
    private ImageView nextButton;
    private RegistrationPresenter presenter;


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
        nextButton = findViewById(R.id.registrationPage1NextButton);

        email.addTextChangedListener(textWatcher);
        password.addTextChangedListener(textWatcher);
    }

    // Makes button appear or disappear depending on whether or not user has entered values in both fields
    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String emailInput = getEmailText();
            String passInput = getPasswordText();
            if(!emailInput.isEmpty() && ! passInput.isEmpty())
                showAnimatedNextButton();
            else
                hideAnimatedNextButton();
        }
        @Override
        public void afterTextChanged(Editable s) { }
    };

    @Override
    public void showNextRegistrationScreen() {
        presenter.getEmailAndPasswordValues(getEmailText(), getPasswordText());
        showEmailError();
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
    public String getEmailText() {
        return this.email.getText().toString();
    }

    @Override
    public String getPasswordText() {
        return this.password.getText().toString();
    }

    @Override
    public void showEmailError() {
        email.setError("You need to enter a correct email");
    }

    @Override
    public void showPasswordError() {

    }

}