package net.project.studyez.statistics.graphs;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.IMarker;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import net.project.studyez.R;
import net.project.studyez.statistics.time_graph.TimeStudiedCustomGraphMarker;

import java.util.ArrayList;

public class DecksGraphFragment extends Fragment {

    private LineChart lineChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_graph_decks, container, false);

        lineChart = view.findViewById(R.id.lineChart);

        initLineChartDownFill();

        return view;
    }

    @ColorInt
    public int color(@ColorRes int res){
        return ContextCompat.getColor(requireContext(), res);
    }

    private void initLineChartDownFill() {
        lineChart.setTouchEnabled(true);
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);
        lineChart.setPinchZoom(true);
        lineChart.setDrawGridBackground(false);
        lineChart.getAxisRight().setDrawGridLines(false);
        lineChart.getAxisLeft().setDrawGridLines(false);
        lineChart.getXAxis().setDrawGridLines(false);
        lineChart.setMaxHighlightDistance(200);
        lineChart.setViewPortOffsets(0, 0, 0, 0);

        IMarker marker = new TimeStudiedCustomGraphMarker(getContext(), R.layout.custom_marker_view_layout);
        lineChart.setMarker(marker);

        lineChartDownFillWithData();

    }

    private void lineChartDownFillWithData() {
        Description description = new Description();
        description.setText("");
        lineChart.setDescription(description);

        ArrayList<Entry> entryArrayList = new ArrayList<>();
        entryArrayList.add(new Entry(0, 2f, "1"));
        entryArrayList.add(new Entry(1, 0f, "2"));
        entryArrayList.add(new Entry(2, 4f, "3"));
        entryArrayList.add(new Entry(3, 5f, "4"));
        entryArrayList.add(new Entry(4, 1f, "5"));
        entryArrayList.add(new Entry(5, 6f, "6"));
        entryArrayList.add(new Entry(6, 2f, "7"));

        //LineDataSet is the line on the graph
        LineDataSet lineDataSet = new LineDataSet(entryArrayList, "This is y bill");

        lineDataSet.setLineWidth(3f);
        lineDataSet.setColor(color(R.color.default_blue));
        lineDataSet.setCircleHoleColor(color(R.color.babyBlue));
        //lineDataSet.setCircleColor(color(R.color.pink));
        //lineDataSet.setHighLightColor(Color.RED);
        lineDataSet.setDrawHorizontalHighlightIndicator(false);
        lineDataSet.setDrawVerticalHighlightIndicator(false);
        lineDataSet.setDrawValues(false);
        lineDataSet.setCircleRadius(8f);
        lineDataSet.setCircleColor(color(R.color.QuickStudyColorTheme));

        //to make the smooth line as the graph is adrapt change so smooth curve
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        //to enable the cubic density : if 1 then it will be sharp curve
        lineDataSet.setCubicIntensity(0.2f);

        //to fill the below of smooth line in graph
        lineDataSet.setDrawFilled(true);
        lineDataSet.setFillColor(color(R.color.default_blue));
        //set the transparency
        lineDataSet.setFillAlpha(80);

        //set the gradiant then the above draw fill color will be replace
        Drawable drawable = ContextCompat.getDrawable(requireContext(), R.drawable.fade_blue);
        lineDataSet.setFillDrawable(drawable);
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        //set legend disable or enable to hide {the left down corner name of graph}
        Legend legend = lineChart.getLegend();
        legend.setEnabled(false);

        //to add/ remove the circle from the graph
        lineDataSet.setDrawCircles(true);

        //lineDataSet.setColor(ColorTemplate.COLORFUL_COLORS);

        ArrayList<ILineDataSet> iLineDataSetArrayList = new ArrayList<>();
        iLineDataSetArrayList.add(lineDataSet);

        //LineData is the data accord
        LineData lineData = new LineData(iLineDataSetArrayList);
        lineData.setValueTextSize(13f);
        lineData.setValueTextColor(Color.BLACK);

        lineChart.setData(lineData);
        lineChart.invalidate();
    }

}
