package net.project.studyez.splashScreen;

import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.firebase.auth.FirebaseAuth;

import net.project.studyez.R;
import net.project.studyez.adapters.ScreenSlideAdapter;

public class SplashScreenActivity extends AppCompatActivity {

    ImageView animation;
    TextView appName;
    ProgressBar progBar;

    private ViewPager viewPager;
    private ScreenSlideAdapter pagerAdapter;
    Animation anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        animation = findViewById(R.id.splash_animation_id);
        appName = findViewById(R.id.app_name_id);
        progBar = findViewById(R.id.progressBarSplash_id);

        viewPager = findViewById(R.id.pager);
        pagerAdapter = new ScreenSlideAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        anim = AnimationUtils.loadAnimation(this, R.anim.intro_anim);
        viewPager.startAnimation(anim);

        animation.animate().translationY(-1600).setDuration(1000).setStartDelay(4000);
        appName.animate().translationY(1400).setDuration(1000).setStartDelay(4000);
        progBar.animate().translationY(1400).setDuration(1000).setStartDelay(4000);

//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                // check if the user is logged in
//                if(firebaseAuth.getCurrentUser() != null){
//                    startActivity(new Intent(getApplicationContext(), IntroductionActivity.class));
//                    finish();
//                }
//                else{
//                    // create new anonymous account if not logged in
//                    firebaseAuth.signInAnonymously().addOnSuccessListener(new OnSuccessListener<AuthResult>() {
//                        @Override
//                        public void onSuccess(AuthResult authResult) {
//                            Toast.makeText(SplashScreenActivity.this, "Logged in with Temporary Account!", Toast.LENGTH_SHORT).show();
//                            startActivity(new Intent(getApplicationContext(), IntroductionActivity.class));
//                            finish();
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Toast.makeText(SplashScreenActivity.this, "Error " + e.getMessage(), Toast.LENGTH_SHORT).show();
//                            finish();
//                        }
//                    });
//                }
//            }
//        }, 4000); //change this back to 4000 ... only testing purposes now

    }

}