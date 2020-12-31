package net.project.studyez.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import net.project.studyez.MainActivity;
import net.project.studyez.R;

import java.util.Timer;
import java.util.TimerTask;

public class IntroductionFragment3 extends Fragment {

    TextView skip;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_intro3, container, false);

        skip = root.findViewById(R.id.skip_intro3);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(root.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        return root;
    }

}
