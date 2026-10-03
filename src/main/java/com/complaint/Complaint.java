package com.complaint;

public class Complaint {
    private int id;
    private String trackingId;
    private String title;
    private String description;
    private String status;

    public Complaint(int id, String trackingId, String title, String description, String status) {
        this.id = id;
        this.trackingId = trackingId;
        this.title = title;
        this.description = description;
        this.status = status;
    }

    public int getId() { return id; }
    public String getTrackingId() { return trackingId; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getStatus() { return status; }
    
    public void setStatus(String status) { this.status = status; }
}
