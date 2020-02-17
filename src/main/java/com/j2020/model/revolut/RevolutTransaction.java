package com.j2020.model.revolut;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.j2020.model.Transaction;

public class RevolutTransaction implements Transaction {
    private String id;
    private String type;

    @JsonProperty(value = "request_id")
    private String requestId;

    private String state;

    @JsonProperty(value = "created_at")
    private String dateOfCreating;

    @JsonProperty(value = "updated_at")
    private String dateOfUpdating;

    @JsonProperty(value = "completed_at")
    private String dateOfCompleting;

    @JsonProperty(value = "related_transaction_id")
    private String relatedTransactionId;

    private String reference;
    private RevolutTransactionMerchant merchant;

    @JsonProperty(value = "legs")
    private RevolutTransactionLeg[] revolutLegs;

    private RevolutTransactionCard card;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDateOfCreating() {
        return dateOfCreating;
    }

    public void setDateOfCreating(String dateOfCreating) {
        this.dateOfCreating = dateOfCreating;
    }

    public String getDateOfUpdating() {
        return dateOfUpdating;
    }

    public void setDateOfUpdating(String dateOfUpdating) {
        this.dateOfUpdating = dateOfUpdating;
    }

    public String getDateOfCompleting() {
        return dateOfCompleting;
    }

    public void setDateOfCompleting(String dateOfCompleting) {
        this.dateOfCompleting = dateOfCompleting;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public RevolutTransactionLeg[] getRevolutLegs() {
        return revolutLegs;
    }

    public void setRevolutLegs(RevolutTransactionLeg[] revolutLegs) {
        this.revolutLegs = revolutLegs;
    }

    public RevolutTransactionMerchant getMerchant() {
        return merchant;
    }

    public void setMerchant(RevolutTransactionMerchant merchant) {
        this.merchant = merchant;
    }

    public RevolutTransactionCard getCard() {
        return card;
    }

    public void setCard(RevolutTransactionCard card) {
        this.card = card;
    }

    public String getRelatedTransactionId() {
        return relatedTransactionId;
    }

    public void setRelatedTransactionId(String relatedTransactionId) {
        this.relatedTransactionId = relatedTransactionId;
    }
}
