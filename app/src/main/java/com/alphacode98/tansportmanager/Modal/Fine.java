package com.alphacode98.tansportmanager.Modal;

public class Fine {
    private int fineId;
    private String inspectorId;
    private String clientEmail;
    private int rootId;
    private float fine;
    private String date;
    private String time;
    private String reason;

    public Fine() {
    }

    public Fine(int fineId, String inspectorId, String clientEmail, int rootId, float fine, String date, String time) {
        this.fineId = fineId;
        this.inspectorId = inspectorId;
        this.clientEmail = clientEmail;
        this.rootId = rootId;
        this.fine = fine;
        this.date = date;
        this.time = time;
    }

    public int getFineId() {
        return fineId;
    }

    public void setFineId(int fineId) {
        this.fineId = fineId;
    }

    public String getInspectorId() {
        return inspectorId;
    }

    public void setInspectorId(String inspectorId) {
        this.inspectorId = inspectorId;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public int getRootId() {
        return rootId;
    }

    public void setRootId(int rootId) {
        this.rootId = rootId;
    }

    public float getFine() {
        return fine;
    }

    public void setFine(float fine) {
        this.fine = fine;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
