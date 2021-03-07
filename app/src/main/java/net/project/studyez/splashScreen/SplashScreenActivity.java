package net.project.studyez.splashScreen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.google.firebase.auth.FirebaseAuth;

import net.project.studyez.MainActivity;
import net.project.studyez.R;
import net.project.studyez.adapters.ScreenSlideAdapter;
import net.project.studyez.introduction.IntroductionFragment1;

public class SplashScreenActivity extends AppCompatActivity {

    private ImageView animation;
    private TextView appName;
    private ProgressBar progBar;
    private ViewPager viewPager;
    private ScreenSlideAdapter pagerAdapter;
    private FirebaseAuth firebaseAuth;

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

        firebaseAuth = FirebaseAuth.getInstance();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // check if the user is logged in
                if(firebaseAuth.getCurrentUser() != null){
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }
                else{
                    Fragment fragment = new IntroductionFragment1();
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.main_container, fragment).commit();
                }
            }
        }, 4000); //change this back to 4000 ... only testing purposes now

    }

}