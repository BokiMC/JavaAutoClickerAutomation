package com.example.eventlogger;

public class KeyboardEvent {
    private long elapsedTime;
    private String eventType;
    private String key;

    public KeyboardEvent(long elapsedTime, String eventType, String key) {
        this.elapsedTime = elapsedTime;
        this.eventType = eventType;
        this.key = key;
    }

    public long getElapsedTime() {
        return elapsedTime;
    }

    public String getEventType() {
        return eventType;
    }

    public String getKey() {
        return key;
    }

    @Override
    public String toString() {
        return "KeyboardEvent{" +
                "elapsedTime=" + elapsedTime +
                ", eventType='" + eventType + '\'' +
                ", key='" + key + '\'' +
                '}';
    }
}
