package net.project.studyez.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import net.project.studyez.IntroductionActivity;
import net.project.studyez.R;

public class SplashScreenActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    ImageView animation;
    TextView appName;
    ProgressBar progBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        firebaseAuth = FirebaseAuth.getInstance();
        animation = findViewById(R.id.splash_animation_id);
        appName = findViewById(R.id.app_name_id);
        progBar = findViewById(R.id.progressBarSplash_id);

        animation.animate().translationY(-1600).setDuration(1000).setStartDelay(4000);
        appName.animate().translationY(1400).setDuration(1000).setStartDelay(4000);
        progBar.animate().translationY(1400).setDuration(1000).setStartDelay(4000);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // check if the user is logged in
                if(firebaseAuth.getCurrentUser() != null){
                    startActivity(new Intent(getApplicationContext(), IntroductionActivity.class));
                    finish();
                }
                else{
                    // create new anonymous account if not logged in
                    firebaseAuth.signInAnonymously().addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Toast.makeText(SplashScreenActivity.this, "Logged in with Temporary Account!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), IntroductionActivity.class));
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SplashScreenActivity.this, "Error " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                }
            }
        }, 4000); //change this back to 4000 ... only testing purposes now

    }

}