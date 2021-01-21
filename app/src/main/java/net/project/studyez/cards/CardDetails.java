//package net.project.studyez.cards;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.text.method.ScrollingMovementMethod;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.WindowManager;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.Toolbar;
//
//import com.google.android.material.floatingactionbutton.FloatingActionButton;
//
//import net.project.studyez.R;
//
//public class CardDetails extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        setContentView(R.layout.activity_note_details);
//
//        //final int colorCode = Note.getRandomCardColor();
//        final Intent data = getIntent();
//
//        TextView content = findViewById(R.id.noteDetailsContent);
//        content.setMovementMethod(new ScrollingMovementMethod());
//
//        TextView title = findViewById(R.id.noteDetailsTitle);
//
//        title.setText(data.getStringExtra("title"));
//        content.setText(data.getStringExtra("content"));
//        content.setBackgroundColor(getResources().getColor(data.getIntExtra("backgroundColor",0), null));
//
//        //Add new edit float button
//        FloatingActionButton fab = findViewById(R.id.editNoteFab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //Send the current clicked note data to the edit note class
//                Intent i = new Intent(v.getContext(), EditNote.class);
//                i.putExtra("title", data.getStringExtra("title"));
//                i.putExtra("content", data.getStringExtra("content"));
//                i.putExtra("backgroundColor", data.getIntExtra("backgroundColor",0));
//                i.putExtra("noteID", data.getStringExtra("noteID"));
//                startActivity(i);
//            }
//        });
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if(item.getItemId() == android.R.id.home)
//            onBackPressed();
//        return super.onOptionsItemSelected(item);
//    }
//}