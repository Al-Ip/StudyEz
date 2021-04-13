package net.project.studyez.statistics.time_graph;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.format.DateUtils;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

import net.project.studyez.R;

public class TimeStudiedCustomGraphMarker extends MarkerView {

    private final TextView day;
    private final TextView value;

    public TimeStudiedCustomGraphMarker(Context context, int layoutResource) {
        super(context, layoutResource);
        // this markerview only displays a textview
        day = (TextView) findViewById(R.id.day);
        value = (TextView) findViewById(R.id.value);
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

    private MPPointF mOffset;

    @Override
    public MPPointF getOffset() {
        if(mOffset == null) {
            // center the marker horizontally and vertically
            mOffset = new MPPointF(-(getWidth() / 2), -getHeight());
        }
        return mOffset;
    }
}