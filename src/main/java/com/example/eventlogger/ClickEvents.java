package com.example.eventlogger;

public class ClickEvents {
    private long elapsedTime;
    private String eventType;
    private int x;
    private int y;

    public ClickEvents(long elapsedTime, String eventType, int x, int y) {
        this.elapsedTime = elapsedTime;
        this.eventType = eventType;
        this.x = x;
        this.y = y;
    }

    public long getElapsedTime() {
        return elapsedTime;
    }

    public long getX() {
        return x;
    }

    public long getY() {
        return y;
    }

    public String getEventType() {
        return eventType;
    }
    
    @Override
    public String toString() {
        return "ClickEvents{" +
                "elapsedTime=" + elapsedTime +
                ", eventType='" + eventType + '\'' +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
