package net.project.studyez.statistics.deck_graph;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.IMarker;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import net.project.studyez.R;
import net.project.studyez.statistics.CustomMarkerGraph;

import java.util.ArrayList;
import java.util.Map;

public class DecksGraphFragment extends Fragment implements DecksGraphContract.View{

    private BarChart barChart;
    private TextView weekText;
    private DecksGraphPresenter graphPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_graph_decks, container, false);
        graphPresenter = new DecksGraphPresenter(this);

        barChart = view.findViewById(R.id.barChart);
        weekText = view.findViewById(R.id.barChart_text_week);

        graphPresenter.getCurrentWeekDate(0);
        initBarChart();
        graphPresenter.getBarChartData();

        return view;
    }

    @ColorInt
    public int color(@ColorRes int res){
        return ContextCompat.getColor(requireContext(), res);
    }

    @Override
    public void setWeekDateTextField(String date) { weekText.setText(date); }

    @Override
    public void initBarChart() {
        barChart.setTouchEnabled(true);
        barChart.setDragEnabled(false);
        barChart.setScaleEnabled(false);
        barChart.setPinchZoom(false);
        barChart.setDrawGridBackground(false);
        barChart.getAxisRight().setDrawGridLines(false);
        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.getXAxis().setDrawGridLines(false);
        barChart.setViewPortOffsets(0, 0, 0, 0);

        IMarker marker = new CustomMarkerGraph(getContext(), R.layout.custom_marker_view_layout);
        barChart.setMarker(marker);

    }

    @Override
    public void setBarChartWithData(Map<String, Integer> deckData) {
        Description description = new Description();
        description.setText("");
        barChart.setDescription(description);

        int counter = 0;
        ArrayList<BarEntry> entryArrayList = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : deckData.entrySet()) {
            entryArrayList.add(new BarEntry(counter++, entry.getValue().floatValue(), entry.getKey()));
        }

//        ArrayList<BarEntry> entryArrayList = new ArrayList<>();
//        entryArrayList.add(new BarEntry(0, 2f, "Deck 1"));
//        entryArrayList.add(new BarEntry(1, 0f, "Deck 2"));
//        entryArrayList.add(new BarEntry(2, 4f, "Deck 3"));
//        entryArrayList.add(new BarEntry(3, 5f, "Deck 4"));
//        entryArrayList.add(new BarEntry(4, 1f, "Deck 5"));
//        entryArrayList.add(new BarEntry(5, 6f, "Deck 6"));
//        entryArrayList.add(new BarEntry(6, 2f, "Deck 7"));

        //LineDataSet is the line on the graph
        BarDataSet barDataSet = new BarDataSet(entryArrayList, "This is y bill");

        barDataSet.setGradientColor(color(R.color.custom_background_grey), color(R.color.default_blue));
        //barDataSet.setColor(color(R.color.default_blue));
        barDataSet.setDrawValues(false);

        //set legend disable or enable to hide {the left down corner name of graph}
        Legend legend = barChart.getLegend();
        legend.setEnabled(false);

        ArrayList<IBarDataSet> iLineDataSetArrayList = new ArrayList<>();
        iLineDataSetArrayList.add(barDataSet);

        //LineData is the data accord
        BarData barData = new BarData(iLineDataSetArrayList);
        barData.setValueTextSize(13f);
        barData.setValueTextColor(Color.BLACK);

        barChart.setData(barData);
        barChart.invalidate();
    }
}
