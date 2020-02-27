/**
 * @author Paulius Staisiunas
 */

package com.j2020.model.revolut;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.j2020.model.PaymentResponse;

public class RevolutPaymentResponse implements PaymentResponse {
    private String id;
    private String state;

    @JsonProperty(value = "created_at")
    private String createdAt;

    @JsonProperty(value = "completed_at")
    private String completedAt;

    @Override
    public String getPaymentId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getStatus() {
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
