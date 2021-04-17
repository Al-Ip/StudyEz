package net.project.studyez.statistics.time_graph;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.text.format.DateUtils;
import android.view.Display;
import android.view.WindowManager;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;

import net.project.studyez.R;

public class TimeStudiedCustomGraphMarker extends MarkerView {

    private final TextView day;
    private final TextView value;
    private final Context mContext;

    public TimeStudiedCustomGraphMarker(Context context, int layoutResource) {
        super(context, layoutResource);
        this.mContext = context;
        day = findViewById(R.id.day);
        value = findViewById(R.id.value);
    }

    // callbacks every time the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @SuppressLint("SetTextI18n")
    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        String timeInMinutesAndSeconds = DateUtils.formatElapsedTime((long) e.getY());
        day.setText(e.getData().toString());
        value.setText(timeInMinutesAndSeconds);
        super.refreshContent(e, highlight);
    }

    @Override
    public void draw(Canvas canvas, float posx, float posy) {
        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;

        int w = getWidth();
        if ((width - posx - w) < w) {
            posx -= w;
        }
        canvas.translate(posx, posy);
        draw(canvas);
        canvas.translate(-posx, -posy);
    }
}