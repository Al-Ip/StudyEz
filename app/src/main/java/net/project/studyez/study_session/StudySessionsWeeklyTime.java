package net.project.studyez.study_session;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;
import java.util.Map;

public class StudySessionsWeeklyTime {

    @ServerTimestamp
    private Date dateCreated;
    private Map<String, Long> dailyTimesList;
    private long totalTime;
    private int averageTime;

    public StudySessionsWeeklyTime(){}

    public StudySessionsWeeklyTime(Date dateCreated) {
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
