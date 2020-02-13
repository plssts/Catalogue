package com.j2020.model.revolut;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RevolutPaymentResponse {
    private String id;
    private String state;

    @JsonProperty(value = "created_at")
    private String createdAt;

    @JsonProperty(value = "completed_at", required = false)
    private String completedAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(String completedAt) {
        this.completedAt = completedAt;
    }
}
