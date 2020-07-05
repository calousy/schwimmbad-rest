package de.codeforgarching.schwimmbad.backend.entity;

import java.time.LocalDateTime;

public class VisitorInformation {
    private LocalDateTime _timestamp;
    private int visitors;

    public VisitorInformation(LocalDateTime timestamp, int visitors) {
        _timestamp = timestamp;
        this.visitors = visitors;
    }

    public VisitorInformation() {
    }

    public LocalDateTime getTimestamp() {
        return _timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        _timestamp = timestamp;
    }

    public int getVisitors() {
        return visitors;
    }

    public void setVisitors(int visitors) {
        this.visitors = visitors;
    }
}
