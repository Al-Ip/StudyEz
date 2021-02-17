package net.project.studyez;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import net.project.studyez.dashboard.DashboardFragment;
import net.project.studyez.decks.DecksFragment;
import net.project.studyez.drawer.DrawerAdapter;
import net.project.studyez.drawer.DrawerItem;
import net.project.studyez.drawer.SimpleItem;
import net.project.studyez.drawer.SpaceItem;
import net.project.studyez.splashScreen.SplashScreenActivity;
import net.project.studyez.view.AboutUsFragment;
import net.project.studyez.view.MyProfileFragment;
import net.project.studyez.view.SettingsFragment;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements DrawerAdapter.OnItemSelectedListener{

    private static final int POS_CLOSE = 0;
    private static final int POS_DASHBOARD = 1;
    private static final int POS_MY_PROFILE = 2;
    private static final int POS_DECKS = 3;
    private static final int POS_SETTINGS = 4;
    private static final int POS_ABOUT_US = 5;
    private static final int POS_LOGOUT = 7;

    private String[] screenTitles;
    private Drawable[] screenIcons;

    private SlidingRootNav slidingRootNav;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar();

        slidingRootNav = new SlidingRootNavBuilder(this)
                .withDragDistance(180)
                .withRootViewScale(0.75f)
                .withRootViewElevation(25)
                .withToolbarMenuToggle(toolbar)
                .withMenuOpened(false)
                .withContentClickableWhenMenuOpened(false)
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.drawer_menu)
                .inject();

        screenIcons = loadScreenIcons();
        screenTitles = loadScreenTitles();

        DrawerAdapter adapter = new DrawerAdapter(Arrays.asList(
                createItemFor(POS_CLOSE),
                createItemFor(POS_DASHBOARD).setChecked(true),
                createItemFor(POS_MY_PROFILE),
                createItemFor(POS_DECKS),
                createItemFor(POS_SETTINGS),
                createItemFor(POS_ABOUT_US),
                new SpaceItem(260),
                createItemFor(POS_LOGOUT)
        ));
        adapter.setListener(this);

        RecyclerView list = findViewById(R.id.drawer_list);
        list.setNestedScrollingEnabled(false);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);

        adapter.setSelected(POS_DASHBOARD);
    }

    public void initToolbar(){
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.toolbar_dashboard);
        toolbar.setTitleTextColor(color(R.color.white));
        setSupportActionBar(toolbar);
    }

    @SuppressWarnings("rawtypes")
    public DrawerItem createItemFor (int position){
        return new SimpleItem(screenIcons[position], screenTitles[position])
                .withIconTint(color(R.color.default_blue))
                .withTextTint(color(R.color.black))
                .withSelectedIconTint(color(R.color.selected_icon_tint))
                .withSelectedTextTint(color(R.color.selected_text_tint));
    }

    @ColorInt
    public int color(@ColorRes int res){
        return ContextCompat.getColor(this, res);
    }

    public  String[] loadScreenTitles(){
        return getResources().getStringArray(R.array.id_activityScreenTitles);

    }
    public Drawable[] loadScreenIcons(){
        TypedArray ta = getResources().obtainTypedArray(R.array.id_activityScreenIcons);
        Drawable[] icons = new Drawable[ta.length()];
        for(int i = 0; i < ta.length(); i++){
            int id = ta.getResourceId(i, 0);
            if(id != 0){
                icons[i] = ContextCompat.getDrawable(this, id);
            }
        }
        ta.recycle();
        return icons;
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
            finish();
        }else if(count == 1){
            super.onBackPressed();
            Toast.makeText(this, "Warning: Closing Application on Next Back Press", Toast.LENGTH_LONG).show();
        }
        else {
            getSupportFragmentManager().popBackStack();
        }

    }

    @Override
    public void onItemSelected(int position) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (position == POS_DASHBOARD){
            toolbar.setTitle(R.string.toolbar_dashboard);
            DashboardFragment dashboardFragment = new DashboardFragment();
            transaction.replace(R.id.main_container, dashboardFragment);
        }
        else if (position == POS_MY_PROFILE) {
            toolbar.setTitle(R.string.toolbar_profile);
            MyProfileFragment myProfileFragment = new MyProfileFragment();
            transaction.replace(R.id.main_container, myProfileFragment);
        }
        else if(position == POS_DECKS){
            toolbar.setTitle(R.string.toolbar_decks);
            DecksFragment decksFragment = new DecksFragment();
            transaction.replace(R.id.main_container, decksFragment);
        }
        else if (position == POS_SETTINGS){
            toolbar.setTitle(R.string.toolbar_settings);
            SettingsFragment settingsFragment = new SettingsFragment();
            transaction.replace(R.id.main_container, settingsFragment);
        }
        else if (position == POS_ABOUT_US){
            toolbar.setTitle(R.string.toolbar_about);
            AboutUsFragment aboutUsFragment = new AboutUsFragment();
            transaction.replace(R.id.main_container, aboutUsFragment);
        }
        else if (position == POS_LOGOUT) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(), SplashScreenActivity.class));
            finish();
        }

        slidingRootNav.closeMenu();
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void changeFragment(Fragment fragment, int id) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.addToBackStack(null);//add the transaction to the back stack so the user can navigate back
        ft.replace(id, fragment, "new").commit();
    }



}