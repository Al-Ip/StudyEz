package net.project.studyez.statistics;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.IMarker;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import net.project.studyez.R;
import net.project.studyez.databinding.FragmentStatisticsBinding;
import net.project.studyez.main.CustomMarkerView;

import java.util.ArrayList;

import static net.project.studyez.main.MainActivity.slidingRootNav;


public class StatisticsFragment extends Fragment implements StatisticsContract.View {

    private StatisticsPresenter presenter;
    private Toolbar toolbar;
    private LineChart lineChart;
    private BarChart barChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentStatisticsBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_statistics, container, false);
        View view = binding.getRoot();
        presenter = new StatisticsPresenter(this);
        binding.setPresenter(presenter);

        toolbar =  view.findViewById(R.id.stats_toolbar);
        lineChart = view.findViewById(R.id.lineChart);
        barChart = view.findViewById(R.id.barChart);

        initToolbar();
        initLineChartDownFill();

        return view;
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

        IMarker marker = new CustomMarkerView(getContext(), R.layout.custom_marker_view_layout);
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

    private void initCharts(){
        BarData barData = new BarData();
        barData.addEntry(new Entry(10, 25) , 0);
        barData.addEntry(new Entry(15, 30) , 1);
        barData.addEntry(new Entry(3, 12) , 2);
        barData.addEntry(new Entry(63, 50) , 3);

        barChart.setData(barData);
    }

    private void setupGradient(LineChart mChart) {
        Paint paint = mChart.getRenderer().getPaintRender();
        int height = mChart.getHeight();

        LinearGradient linGrad = new LinearGradient(0, 0, 0, height,
                color(android.R.color.holo_green_light),
                color(android.R.color.holo_red_light),
                Shader.TileMode.REPEAT);
        paint.setShader(linGrad);
    }

    private void initToolbar(){
        toolbar.setTitle(R.string.toolbar_stats);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_menu_24);
        toolbar.setNavigationOnClickListener(v -> { slidingRootNav.openMenu(); });
    }

    public void transparentStatusBar(Activity activity, boolean isTransparent, boolean fullscreen) {
        int defaultStatusBarColor = color(R.color.default_blue);
        if (isTransparent){
            activity.setTheme(R.style.QuickStudy_Theme_App);
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            ((AppCompatActivity) requireActivity()).getSupportActionBar().hide();
            defaultStatusBarColor = activity.getWindow().getStatusBarColor();
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // FOR TRANSPARENT NAVIGATION BAR
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        }else {
            if (fullscreen){
                View decorView = activity.getWindow().getDecorView();
                int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN;
                decorView.setSystemUiVisibility(uiOptions);
            }else {
                ((AppCompatActivity) requireActivity()).getSupportActionBar().show();
                activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
                activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                activity.getWindow().setStatusBarColor(defaultStatusBarColor);
                activity.setTheme(R.style.Theme_StudyEz_NoActionBar);
            }
        }
    }

    @ColorInt
    public int color(@ColorRes int res){
        return ContextCompat.getColor(requireContext(), res);
    }

    // created a method which clears desired flags at onStop and add flags at onResume
    @Override
    public void onResume() {
        super.onResume();
        transparentStatusBar(getActivity(), true, false);
    }
    @Override
    public void onStart() {
        super.onStart();
        //deckAdapter.startListening();
        //requireView().post(() -> setupGradient(lineChart));
    }
    @Override
    public void onStop() {
        super.onStop();
        transparentStatusBar(getActivity(), false, false);
        //deckAdapter.stopListening();
    }
}
