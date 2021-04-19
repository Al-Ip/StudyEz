package net.project.studyez.statistics.time_graph;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
import net.project.studyez.statistics.CustomMarkerGraph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class TimeStudiedGraphFragment extends Fragment implements TimeStudiedGraphContract.View {

    private TextView weekText;
    private LineChart lineChart;
    private TimeStudiedGraphPresenter graphPresenter;

    private long average;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_graph_time_studied, container, false);
        graphPresenter = new TimeStudiedGraphPresenter(this);

        weekText = view.findViewById(R.id.lineChart_text_week);
        lineChart = view.findViewById(R.id.lineChart);

        graphPresenter.getCurrentWeekDate(0);
        initLineChart();
        graphPresenter.getLineChartData();

        return view;
    }

    @ColorInt
    public int color(@ColorRes int res){
        return ContextCompat.getColor(requireContext(), res);
    }

    @Override
    public void initLineChart() {
        lineChart.setTouchEnabled(true);
        lineChart.setDragEnabled(false);
        lineChart.setScaleEnabled(false);
        lineChart.setPinchZoom(false);
        lineChart.setDrawGridBackground(false);
        lineChart.getAxisRight().setDrawGridLines(false);
        lineChart.getAxisLeft().setDrawGridLines(false);
        lineChart.getXAxis().setDrawGridLines(false);
        lineChart.setViewPortOffsets(0, 0, 0, 0);

        IMarker marker = new CustomMarkerGraph(getContext(), R.layout.custom_marker_view_layout);
        lineChart.setMarker(marker);
    }

    @Override
    public void setWeekDateTextField(String date) {
        weekText.setText(date);
    }

    @Override
    public void setLineChartWithData(Map<Integer, Integer> dateMap, long average, long total) {
        Description description = new Description();
        description.setText("");
        lineChart.setDescription(description);

        this.average = average;
        ArrayList<Entry> entryArrayList = new ArrayList<>();
        List<String> dayNames = Arrays.asList("", "Sun", "Mon", "Tue", "Wed", "Thur", "Fri", "Sat", "");
        for (Map.Entry<Integer, Integer> entry : dateMap.entrySet()) {
            entryArrayList.add(new Entry(entry.getKey(), entry.getValue().floatValue(), dayNames.get(entry.getKey())));
        }

        //LineDataSet is the line on the graph
        LineDataSet lineDataSet = new LineDataSet(entryArrayList, "dataset");
        lineDataSet.setLineWidth(3f);
        lineDataSet.setColor(color(R.color.default_blue));
        lineDataSet.setCircleHoleColor(color(R.color.white));
        lineDataSet.setCircleHoleRadius(4f);
        lineDataSet.setDrawHorizontalHighlightIndicator(false);
        lineDataSet.setDrawVerticalHighlightIndicator(false);
        lineDataSet.setDrawValues(false);
        lineDataSet.setCircleRadius(7f);
        lineDataSet.setCircleColor(color(R.color.default_blue));

        //to make the smooth line as the graph is adapt change so smooth curve
        lineDataSet.setMode(LineDataSet.Mode.LINEAR);
        //to enable the cubic density : if 1 then it will be sharp curve
        lineDataSet.setCubicIntensity(0.1f);

        //to fill the below of smooth line in graph
        lineDataSet.setDrawFilled(true);
        lineDataSet.setFillColor(color(R.color.default_blue));
        //set the transparency
        lineDataSet.setFillAlpha(80);

        //set the gradient then the above draw fill color will be replace
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
