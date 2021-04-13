package net.project.studyez.statistics.time_graph;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;
import java.util.Map;

public class TimeStudied {

    @ServerTimestamp
    private Date dateCreated;
    private Map<String, Long> dailyTimesList;
    private long totalTime;
    private int averageTime;

    public TimeStudied(){}

    public TimeStudied(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
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
