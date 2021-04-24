package net.project.studyez.statistics.time_graph;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TimeStudied {

    private Date weekCreated;
    private Map<String, Long> dailyTimesList;
    private long totalTime;
    private int averageTime;

    public TimeStudied(){}

    public TimeStudied(Date weekCreated) {
        this.weekCreated = weekCreated;
    }

    public Date getWeekCreated() {
        return weekCreated;
    }

    public void setWeekCreated(Date weekCreated) {
        this.weekCreated = weekCreated;
    }

    public void initMap() {
        dailyTimesList = new HashMap<>();
    }
    public void deleteMap() {
        dailyTimesList = Collections.emptyMap();
    }

    public Map<String, Long> getDailyTimesList() {
        return dailyTimesList;
    }

    public void setDailyTimesList(Map<String, Long> dailyTimesList) {
        this.dailyTimesList = dailyTimesList;
    }

    public long getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(long totalTime) {
        this.totalTime = totalTime;
    }

    public int getAverageTime() {
        return averageTime;
    }

    public void setAverageTime(int averageTime) {
        this.averageTime = averageTime;
    }
}
