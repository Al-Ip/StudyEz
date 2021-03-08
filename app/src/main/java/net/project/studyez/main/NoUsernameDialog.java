package net.project.studyez.main;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import net.project.studyez.R;
import net.project.studyez.userProfile.User;

public class NoUsernameDialog extends Dialog implements View.OnClickListener {

    private final Activity mainActivity;
    private Dialog d;
    private EditText username;
    private Button add;
    private final User user;

    public NoUsernameDialog(Activity mainActivity, User user) {
        super(mainActivity);
        this.mainActivity = mainActivity;
        this.user = user;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.custom_dialog_no_username);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        username = findViewById(R.id.noUsernameEditText);
        add = findViewById(R.id.noUsernameButton);
        add.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.noUsernameButton) {
            if(!username.getText().toString().isEmpty()) {
                mainActivity.getParent();
                if (mainActivity instanceof MainActivity)
                    ((MainActivity)mainActivity).getEnteredUsernameText(username.getText().toString());
            }
            else
                username.setError("Must Enter a Username!");
        }
    }

    public void setUsernameAlreadyTakenError(String message){
        if(!message.isEmpty())
            username.setError(message);
    }
}
