/**
 * @author Paulius Staisiunas
 */

package com.j2020.model.deutsche;

public class DeutschePhototanResponse {
    private String method;
    private DeutschePhototanChallenge challenge;
    private String id;

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public DeutschePhototanChallenge getChallenge() {
        return challenge;
    }

    public void setChallenge(DeutschePhototanChallenge challenge) {
        this.challenge = challenge;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
