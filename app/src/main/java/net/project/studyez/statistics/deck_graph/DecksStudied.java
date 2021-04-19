package net.project.studyez.statistics.deck_graph;

import java.util.Date;
import java.util.Map;

public class DecksStudied {

    private Date weekCreated;
    private Map<String, Long> deckTimesList;
    private long totalTime;
    private int averageTime;

    public DecksStudied(){}

    public DecksStudied(Date weekCreated, Map<String, Long> deckTimesList, long totalTime, int averageTime) {
        this.weekCreated = weekCreated;
        this.deckTimesList = deckTimesList;
        this.totalTime = totalTime;
        this.averageTime = averageTime;
    }

    public Date getWeekCreated() {
        return weekCreated;
    }

    public void setWeekCreated(Date weekCreated) {
        this.weekCreated = weekCreated;
    }

    public Map<String, Long> getDeckTimesList() {
        return deckTimesList;
    }

    public void setDeckTimesList(Map<String, Long> deckTimesList) {
        this.deckTimesList = deckTimesList;
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
